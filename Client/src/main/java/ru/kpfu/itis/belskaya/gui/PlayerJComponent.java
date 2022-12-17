package ru.kpfu.itis.belskaya.gui;

import ru.kpfu.itis.belskaya.exceptions.ResourceLoadingException;
import ru.kpfu.itis.belskaya.helpers.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PlayerJComponent extends PlayableJComponent {
    private int playerId;
    private Image playerAvatar;


    public int getPlayerId() {
        return playerId;
    }

    PlayerJComponent(int playerId, int x, int y) throws ResourceLoadingException {
        super(x, y);
        this.playerId = playerId;
        playerAvatar = ImageLoader.loadImage(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        playerAvatar = playerAvatar.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
        g.drawImage(playerAvatar, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
