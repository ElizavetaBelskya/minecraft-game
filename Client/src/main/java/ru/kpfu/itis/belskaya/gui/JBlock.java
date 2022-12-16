package ru.kpfu.itis.belskaya.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class JBlock extends JPanel {

    private int id;

    private int xCoordinate;
    private int yCoordinate;

    private Image block;

    protected JBlock() {
    }

    public JBlock(int id, int xCoordinate, int yCoordinate) {
        this.id = id;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public JBlock(int id) {
        this.id = id;
    }

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

    public int getId() {
        return id;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        try {
            block = ImageIO.read(new File(getPicture(this.id))).
                    getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g.drawImage(block, 0, 0, getWidth(), getHeight(), null);

    }


    public void create(Graphics graphics) {

        setBackground(Color.LIGHT_GRAY);

        try {
            block = ImageIO.read(new File(getPicture(this.id)))
                    .getScaledInstance(this.getWidth(), getHeight(), Image.SCALE_DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        graphics.drawImage(block, 0, 0, this.getWidth(), this.getHeight(), this);

    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        create(g);
    }


    public String getPicture(int id) {
        String resource;
        switch (id) {
            case 1:
                resource = "amethyst.png";
                break;
            case 2:
                resource = "brick.png";
                break;
            case 3:
                resource = "dimond.png";
                break;
            case 4:
                resource = "dirt.png";
                break;
            case 5:
                resource = "lava.png";
                break;
            case 6:
                resource = "redBrick.png";
                break;
            case 7:
                resource = "stone.jpg";
                break;
            case 8:
                resource = "tnt.png";
                break;
            default:
                resource = "dirt.png";
        }
        String resLink;
        try {
            resLink = URLDecoder.decode(getClass().getClassLoader().getResource(resource).getPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return resLink;
    }


}
