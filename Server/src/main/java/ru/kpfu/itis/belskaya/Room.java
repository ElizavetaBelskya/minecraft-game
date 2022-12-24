package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.protocol.BlockEntity;
import ru.kpfu.itis.belskaya.protocol.PlayerEntity;
import ru.kpfu.itis.belskaya.protocol.exceptions.PlayerToRoomAddingException;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomId;
    private int MIN_COORDINATE = 0;
    private int MAX_COORDINATE = 20;
    public int MAX_COUNT = 4;
    private List<PlayerEntity> players = new ArrayList<>();
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

    public void addConnection(Connection connection) throws PlayerToRoomAddingException {
        if (getCountOfPlayers() < MAX_COUNT || MAX_COUNT == -1) {
            roomConnections.add(connection);
            connection.getServer().incrementConnectionCounter();
            int randomX = MIN_COORDINATE + (int)(Math.random() * MAX_COORDINATE);
            int randomY = MIN_COORDINATE + (int)(Math.random() * MAX_COORDINATE);
            PlayerEntity player = new PlayerEntity(connection.getConnectionId(), randomX, randomY);
            players.add(player);
        } else {
            throw new PlayerToRoomAddingException("This room is full, you are late");
        }

    }

    public void removeConnection(int connectionId) {
        int index = getPlayerAndConnectionIndex(connectionId);
        getConnection(connectionId).getServer().decrementConnectionCounter();
        roomConnections.remove(index);
        players.remove(index);
    }

    public Room(int roomId) {
        this.roomId = roomId;
    }

    public Room(int roomId, int maxCount) {
        this.roomId = roomId;
        this.MAX_COUNT = maxCount;
    }

    public int getPlayerAndConnectionIndex(int connectionId) {
        int index = -1;
        for (int i = 0; i < roomConnections.size(); i++) {
            if (roomConnections.get(i).getConnectionId() == connectionId) {
                index = i;
                break;
            }
        }
        return index;
    }

    public Connection getConnection(int connectionId) {
        int index = getPlayerAndConnectionIndex(connectionId);
        return getRoomConnections().get(index);
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
