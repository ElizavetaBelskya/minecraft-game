package ru.kpfu.itis.belskaya.protocol.messages;

import ru.kpfu.itis.belskaya.protocol.PlayerEntity;

import java.util.List;

public class MessageJoinGame extends Message {

    private List<PlayerEntity> players;

    private int connectionId;
    public MessageJoinGame(List<PlayerEntity> players, int connectionId) {
        super(4);
        this.players = players;
        this.connectionId = connectionId;
    }

    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public int getConnectionId() {
        return connectionId;
    }
}
