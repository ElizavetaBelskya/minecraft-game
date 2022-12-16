package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.listener.ServerEventListener;
import ru.kpfu.itis.belskaya.protocol.PlayerEntity;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements ServerWorking {
    private List<Connection> connections;

    private List<PlayerEntity> players;
    private int connectionCounter = 0;
    protected List<ServerEventListener> listeners;

    protected ServerSocket server;
    protected boolean started;
    private int PORT = 8077;
    public Server() {
        connections = new ArrayList<>();
        players = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.started = false;
    }

    @Override
    public void sendMessage(int connectionId, Message message) throws ServerException {
        if (!started){
            throw new ServerException("Server hasn't been started yet.");
        }

        try {
            Connection connection = connections.stream().filter(x -> x.getConnectionId() == connectionId).findFirst().get();
            connection.getOutputService().writeMessage(message);
        } catch (MessageWorkException e) {
            throw new ServerException("Can't send message.", e);
        }
    }

    @Override
    public void sendBroadCastMessage(Message message) throws ServerException {
        if(!started){
            throw new ServerException("Server hasn't been started yet.");
        }
        try{
            for (Connection connection : connections){
                connection.getOutputService().writeMessage(message);
            }
        } catch (MessageWorkException e) {
            throw new ServerException("Can't send message.", e);
        }
    }

    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public void addPlayer(PlayerEntity player) {
        players.add(player);
    }
    public void init() throws ServerException {
        server = null;
        try {
            server = new ServerSocket(PORT);
            started = true;
            while (true) {
                Socket client = server.accept();
                connections.add(new Connection(this, client, connectionCounter));
                connectionCounter++;
            }
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }







}
