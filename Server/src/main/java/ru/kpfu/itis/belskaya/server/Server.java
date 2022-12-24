package ru.kpfu.itis.belskaya.server;

import ru.kpfu.itis.belskaya.exceptions.ServerException;
import ru.kpfu.itis.belskaya.listener.ServerEventListener;
import ru.kpfu.itis.belskaya.protocol.entities.BlockEntity;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.exceptions.PlayerToRoomAddingException;
import ru.kpfu.itis.belskaya.protocol.messages.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements ServerWorking {

    private Room waitingRoom = new Room(-1, -1);
    private List<Room> rooms;
    private int indexCounter = 0;

    private int connectionCounter = 0;
    public static final int PLAYERS_LIMIT = 100;
    protected List<ServerEventListener> listeners;

    protected ServerSocket server;
    protected boolean started;
    private int PORT = 8077;
    public Server() {
        rooms = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.started = false;
    }

    @Override
    public void sendMessage(Message message, int connectionId) throws MessageWorkException {
        int id = message.getRoomId();
        Room room = getRoom(id);
        Connection connection = room.getConnection(connectionId);
        connection.getOutputService().writeMessage(message);

    }

    @Override
    public void sendBroadCastMessage(Message message) throws MessageWorkException {
        int id = message.getRoomId();
        Room room = getRoom(id);
        for (Connection connection : room.getRoomConnections()) {
            connection.getOutputService().writeMessage(message);
        }
    }

    @Override
    public void changedPlayerCoordinates(MessagePutPlayer messagePutPlayer) {
        int id = messagePutPlayer.getRoomId();
        Room room = getRoom(id);
        room.changedPlayerCoordinates(messagePutPlayer.getPlayerId(), messagePutPlayer.getxCoordinate(), messagePutPlayer.getyCoordinate());
    }

    @Override
    public void addBlockToRoom(MessagePutBlock messagePutBlock) {
        int id = messagePutBlock.getRoomId();
        Room room = getRoom(id);
        BlockEntity block = new BlockEntity(messagePutBlock.getBlockType(), messagePutBlock.getxCoordinate(), messagePutBlock.getyCoordinate());
        room.addBlock(block);
    }

    @Override
    public void removeBlockToRoom(MessageDeleteBlock messageDeleteBlock) {
        int id = messageDeleteBlock.getRoomId();
        Room room =  getRoom(id);
        room.removeBlock(messageDeleteBlock.getxCoordinate(), messageDeleteBlock.getyCoordinate());
    }

    public Integer[] findAvailableRooms() {
        List<Integer> availableRoomsList = new ArrayList<Integer>();
        for (Room room: rooms) {
            if (room.getCountOfPlayers() < room.MAX_COUNT) {
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

    public int moveOutOfWaitingRoom(int roomId, int connectionId) throws PlayerToRoomAddingException {
        Room room;
        Connection connection = waitingRoom.getConnection(connectionId);
        if (roomId != -2) {
            room = getRoom(roomId);
        } else {
            room = new Room(rooms.size());
            rooms.add(room);
        }
        connection.setRoomId(room.getRoomId());
        addConnectionToRoom(connection, room.getRoomId());
        waitingRoom.removeConnection(connectionId);
        return room.getRoomId();
    }

    public void addConnectionToRoom(Connection connection, int roomId) throws PlayerToRoomAddingException {
        Room room = getRoom(roomId);
        room.addConnection(connection);
    }

    public void removeConnectionFromRoom(int roomId, int connectionId) {
        Room room = getRoom(roomId);
        room.removeConnection(connectionId);
    }

    @Override
    public void init() throws ServerException {
        server = null;
        try {
            server = new ServerSocket(PORT);
            started = true;
            while (true) {
                Socket client = server.accept();
                Connection connection = new Connection(this, client, indexCounter, -1);
                try {
                    waitingRoom.addConnection(connection);
                    indexCounter++;
                } catch (PlayerToRoomAddingException e) {
                    throw new ServerException(e);
                }
            }
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }


    public int getConnectionCounter() {
        return connectionCounter;
    }

    public void incrementConnectionCounter() {
        connectionCounter++;
    }


    public void decrementConnectionCounter() {
        connectionCounter--;
    }

}
