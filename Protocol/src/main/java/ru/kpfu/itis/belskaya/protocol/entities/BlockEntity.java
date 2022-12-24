package ru.kpfu.itis.belskaya.protocol.entities;

import java.io.Serializable;

public class BlockEntity implements Serializable {
    private BlockType blockType;
    private int x;
    private int y;

    public BlockEntity(BlockType blockType, int x, int y) {
        this.blockType = blockType;
        this.x = x;
        this.y = y;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "BlockEntity{" +
                "blockType=" + blockType +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
