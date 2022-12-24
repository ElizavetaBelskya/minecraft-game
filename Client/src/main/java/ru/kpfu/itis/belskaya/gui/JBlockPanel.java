package ru.kpfu.itis.belskaya.gui;

import ru.kpfu.itis.belskaya.exceptions.ResourceLoadingException;
import ru.kpfu.itis.belskaya.helpers.ImageLoader;
import ru.kpfu.itis.belskaya.protocol.entities.BlockType;
import java.awt.*;

public class JBlockPanel extends PlayableJComponent {

    private BlockType type;
    private Image block;

    public JBlockPanel(BlockType type) throws ResourceLoadingException {
        super(-1, -1);
        this.type = type;
        block = ImageLoader.loadImage(type.getFile());
    }

    public JBlockPanel(BlockType type, int xCoordinate, int yCoordinate) throws ResourceLoadingException {
        super(xCoordinate, yCoordinate);
        this.type = type;
        block = ImageLoader.loadImage(type.getFile());
    }

    public BlockType getType() {
        return type;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        block = block.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT);
        g.drawImage(block, 0, 0, this.getWidth(), this.getHeight(), null);
    }



}
