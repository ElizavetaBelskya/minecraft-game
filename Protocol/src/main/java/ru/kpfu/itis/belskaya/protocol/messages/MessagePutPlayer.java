package ru.kpfu.itis.belskaya.protocol.messages;

public class MessagePutPlayer extends Message {
    private int xCoordinate;
    private int yCoordinate;
    public MessagePutPlayer(int xCoordinate, int yCoordinate, int roomId, int connectionId) {
        super(MessageTypes.PUT_PLAYER_MESSAGE, roomId, connectionId);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getPlayerId() {
        return getConnectionId();
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    @Override
    public String toString() {
        return "MessagePutPlayer{" +
                "playerId=" + getConnectionId() +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                '}';
    }
}
