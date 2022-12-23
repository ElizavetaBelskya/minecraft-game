package ru.kpfu.itis.belskaya.protocol.messages;

public class MessageJoinRoom extends Message {

    public MessageJoinRoom(int roomId, int connectionId) {
        super(MessageTypes.JOIN_ROOM_MESSAGE, roomId, connectionId);
    }

}
