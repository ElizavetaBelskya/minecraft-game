package ru.kpfu.itis.belskaya.gui;

import ru.kpfu.itis.belskaya.exceptions.ResourceLoadingException;
import ru.kpfu.itis.belskaya.protocol.PlayerEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MinecraftPanel extends MapPanel {

    private static final int LINES_COUNT = 30;

    private GameFrame frame;

    private ArrayList<PlayerJComponent> playerJComponents = new ArrayList<PlayerJComponent>();

    private static final Color GRASS = new Color(0,80,0);

    private ArrayList<JBlockPanel> blocks = new ArrayList<JBlockPanel>();

    public PlayerJComponent getPlayerById(int id) {
        return playerJComponents.stream().filter(x -> x.getPlayerId() == id).findFirst().get();
    }

    public MinecraftPanel(GameFrame frame) {
        super(LINES_COUNT);
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
        PlayerJComponent playerJComponent = getPlayerById(playerId);
        playerJComponent.setXCoordinate(x);
        playerJComponent.setYCoordinate(y);
        repaint();
    }

    public void initPlayers(List<PlayerEntity> playersChanged) throws ResourceLoadingException {
        for (PlayerJComponent playerJComponent : playerJComponents) {
            remove(playerJComponent);
        }
        playerJComponents = new ArrayList<>();
        System.out.println(playerJComponents);
        for (PlayerEntity playerEntity : playersChanged) {
            PlayerJComponent playerJComponent = new PlayerJComponent(playerEntity.getId(), playerEntity.getxCoordinate(), playerEntity.getyCoordinate());
            playerJComponents.add(playerJComponent);
        }

        validate();
        repaint();
    }

    public void removePlayer(int playerId) {
        System.out.println(playerJComponents);
        PlayerJComponent player = getPlayerById(playerId);
        remove(player);
        playerJComponents.remove(player);
        validate();
        repaint();
    }

    private void setBlockLocation(PlayableJComponent blockEntity) {
        int x = blockEntity.getXCoordinate()*getCellWidth();
        int y = blockEntity.getYCoordinate()*getCellHeight();
        blockEntity.setSize(getCellWidth(), getCellHeight());
        blockEntity.setLocation(x, y);
    }

    public void putBlock(JBlockPanel block) {
        add(block);
        blocks.add(block);
        setBlockLocation(block);
    }

    public void destroyBlock(int x, int y) {
        if (findComponentAt(x*getCellWidth(), y*getCellHeight()) instanceof JBlockPanel) {
            JBlockPanel removedBlock = (JBlockPanel) findComponentAt(x*getCellWidth(), y*getCellHeight());
            remove(removedBlock);
            blocks.remove(removedBlock);
            validate();
            repaint();
        }
    }



    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for (PlayerJComponent p : playerJComponents) {
            add(p);
            setBlockLocation(p);
        }

        for (JBlockPanel block : blocks) {
            add(block);
            setBlockLocation(block);
        }

    }


    public ArrayList<PlayerJComponent> getPlayers() {
        return playerJComponents;
    }


}
