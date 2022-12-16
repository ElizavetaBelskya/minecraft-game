package ru.kpfu.itis.belskaya.gui;

import ru.kpfu.itis.belskaya.protocol.PlayerEntity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MinecraftPanel extends MapPanel {

    private static final int LINES_COUNT = 30;

    private GameFrame frame;

    public static int defaultPlayerX = 15;

    public static int defaultPlayerY = 15;

    private ArrayList<Player> players = new ArrayList<Player>();

    private final Color GRASS = new Color(0,80,0);

    private ArrayList<JBlock> blocks = new ArrayList<JBlock>();

    public Player getPlayerById(int id) {
        return players.stream().filter(x -> x.getId() == id).findFirst().get();
    }

    public MinecraftPanel(GameFrame frame) {
        super(LINES_COUNT);
        players.add(new Player(0, defaultPlayerX, defaultPlayerY));
        putPlayer(0,defaultPlayerX, defaultPlayerY);
        this.frame = frame;
        setLayout(null);
        setBackground(GRASS);
    }



    private int getCellWidth() {
        return getWidth() / LINES_COUNT;
    }

    private int getCellHeight() {
        return getHeight() / LINES_COUNT;
    }

    public void putPlayer(int playerId, int x, int y) {
        Player player = getPlayerById(playerId);
        player.setXCoordinate(x);
        player.setYCoordinate(y);
        add(player);
        setBlockLocation(player);
        repaint();
    }

    public void initPlayer(List<PlayerEntity> playersChanged) {
        for (PlayerEntity playerEntity : playersChanged) {
            Player player = new Player(playerEntity.getId(), playerEntity.getxCoordinate(), playerEntity.getyCoordinate());
            players.add(player);
        }
        repaint();
    }

    private void setBlockLocation(JBlock blockEntity) {
        int x = blockEntity.getXCoordinate()*getCellWidth();
        int y = blockEntity.getYCoordinate()*getCellHeight();
        blockEntity.setSize(getCellWidth(), getCellHeight());
        blockEntity.setLocation(x, y);
        repaint();
    }


    public void movePlayerUp(Player player) {
        player.setYCoordinate(player.getYCoordinate()-1);
        if (player.getYCoordinate() < 0) {
            player.setYCoordinate(29);
        }
        setBlockLocation(player);
    }

    public void movePlayerDown(Player player) {
        player.setYCoordinate(player.getYCoordinate()+1);
        if (player.getYCoordinate() > 29) {
            player.setYCoordinate(0);
        }
        setBlockLocation(player);
    }

    public void movePlayerRight(Player player) {
        player.setXCoordinate(player.getXCoordinate() + 1);
        if (player.getXCoordinate() > 29) {
            player.setXCoordinate(0);
        }
        setBlockLocation(player);
    }

    public void movePlayerLeft(Player player) {
        player.setXCoordinate(player.getXCoordinate() - 1);
        if (player.getXCoordinate() < 0) {
            player.setXCoordinate(29);
        }
        setBlockLocation(player);
    }


    public void putBlock(JBlock block) {
        add(block);
        blocks.add(block);
        setBlockLocation(block);
    }

    public void destroyBlock(int x, int y) {
        if (findComponentAt(x*getCellWidth(), y*getCellHeight()) instanceof JBlock) {
            JBlock removedBlock = (JBlock) findComponentAt(x*getCellWidth(), y*getCellHeight());
            remove(removedBlock);
            blocks.remove(removedBlock);
            validate();
            repaint();
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for (Player p : players) {
            setBlockLocation(p);
        }

        for (JBlock block : blocks) {
            setBlockLocation(block);
        }
    }


    public ArrayList<Player> getPlayers() {
        return players;
    }

}
