package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.protocol.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomId;

    public final static int MAX_COUNT = 4;

    private List<PlayerEntity> players;
    private List<Connection> roomConnections = new ArrayList<>();

    public List<Connection> getRoomConnections() {
        return roomConnections;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getCountOfPlayers() {
        return roomConnections.size();
    }

    public void addConnection(Connection connection) {
        roomConnections.add(connection);
        int randomX = 0 + (int)(Math.random() * 30);
        int randomY = 0 + (int)(Math.random() * 30);
        PlayerEntity player = new PlayerEntity(connection.getConnectionId(), randomX, randomY);
        players.add(player);
    }

    public void removeConnection(int connectionId) {
        Connection connection = getRoomConnections().get(getPlayerAndConnectionIndex(connectionId));
        roomConnections.remove(connection);
        players.remove(getPlayerAndConnectionIndex(connection.getConnectionId()));
    }

    public Room(int roomId) {
        this.roomId = roomId;
        players = new ArrayList<>();
    }

    public int getPlayerAndConnectionIndex(int connectionId) {
        int index = -1;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == connectionId) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void changedPlayerCoordinates(int playerId, int xCoordinate, int yCoordinate) {
        int index = getPlayerAndConnectionIndex(playerId);
        PlayerEntity player = players.get(index);
        player.setxCoordinate(xCoordinate);
        player.setyCoordinate(yCoordinate);
    }

    public List<PlayerEntity> getPlayers() {
        return players;
    }

}
