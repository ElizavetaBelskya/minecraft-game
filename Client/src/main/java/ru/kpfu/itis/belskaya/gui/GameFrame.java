package ru.kpfu.itis.belskaya.gui;

import ru.kpfu.itis.belskaya.Client;
import ru.kpfu.itis.belskaya.exceptions.ClientException;
import ru.kpfu.itis.belskaya.protocol.messages.MessageDeleteBlock;
import ru.kpfu.itis.belskaya.protocol.messages.MessagePutBlock;
import ru.kpfu.itis.belskaya.protocol.messages.MessagePutPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame {

    public GameFrame(Client client) {
        this.client = client;
    }
    private Player mainPlayer;
    private final static int DEFAULT_WIDTH = 600;
    private final static int DEFAULT_HEIGHT = 500;
    private Client client;
    private GameListener listener;
    private int blockId = 1;
    private MinecraftPanel minecraftPanel;
    private static final String TITLE = "Minecraft";
    private GamePadPanel gamePadPanel;

    public void createGUI() {
        setTitle(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setBounds(0,0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        createMinecraftPanel();
        createGamePadPanel();
        revalidate();
    }

    public void createGamePadPanel() {
        gamePadPanel = new GamePadPanel(this);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        constraints.weightx = 1;
        constraints.gridx = 5;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 5;
        getContentPane().add(gamePadPanel, constraints);
    }

    public void createMinecraftPanel() {
        minecraftPanel = new MinecraftPanel(this);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        constraints.weightx = 5;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 5;
        constraints.gridheight = 5;
        getContentPane().add(minecraftPanel, constraints);
    }

    public void initMainPlayer(int id) {
        mainPlayer = minecraftPanel.getPlayerById(id);
    }


    public void initGameListener() {
        if (listener == null) {
            listener = new GameListener();
            this.addKeyListener(listener);
        }
    }

    public MinecraftPanel getMinecraftPanel() {
        return minecraftPanel;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    private class GameListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            int mods = e.getModifiers();
            if ((mods & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
                switch (key) {
                    case KeyEvent.VK_A:
                        MessageDeleteBlock message = new MessageDeleteBlock(mainPlayer.getXCoordinate()-1, mainPlayer.getYCoordinate());
                        //TODO: это что вообще
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_W:
                        message = new MessageDeleteBlock(mainPlayer.getXCoordinate(), mainPlayer.getYCoordinate() - 1);
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_D:
                        message = new MessageDeleteBlock(mainPlayer.getXCoordinate()+1, mainPlayer.getYCoordinate());
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_S:
                        message = new MessageDeleteBlock(mainPlayer.getXCoordinate(), mainPlayer.getYCoordinate()+1);
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                }
            } else {
                switch (key) {
                    case KeyEvent.VK_LEFT:
                        movePlayerLeft(mainPlayer);
                        MessagePutPlayer message = new MessagePutPlayer(mainPlayer.getId(), mainPlayer.getXCoordinate(), mainPlayer.getYCoordinate());
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        movePlayerRight(mainPlayer);
                        message = new MessagePutPlayer(mainPlayer.getId(), mainPlayer.getXCoordinate(), mainPlayer.getYCoordinate());
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_UP:
                        movePlayerUp(mainPlayer);
                        message = new MessagePutPlayer(mainPlayer.getId(), mainPlayer.getXCoordinate(), mainPlayer.getYCoordinate());
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        movePlayerDown(mainPlayer);
                        message = new MessagePutPlayer(mainPlayer.getId(), mainPlayer.getXCoordinate(), mainPlayer.getYCoordinate());
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_A:
                        MessagePutBlock messagePutBlock = new MessagePutBlock(blockId, mainPlayer.getXCoordinate()-1, mainPlayer.getYCoordinate());
                        try {
                            client.sendMessage(messagePutBlock);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_W:
                        messagePutBlock = new MessagePutBlock(blockId, mainPlayer.getXCoordinate(), mainPlayer.getYCoordinate()-1);
                        try {
                            client.sendMessage(messagePutBlock);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_D:
                        messagePutBlock = new MessagePutBlock(blockId, mainPlayer.getXCoordinate()+1, mainPlayer.getYCoordinate());
                        try {
                            client.sendMessage(messagePutBlock);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_S:
                        messagePutBlock = new MessagePutBlock(blockId, mainPlayer.getXCoordinate(), mainPlayer.getYCoordinate()+1);
                        try {
                            client.sendMessage(messagePutBlock);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                }
            }
        }


        public void movePlayerUp(Player player) {
            player.setYCoordinate(player.getYCoordinate()-1);
            if (player.getYCoordinate() < 0) {
                player.setYCoordinate(29);
            }
        }

        public void movePlayerDown(Player player) {
            player.setYCoordinate(player.getYCoordinate()+1);
            if (player.getYCoordinate() > 29) {
                player.setYCoordinate(0);
            }
        }

        public void movePlayerRight(Player player) {
            player.setXCoordinate(player.getXCoordinate() + 1);
            if (player.getXCoordinate() > 29) {
                player.setXCoordinate(0);
            }
        }

        public void movePlayerLeft(Player player) {
            player.setXCoordinate(player.getXCoordinate() - 1);
            if (player.getXCoordinate() < 0) {
                player.setXCoordinate(29);
            }
        }

    }


}
