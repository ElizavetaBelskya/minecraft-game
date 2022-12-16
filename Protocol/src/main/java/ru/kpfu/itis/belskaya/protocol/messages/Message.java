package ru.kpfu.itis.belskaya.protocol.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

    private int messageType;

    public int getMessageType() {
        return messageType;
    }

    protected Message(int messageType) {
        this.messageType = messageType;
    }

}
