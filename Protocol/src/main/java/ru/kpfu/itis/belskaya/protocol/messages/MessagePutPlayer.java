package ru.kpfu.itis.belskaya.protocol.messages;

public class MessagePutPlayer extends Message {

    private static final byte TYPE = 1;
    private int playerId;
    private int xCoordinate;
    private int yCoordinate;
    public MessagePutPlayer(int playerId, int xCoordinate, int yCoordinate) {
        super(1);
        this.playerId = playerId;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
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
                "playerId=" + playerId +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                '}';
    }
}
