package ru.kpfu.itis.belskaya.gui;

import ru.kpfu.itis.belskaya.exceptions.ResourceLoadingException;
import ru.kpfu.itis.belskaya.protocol.BlockEntity;
import ru.kpfu.itis.belskaya.protocol.PlayerEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MinecraftPanel extends MapPanel {
    private static final int LINES_COUNT = 20;
    private GameFrame frame;
    private ArrayList<PlayerJComponent> playerJComponents = new ArrayList<PlayerJComponent>();
    private static final Color GRASS = new Color(0,80,0);

    private ArrayList<JBlockPanel> blocks = new ArrayList<JBlockPanel>();

    private int squareSide;

    public PlayerJComponent getPlayerById(int id) {
        return playerJComponents.stream().filter(x -> x.getPlayerId() == id).findFirst().get();
    }

    public MinecraftPanel(GameFrame frame) {
        super(LINES_COUNT);
        this.frame = frame;
        setLayout(null);
        setBackground(GRASS);
    }

    private int getCellSide() {
        squareSide = Math.min(getHeight(), getWidth());
        int cellSide = squareSide / LINES_COUNT;
        return cellSide;
    }
    public void putPlayer(int playerId, int x, int y) {
        PlayerJComponent playerJComponent = getPlayerById(playerId);
        playerJComponent.setXCoordinate(x);
        playerJComponent.setYCoordinate(y);
        setBlockLocation(playerJComponent);
        repaint();
    }

    public void initPlayers(List<PlayerEntity> playersChanged) throws ResourceLoadingException {
        for (PlayerJComponent playerJComponent : playerJComponents) {
            remove(playerJComponent);
        }
        validate();
        playerJComponents = new ArrayList<>();
        for (PlayerEntity playerEntity : playersChanged) {
            PlayerJComponent playerJComponent = new PlayerJComponent(playerEntity.getId(), playerEntity.getxCoordinate(), playerEntity.getyCoordinate());
            playerJComponents.add(playerJComponent);
            add(playerJComponent);
            setBlockLocation(playerJComponent);
        }

    }

    public void removePlayer(int playerId) {
        PlayerJComponent player = getPlayerById(playerId);
        remove(player);
        playerJComponents.remove(player);
        validate();
        repaint();
    }

    private void setBlockLocation(PlayableJComponent blockEntity) {
        int x = blockEntity.getXCoordinate()*getCellSide();
        int y = blockEntity.getYCoordinate()*getCellSide();
        blockEntity.setSize(getCellSide(), getCellSide());
        blockEntity.setLocation(x, y);
    }

    public void putBlock(BlockEntity blockEntity) throws ResourceLoadingException {
        if (!(findComponentAt(blockEntity.getX()*getCellSide(), blockEntity.getY()*getCellSide()) instanceof JBlockPanel)) {
            JBlockPanel block = new JBlockPanel(blockEntity.getBlockType(), blockEntity.getX(), blockEntity.getY());
            add(block);
            blocks.add(block);
            setBlockLocation(block);
        }
    }

    public void destroyBlock(int x, int y) {
        if (findComponentAt(x*getCellSide(), y*getCellSide()) instanceof JBlockPanel) {
            JBlockPanel removedBlock = (JBlockPanel) findComponentAt(x* getCellSide(), y* getCellSide());
            remove(removedBlock);
            blocks.remove(removedBlock);
            validate();
            repaint();
        }
    }



    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (squareSide != Math.min(getHeight(), getWidth())) {
            for (PlayerJComponent p : playerJComponents) {
                add(p);
                setBlockLocation(p);
            }

            for (JBlockPanel block : blocks) {
                add(block);
                setBlockLocation(block);
            }
        }
    }


    public ArrayList<PlayerJComponent> getPlayers() {
        return playerJComponents;
    }


}
