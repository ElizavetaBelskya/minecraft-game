package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.protocol.BlockEntity;
import ru.kpfu.itis.belskaya.protocol.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomId;
    private int MIN_COORDINATE = 0;
    private int MAX_COORDINATE = 20;
    public final static int MAX_COUNT = 4;
    private List<PlayerEntity> players;
    private List<Connection> roomConnections = new ArrayList<>();

    public List<Connection> getRoomConnections() {
        return roomConnections;
    }

    private List<BlockEntity> blockEntities = new ArrayList<>();

    public int getRoomId() {
        return roomId;
    }

    public int getCountOfPlayers() {
        return roomConnections.size();
    }

    public void addConnection(Connection connection) {
        roomConnections.add(connection);
        int randomX = MIN_COORDINATE + (int)(Math.random() * MAX_COORDINATE);
        int randomY = MIN_COORDINATE + (int)(Math.random() * MAX_COORDINATE);
        PlayerEntity player = new PlayerEntity(connection.getConnectionId(), randomX, randomY);
        players.add(player);
    }

    public void removeConnection(int connectionId) {
        Connection connection = getConnection(connectionId);
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

    public Connection getConnection(int connectionId) {
        return getRoomConnections().get(getPlayerAndConnectionIndex(connectionId));
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

    public void addBlock(BlockEntity block) {
        blockEntities.add(block);
    }

    public void removeBlock(int x, int y) {
        BlockEntity block = findBlockByCoordinates(x, y);
        blockEntities.remove(block);
    }

    private BlockEntity findBlockByCoordinates(int x, int y) {
        for (BlockEntity block: blockEntities) {
            if (block.getX() == x && block.getY() == y) {
                return block;
            }
        }
        return null;
    }


    public List<BlockEntity> getBlockEntities() {
        return blockEntities;
    }
}
