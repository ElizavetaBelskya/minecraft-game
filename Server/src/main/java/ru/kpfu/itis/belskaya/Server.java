package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.listener.ServerEventListener;
import ru.kpfu.itis.belskaya.protocol.BlockEntity;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;
import ru.kpfu.itis.belskaya.protocol.messages.MessageDeleteBlock;
import ru.kpfu.itis.belskaya.protocol.messages.MessagePutBlock;
import ru.kpfu.itis.belskaya.protocol.messages.MessagePutPlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements ServerWorking {

    private Room waitingRoom = new Room(-1);
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

    @Override
    public void sendMessage(Message message) throws ServerException {
        if (!started){
            throw new ServerException("Server hasn't been started yet.");
        }

        try {
            int id = message.getRoomId();
            int connectionId = message.getConnectionId();
            Room room = getRoom(id);
            Connection connection = room.getConnection(connectionId);
            connection.getOutputService().writeMessage(message);
        } catch (MessageWorkException e) {
            throw new ServerException("Can't send message.", e);
        }
    }

    @Override
    public void sendBroadCastMessage(Message message) throws ServerException {

        if (!started){
            throw new ServerException("Server hasn't been started yet.");
        }

        try{
            int id = message.getRoomId();
            Room room = getRoom(id);
            for (Connection connection : room.getRoomConnections()) {
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
        Room room = getRoom(id);
        room.changedPlayerCoordinates(messagePutPlayer.getPlayerId(), messagePutPlayer.getxCoordinate(), messagePutPlayer.getyCoordinate());
    }

    public void addBlockToRoom(MessagePutBlock messagePutBlock) {
        int id = messagePutBlock.getRoomId();
        Room room = getRoom(id);
        BlockEntity block = new BlockEntity(messagePutBlock.getBlockType(), messagePutBlock.getxCoordinate(), messagePutBlock.getyCoordinate());
        room.addBlock(block);
    }

    public void removeBlockToRoom(MessageDeleteBlock messageDeleteBlock) {
        int id = messageDeleteBlock.getRoomId();
        Room room =  getRoom(id);
        room.removeBlock(messageDeleteBlock.getxCoordinate(), messageDeleteBlock.getyCoordinate());
    }

    public Integer[] findAvailableRooms() {
        List<Integer> availableRoomsList = new ArrayList<Integer>();
        for (Room room: rooms) {
            if (room.getCountOfPlayers() < Room.MAX_COUNT) {
                availableRoomsList.add(room.getRoomId());
            }
        }
        Integer[] availableRooms = new Integer[availableRoomsList.size()];
        availableRoomsList.toArray(availableRooms);
        return availableRooms;
    }

    public Room getRoom(int roomId) {
        if (roomId == -1 || roomId == -2) {
            return waitingRoom;
        } else {
            return rooms.get(roomId);
        }
    }

    public int moveOutOfWaitingRoom(int roomId, int connectionId) {
        Room room;
        Connection connection = waitingRoom.getConnection(connectionId);
        waitingRoom.removeConnection(connectionId);
        if (roomId != -2) {
            room = getRoom(roomId);
        } else {
            room = new Room(rooms.size());
            rooms.add(room);
        }
        connection.setRoomId(room.getRoomId());
        room.addConnection(connection);
        return room.getRoomId();
    }


    public void init() throws ServerException {
        server = null;
        try {
            server = new ServerSocket(PORT);
            started = true;
            while (true) {
                Socket client = server.accept();
                Connection connection = new Connection(this, client, connectionCounter, -1);
                waitingRoom.addConnection(connection);
                connectionCounter++;
            }
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }


}
