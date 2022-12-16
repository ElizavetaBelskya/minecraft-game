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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        int cellWidth = getWidth() / linesCount;
        int cellHeight = getHeight() / linesCount;
        for (int i = 1; i < linesCount; i++) {
            g.drawLine(i * cellWidth, 0, i * cellWidth, getHeight());
            g.drawLine(0, i * cellHeight, getWidth(), i * cellHeight);
        }
    }
}