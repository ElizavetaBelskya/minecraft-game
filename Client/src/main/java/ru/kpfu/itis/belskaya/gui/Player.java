package ru.kpfu.itis.belskaya.gui;

import ru.kpfu.itis.belskaya.gui.JBlock;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Player extends JBlock {
    private Image player;
    private final String playerAvatar;

    Player(int id, int x, int y) {
        super(id, x, y);
    }

    //TODO: это что вообще
    {
        try {
            playerAvatar = URLDecoder.decode(getClass().getClassLoader().getResource("stiv.png").getPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        try {
            player = ImageIO.read(new File(playerAvatar)).
                    getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g.drawImage(player, 0, 0, getWidth(), getHeight(), null);

    }


    @Override
    public String toString() {
        return super.toString();
    }
}
