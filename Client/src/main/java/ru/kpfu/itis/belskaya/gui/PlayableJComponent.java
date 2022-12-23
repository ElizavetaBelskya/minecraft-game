package ru.kpfu.itis.belskaya.gui;

import javax.swing.*;

public class PlayableJComponent extends JPanel {
    private int xCoordinate;
    private int yCoordinate;

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }


    public PlayableJComponent(int xCoordinate, int yCoordinate) {
        this.xCoordinate =xCoordinate;
        this.yCoordinate = yCoordinate;
    }


}
