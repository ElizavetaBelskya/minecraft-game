package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.listener.ServerEventListener;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;
import ru.kpfu.itis.belskaya.protocol.messages.MessagePutPlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements ServerWorking {
    private List<Room> rooms;
    private int connectionCounter = 0;
    protected List<ServerEventListener> listeners;

    protected ServerSocket server;
    protected boolean started;
    private int PORT = 8077;
    public Server() {
        rooms = new ArrayList<Room>();
        this.listeners = new ArrayList<>();
        //TODO:register listeners
        this.started = false;
    }

//    @Override
//    public void sendMessage(int connectionId, Message message) throws ServerException {
//        if (!started){
//            throw new ServerException("Server hasn't been started yet.");
//        }
//
//        try {
//            Connection connection =
//                    connections.stream().filter(x -> x.getConnectionId() == connectionId).findFirst().get();
//            connection.getOutputService().writeMessage(message);
//        } catch (MessageWorkException e) {
//            throw new ServerException("Can't send message.", e);
//        }
//    }

    @Override
    public void sendBroadCastMessage(Message message) throws ServerException {
        if(!started){
            throw new ServerException("Server hasn't been started yet.");
        }
        try{
            int id = message.getRoomId();
            Room room = rooms.get(id);
            for (Connection connection : room.getRoomConnections()){
                connection.getOutputService().writeMessage(message);
            }
        } catch (MessageWorkException e) {
            throw new ServerException("Can't send message.", e);
        }
    }

//    public void addPlayer(PlayerEntity player) {
//        players.add(player);
//    }
//
//    public void removePlayer(PlayerEntity player) {
//        players.remove(player);
//    }


    public void changedPlayerCoordinates(MessagePutPlayer messagePutPlayer) {
        int id = messagePutPlayer.getRoomId();
        Room room = rooms.get(id);
        room.changedPlayerCoordinates(messagePutPlayer.getPlayerId(), messagePutPlayer.getxCoordinate(), messagePutPlayer.getyCoordinate());
    }

    public int findAvailableRoom() {
        for (Room room: rooms) {
            if (room.getCountOfPlayers() != Room.MAX_COUNT) {
                return room.getRoomId();
            }
        }
        Room room = new Room(rooms.size());
        rooms.add(room);
        return room.getRoomId();
    }

    public Room getRoom(int roomId) {
        return rooms.get(roomId);
    }


    public void init() throws ServerException {
        server = null;
        try {
            server = new ServerSocket(PORT);
            started = true;
            while (true) {
                Socket client = server.accept();
                int roomId = findAvailableRoom();
                Connection connection = new Connection(this, client, connectionCounter, roomId);
                rooms.get(roomId).addConnection(connection);
                connectionCounter++;
            }
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }


}
