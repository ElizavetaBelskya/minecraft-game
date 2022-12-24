package ru.kpfu.itis.belskaya.gui;

import javax.swing.*;
import java.awt.*;

class MapPanel extends JPanel {
    private final int linesCount;
    private int startX = 0;
    private int startY = 0;

    public MapPanel(int linesCount) {
        this.linesCount = linesCount;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        int squareSide = Math.min(getHeight(), getWidth());
        if (getHeight() > getWidth()) {
            startX = 0;
            startY = (getHeight() - squareSide)/2;
        } else {
            startX = (getWidth() - squareSide)/2;
            startY = 0;
        }
        int cellSide = squareSide / linesCount;
        for (int i = 0; i <= linesCount; i++) {
            g.drawLine(startX + i * cellSide, startY, startX + i*cellSide, startY + linesCount*cellSide);
            g.drawLine(startX, startY + i * cellSide, startX + linesCount*cellSide, startY + i * cellSide);
        }
    }

//
//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.setColor(Color.GRAY);
//        int squareSide = Math.min(getHeight(), getWidth());
//        int cellSide = squareSide / linesCount;
//        for (int i = 1; i <= linesCount; i++) {
//            g.drawLine(i * cellSide, 0, linesCount * cellSide, i*cellSide);
//            g.drawLine(0, i * cellSide, linesCount*cellSide, i * cellSide);
//        }
//    }


//    Визуально интересный баг
//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.setColor(Color.GRAY);
//        int squareSide = Math.min(getHeight(), getWidth());
//        int cellSide = squareSide / linesCount;
//        for (int i = 1; i <= linesCount; i++) {
//            g.drawLine(i * cellSide, 0, i * cellSide, i*cellSide);
//            g.drawLine(0, i * cellSide, i*cellSide, i * cellSide);
//        }
//    }


}