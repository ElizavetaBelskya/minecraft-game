package ru.kpfu.itis.belskaya.protocol.messages;

public class MessageServerIsFull extends Message {

    public MessageServerIsFull(int roomId, int connectionId) {
        super(MessageTypes.SERVER_IS_FULL_MESSAGE, roomId, connectionId);
    }

}
