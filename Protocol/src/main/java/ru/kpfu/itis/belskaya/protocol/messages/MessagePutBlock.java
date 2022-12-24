package ru.kpfu.itis.belskaya.protocol.messages;

import ru.kpfu.itis.belskaya.protocol.entities.BlockType;

public class MessagePutBlock extends Message {

    private BlockType blockType;

    private int xCoordinate;

    private int yCoordinate;

    public MessagePutBlock(BlockType blockType, int xCoordinate, int yCoordinate, int roomId, int connectionId) {
        super(MessageTypes.PUT_BLOCK_MESSAGE, roomId, connectionId);
        this.blockType = blockType;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
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


}
