package ru.kpfu.itis.belskaya.protocol.messages;


public class MessageDeleteBlock extends Message {
    private int xCoordinate;

    private int yCoordinate;

    public MessageDeleteBlock(int xCoordinate, int yCoordinate) {
        super(3);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
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
        return "MessageDeleteBlock{" +
                "xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                '}';
    }
}
