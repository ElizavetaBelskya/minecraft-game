package ru.kpfu.itis.belskaya.protocol.messages;

public class MessageChooseRoom extends Message {
    private Integer[] roomIndexes;
    public MessageChooseRoom(int connectionId, Integer[] roomIds) {
        super(MessageTypes.CHOOSE_ROOM_MESSAGE, -1, connectionId);
        this.roomIndexes = roomIds;
    }

    public Integer[] getRoomIndexes() {
        return roomIndexes;
    }

}
