package ru.kpfu.itis.belskaya.server;

import ru.kpfu.itis.belskaya.listener.*;
import ru.kpfu.itis.belskaya.protocol.ioServices.InputService;
import ru.kpfu.itis.belskaya.protocol.ioServices.OutputService;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.exceptions.UnsupportedProtocolException;
import ru.kpfu.itis.belskaya.protocol.exceptions.WrongMessageTypeException;
import ru.kpfu.itis.belskaya.protocol.messages.*;

import java.io.IOException;
import java.net.Socket;


public class Connection implements Runnable {

    private ListenerHandler listenerHandler;
    private Socket socket;
    private Thread thread;
    private Server server;
    private InputService inputService;
    private OutputService outputService;
    private int connectionId;
    private int roomId;
    boolean started = false;

    public Connection(Server server, Socket socket, int connectionId, int roomId) throws IOException {
        this.socket = socket;
        this.server = server;
        this.inputService = new InputService(socket.getInputStream());
        this.outputService = new OutputService(socket.getOutputStream());
        this.connectionId = connectionId;
        this.roomId = roomId;
        listenerHandler = new ListenerHandler(server);
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            if (server.getConnectionCounter() >= Server.PLAYERS_LIMIT) {
                MessageServerIsFull messageServerIsFull = new MessageServerIsFull(-1, connectionId);
                ServerOverloadedListener serverOverloadedListener = new ServerOverloadedListener();
                serverOverloadedListener.init(server);
                serverOverloadedListener.handle(messageServerIsFull);
                closeSocket();
            } else {
                Integer[] roomIds = server.findAvailableRooms();
                MessageChooseRoom messageChooseRoom = new MessageChooseRoom(connectionId, roomIds);
                ChooseRoomListener chooseRoomListener = new ChooseRoomListener();
                chooseRoomListener.init(server);
                chooseRoomListener.handle(messageChooseRoom);
                Message message = inputService.readMessage();
                while (!(message.getMessageType() == MessageTypes.REMOVE_PLAYER_MESSAGE)) {
                    ServerEventListener listener = listenerHandler.getListenerByType(message.getMessageType());
                    listener.handle(message);
                    message = inputService.readMessage();
                }
            }
        } catch (MessageWorkException e) {
            System.out.println(e.getMessage() + ": messaging error with " + connectionId);
        } catch (UnsupportedProtocolException e) {
            System.out.println(e.getMessage() + ": unsupported protocol of " + connectionId);
        } catch (WrongMessageTypeException e) {
            System.out.println(e.getMessage() + ": wrong message type from " + connectionId);
        } finally {
            MessageRemovePlayer messageRemovePlayer = new MessageRemovePlayer(roomId, connectionId);
            RemovePlayerListener listener = new RemovePlayerListener();
            listener.init(server);
            listener.handle(messageRemovePlayer);
            closeSocket();
        }

    }


    public void setStarted(boolean started) {
        this.started = started;
    }

    public InputService getInputService() {
        return inputService;
    }

    public OutputService getOutputService() {
        return outputService;
    }


    public int getConnectionId() {
        return connectionId;
    }

    public void closeSocket() {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Server getServer() {
        return server;
    }


    @Override
    public String toString() {
        return "Connection{" +
                "socket=" + socket +
                ", thread=" + thread +
                ", server=" + server +
                ", inputService=" + inputService +
                ", outputService=" + outputService +
                ", connectionId=" + connectionId +
                ", roomId=" + roomId +
                ", started=" + started +
                '}';
    }
}
