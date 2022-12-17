package ru.kpfu.itis.belskaya.gui;

import ru.kpfu.itis.belskaya.exceptions.ResourceLoadingException;
import ru.kpfu.itis.belskaya.helpers.ImageLoader;

import java.awt.*;

public class JBlockPanel extends PlayableJComponent {

    private int typeId;
    private Image block;

    public JBlockPanel(int typeId) throws ResourceLoadingException {
        super(-1, -1);
        this.typeId = typeId;
        block = ImageLoader.loadImage(typeId);
    }

    public JBlockPanel(int typeId, int xCoordinate, int yCoordinate) throws ResourceLoadingException {
        super(xCoordinate, yCoordinate);
        this.typeId = typeId;
        block = ImageLoader.loadImage(typeId);
    }
    public int getTypeId() {
        return typeId;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        block = block.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT);
        g.drawImage(block, 0, 0, this.getWidth(), this.getHeight(), null);
    }



}
