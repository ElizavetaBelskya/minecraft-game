package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.listener.JoinListener;
import ru.kpfu.itis.belskaya.listener.ListenerHandler;
import ru.kpfu.itis.belskaya.listener.ServerEventListener;
import ru.kpfu.itis.belskaya.protocol.InputService;
import ru.kpfu.itis.belskaya.protocol.OutputService;
import ru.kpfu.itis.belskaya.protocol.PlayerEntity;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.exceptions.UnsupportedProtocolException;
import ru.kpfu.itis.belskaya.protocol.exceptions.WrongMessageTypeException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;
import ru.kpfu.itis.belskaya.protocol.messages.MessageJoinGame;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Connection implements Runnable {

    private Socket socket;
    private Thread thread;
    private Server server;
    private InputService inputService;
    private OutputService outputService;
    private int connectionId;

    public Connection(Server server, Socket socket, int connectionId) throws IOException {
        this.socket = socket;
        this.server = server;
        this.inputService = new InputService(socket.getInputStream());
        this.outputService = new OutputService(socket.getOutputStream());
        this.connectionId = connectionId;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        Message message;
        try {
            int randomX = 0 + (int)(Math.random() * 30);
            int randomY = 0 + (int)(Math.random() * 30);
            PlayerEntity player = new PlayerEntity(connectionId, randomX, randomY);
            server.addPlayer(player);
            MessageJoinGame messageJoinGame = new MessageJoinGame(server.getPlayers(), connectionId);
            JoinListener join = new JoinListener();

            join.init(server);
            join.handle(connectionId, messageJoinGame);

            while ((message = inputService.readMessage()) != null) {
                ServerEventListener listener = ListenerHandler.getListenerByType(message.getMessageType());
                listener.init(server);
                listener.handle(connectionId, message);
            }

            //TODO: Обработка исключений, братан!
        } catch (MessageWorkException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedProtocolException e) {
            throw new RuntimeException(e);
        } catch (WrongMessageTypeException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }
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
}
