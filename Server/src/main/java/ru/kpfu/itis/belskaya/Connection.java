package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.listener.*;
import ru.kpfu.itis.belskaya.protocol.BlockEntity;
import ru.kpfu.itis.belskaya.protocol.InputService;
import ru.kpfu.itis.belskaya.protocol.OutputService;
import ru.kpfu.itis.belskaya.protocol.PlayerEntity;
import ru.kpfu.itis.belskaya.protocol.messages.*;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Connection implements Runnable {
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
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        Message message;
        try {
            Integer[] roomIds = server.findAvailableRooms();
            MessageChooseRoom messageChooseRoom = new MessageChooseRoom(connectionId, roomIds);
            ChooseRoomListener chooseRoomListener = new ChooseRoomListener();
            chooseRoomListener.init(server);
            chooseRoomListener.handle(messageChooseRoom);

            do {
                message = inputService.readMessage();
                ServerEventListener listener = ListenerHandler.getListenerByType(message.getMessageType());
                listener.init(server);
                listener.handle(message);
            } while (!(message.getMessageType() == MessageTypes.REMOVE_PLAYER_MESSAGE));

        } catch (Exception e) {
//            if (started) {
//                MessageRemovePlayer messageRemovePlayer = new MessageRemovePlayer(roomId, connectionId);
//                RemovePlayerListener removePlayerListener = new RemovePlayerListener();
//                removePlayerListener.init(server);
//                try {
//                    removePlayerListener.handle(messageRemovePlayer);
//                } catch (ServerException ex) {
//                    throw new RuntimeException(ex);
//                }
//                closeConnection();
//            } else {
//                server.getRoom(roomId).removeConnection(connectionId);
//                closeConnection();
//            }

        }
//        } catch (ServerException e) {
//            throw new RuntimeException(e);
//        } catch (MessageWorkException e) {
//            throw new RuntimeException(e);
//        } catch (UnsupportedProtocolException e) {
//            throw new RuntimeException(e);
//        } catch (WrongMessageTypeException e) {
//            throw new RuntimeException(e);
//        }
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

    public void closeConnection() {
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

    public MessageJoinGame createMessageJoinGame() {
        List<PlayerEntity> players = server.getRoom(roomId).getPlayers();
        List<BlockEntity> blocks = server.getRoom(roomId).getBlockEntities();
        MessageJoinGame messageJoinGame = new MessageJoinGame(players, blocks, roomId, connectionId);
        return messageJoinGame;
    }

}
