package ru.kpfu.itis.belskaya.protocol.messages;

public class MessagePutBlock extends Message {

    private int blockId;

    private int xCoordinate;

    private int yCoordinate;

    public MessagePutBlock(int blockId, int xCoordinate, int yCoordinate) {
        super(2);
        this.blockId = blockId;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
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
        return "MessagePutBlock{" +
                "blockId=" + blockId +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                '}';
    }
}
