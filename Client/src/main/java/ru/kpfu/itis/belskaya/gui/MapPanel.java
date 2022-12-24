package ru.kpfu.itis.belskaya.gui;

import javax.swing.*;
import java.awt.*;

class MapPanel extends JPanel {
    private final int linesCount;

    public MapPanel(int linesCount) {
        this.linesCount = linesCount;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        int squareSide = Math.min(getHeight(), getWidth());
        int cellSide = squareSide / linesCount;
        for (int i = 1; i <= linesCount; i++) {
            g.drawLine(i * cellSide, 0, i*cellSide, linesCount*cellSide);
            g.drawLine(0, i * cellSide, linesCount*cellSide, i * cellSide);
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