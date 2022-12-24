package ru.kpfu.itis.belskaya.protocol.entities;

public enum BlockType {
    AMETHYST(1, "amethyst.jpg"),
    BRICK(2, "brick.png"),
    DIAMOND(3, "dimond.png"),
    DIRT(4, "dirt.png"),
    LAVA(5, "lava.png"),
    RED_BRICK(6, "redBrick.png"),
    STONE(7, "stone.jpg"),
    TNT(8, "tnt.png");
    final int id;
    final String file;

    BlockType(int id, String file){
        this.id = id;
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public String getFile() {
        return file;
    }
}
