package ru.kpfu.itis.belskaya.protocol.messages;

import ru.kpfu.itis.belskaya.protocol.entities.BlockEntity;
import ru.kpfu.itis.belskaya.protocol.entities.PlayerEntity;

import java.util.List;

public class MessageJoinGame extends Message {

    private List<PlayerEntity> players;

    private List<BlockEntity> blocks;
    private int roomId;
    private int connectionId;

    public MessageJoinGame(List<PlayerEntity> players, List<BlockEntity> blocks, int roomId, int connectionId) {
        super(MessageTypes.JOIN_MESSAGE, roomId, connectionId);
        this.players = players;
        this.blocks = blocks;
        this.roomId = roomId;
        this.connectionId = connectionId;
    }
    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public List<BlockEntity> getBlocks() {
        return blocks;
    }

}
