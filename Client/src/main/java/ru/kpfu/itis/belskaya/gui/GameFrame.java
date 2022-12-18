package ru.kpfu.itis.belskaya.gui;

import ru.kpfu.itis.belskaya.Client;
import ru.kpfu.itis.belskaya.exceptions.ClientException;
import ru.kpfu.itis.belskaya.protocol.BlockType;
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
    private PlayerJComponent mainPlayerJComponent;
    private final static int DEFAULT_WIDTH = 600;
    private final static int DEFAULT_HEIGHT = 500;
    private Client client;
    private GameListener listener;
    private BlockType blockId = BlockType.STONE;
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
        mainPlayerJComponent = minecraftPanel.getPlayerById(id);
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

    public void setBlockType(BlockType blockId) {
        this.blockId = blockId;
    }


    public void showErrorMessageDialog(String errorMessage) {
        JOptionPane.showMessageDialog(null, "Error", errorMessage, JOptionPane.ERROR_MESSAGE);
    }

    private class GameListener implements KeyListener {
        int roomId = client.getConnection().getRoomId();

        int connectionId = mainPlayerJComponent.getPlayerId();
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
                        MessageDeleteBlock message = new MessageDeleteBlock(mainPlayerJComponent.getXCoordinate()-1, mainPlayerJComponent.getYCoordinate(), roomId, connectionId);
                        //TODO:это что вообще
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_W:
                        message = new MessageDeleteBlock(mainPlayerJComponent.getXCoordinate(), mainPlayerJComponent.getYCoordinate() - 1, roomId, connectionId);
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_D:
                        message = new MessageDeleteBlock(mainPlayerJComponent.getXCoordinate()+1, mainPlayerJComponent.getYCoordinate(), roomId, connectionId);
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_S:
                        message = new MessageDeleteBlock(mainPlayerJComponent.getXCoordinate(), mainPlayerJComponent.getYCoordinate()+1, roomId, connectionId);
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
                        movePlayerLeft(mainPlayerJComponent);
                        MessagePutPlayer message = new MessagePutPlayer(mainPlayerJComponent.getPlayerId(), mainPlayerJComponent.getXCoordinate(), mainPlayerJComponent.getYCoordinate(), roomId, connectionId);
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        movePlayerRight(mainPlayerJComponent);
                        message = new MessagePutPlayer(mainPlayerJComponent.getPlayerId(), mainPlayerJComponent.getXCoordinate(), mainPlayerJComponent.getYCoordinate(), roomId, connectionId);
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_UP:
                        movePlayerUp(mainPlayerJComponent);
                        message = new MessagePutPlayer(mainPlayerJComponent.getPlayerId(), mainPlayerJComponent.getXCoordinate(), mainPlayerJComponent.getYCoordinate(), roomId, connectionId);
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        movePlayerDown(mainPlayerJComponent);
                        message = new MessagePutPlayer(mainPlayerJComponent.getPlayerId(), mainPlayerJComponent.getXCoordinate(), mainPlayerJComponent.getYCoordinate(), roomId, connectionId);
                        try {
                            client.sendMessage(message);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_A:
                        MessagePutBlock messagePutBlock = new MessagePutBlock(blockId, mainPlayerJComponent.getXCoordinate()-1, mainPlayerJComponent.getYCoordinate(), roomId, connectionId);
                        try {
                            client.sendMessage(messagePutBlock);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_W:
                        messagePutBlock = new MessagePutBlock(blockId, mainPlayerJComponent.getXCoordinate(), mainPlayerJComponent.getYCoordinate()-1, roomId, connectionId);
                        try {
                            client.sendMessage(messagePutBlock);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_D:
                        messagePutBlock = new MessagePutBlock(blockId, mainPlayerJComponent.getXCoordinate()+1, mainPlayerJComponent.getYCoordinate(), roomId, connectionId);
                        try {
                            client.sendMessage(messagePutBlock);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case KeyEvent.VK_S:
                        messagePutBlock = new MessagePutBlock(blockId, mainPlayerJComponent.getXCoordinate(), mainPlayerJComponent.getYCoordinate()+1, roomId, connectionId);
                        try {
                            client.sendMessage(messagePutBlock);
                        } catch (ClientException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                }
            }
        }


        public void movePlayerUp(PlayerJComponent playerJComponent) {
            playerJComponent.setYCoordinate(playerJComponent.getYCoordinate()-1);
            if (playerJComponent.getYCoordinate() < 0) {
                playerJComponent.setYCoordinate(29);
            }
        }

        public void movePlayerDown(PlayerJComponent playerJComponent) {
            playerJComponent.setYCoordinate(playerJComponent.getYCoordinate()+1);
            if (playerJComponent.getYCoordinate() > 29) {
                playerJComponent.setYCoordinate(0);
            }
        }

        public void movePlayerRight(PlayerJComponent playerJComponent) {
            playerJComponent.setXCoordinate(playerJComponent.getXCoordinate() + 1);
            if (playerJComponent.getXCoordinate() > 29) {
                playerJComponent.setXCoordinate(0);
            }
        }

        public void movePlayerLeft(PlayerJComponent playerJComponent) {
            playerJComponent.setXCoordinate(playerJComponent.getXCoordinate() - 1);
            if (playerJComponent.getXCoordinate() < 0) {
                playerJComponent.setXCoordinate(29);
            }
        }

    }


}
