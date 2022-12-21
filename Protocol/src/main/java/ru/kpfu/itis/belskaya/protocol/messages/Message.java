package ru.kpfu.itis.belskaya.protocol.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private MessageTypes messageType;

    private int roomId;

    private int connectionId;
    public MessageTypes getMessageType() {
        return messageType;
    }

    protected Message(MessageTypes messageType, int roomId, int connectionId) {
        this.messageType = messageType;
        this.roomId = roomId;
        this.connectionId = connectionId;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getConnectionId() {
        return connectionId;
    }
}
