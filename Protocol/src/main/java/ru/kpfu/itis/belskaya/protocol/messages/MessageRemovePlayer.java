package ru.kpfu.itis.belskaya.protocol.messages;

public class MessageRemovePlayer extends Message {
    public MessageRemovePlayer(int roomId, int connectionId) {
        super(MessageTypes.REMOVE_PLAYER_MESSAGE, roomId, connectionId);
    }

    public int getPlayerId() {
        return getConnectionId();
    }

}
