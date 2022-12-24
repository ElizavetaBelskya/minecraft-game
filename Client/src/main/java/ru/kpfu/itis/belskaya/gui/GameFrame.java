package ru.kpfu.itis.belskaya.gui;

import ru.kpfu.itis.belskaya.client.Client;
import ru.kpfu.itis.belskaya.exceptions.ClientException;
import ru.kpfu.itis.belskaya.exceptions.ResourceLoadingException;
import ru.kpfu.itis.belskaya.protocol.entities.BlockEntity;
import ru.kpfu.itis.belskaya.protocol.entities.BlockType;
import ru.kpfu.itis.belskaya.protocol.messages.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameFrame extends JFrame {
    private final static int DEFAULT_WIDTH = 600;
    private final static int DEFAULT_HEIGHT = 500;
    private int MIN_COORDINATE = 0;
    private int MAX_COORDINATE = 20;
    private int mainPlayerId;
    private Client client;
    private GameListener listener;
    private BlockType blockId = BlockType.STONE;
    private MinecraftPanel minecraftPanel;
    private static final String TITLE = "MyCraft";
    private GamePadPanel gamePadPanel;

    public void createGUI(Client client) {
        this.client = client;
        setTitle(TITLE);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int roomId = client.getConnection().getRoomId();
                int connectionId = mainPlayerId;
                MessageRemovePlayer messageRemovePlayer = new MessageRemovePlayer(roomId, connectionId);
                try {
                    client.sendMessage(messageRemovePlayer);
                } catch (ClientException ex) {}
                client.getConnection().closeConnection();
                e.getWindow().dispose();
                System.exit(0);
            }
        });
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
        constraints.gridx = 6;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 6;
        getContentPane().add(gamePadPanel, constraints);
    }

    public void createMinecraftPanel() {
        minecraftPanel = new MinecraftPanel();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1;
        constraints.weightx = 6;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 6;
        constraints.gridheight = 6;
        getContentPane().add(minecraftPanel, constraints);
    }

    public void initMainPlayer(int id) {
        mainPlayerId = id;
    }

    public void initBlocks(List<BlockEntity> blockEntities) throws ResourceLoadingException {
        for (BlockEntity blockEntity : blockEntities) {
            minecraftPanel.putBlock(blockEntity);
        }
    }

    private PlayerJComponent mainPlayerJComponent() {
        return minecraftPanel.getPlayerById(mainPlayerId);
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

    public void closeFrame() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public Integer chooseRoom(Integer[] roomId) {
        Integer room = -1;
        if (roomId.length == 0) {
            Integer ans = JOptionPane.showConfirmDialog(this,"There are no available rooms at the moment. Create a new one?");
            if (ans == 0) {
                room = -2;
            }
        } else {
            List<String> selectionOptions = new ArrayList<>();
            selectionOptions.addAll(Arrays.stream(roomId).map(x -> x.toString()).collect(Collectors.toList()));
            selectionOptions.add("Create a new room");
            String result = (String) JOptionPane.showInputDialog(
                    this,
                    "Choose room, please",
                    "Room",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    selectionOptions.toArray(),
                    selectionOptions.get(0));
            if (result != null) {
                if (result.equals("Create a new room")) {
                    room = -2;
                } else {
                    room = Integer.parseInt(result);
                }
            }
        }

        return room;
    }

    private class GameListener implements KeyListener {
        int roomId = client.getConnection().getRoomId();
        int connectionId = mainPlayerId;

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
            try {
                if ((mods & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
                    int x;
                    int y;
                    switch (key) {
                        case KeyEvent.VK_A:
                            x = mainPlayerJComponent().getXCoordinate() - 1;
                            y =  mainPlayerJComponent().getYCoordinate();
                            break;
                        case KeyEvent.VK_W:
                            x = mainPlayerJComponent().getXCoordinate();
                            y = mainPlayerJComponent().getYCoordinate() - 1;
                            break;
                        case KeyEvent.VK_D:
                            x = mainPlayerJComponent().getXCoordinate() + 1;
                            y = mainPlayerJComponent().getYCoordinate();
                            break;
                        case KeyEvent.VK_S:
                            x = mainPlayerJComponent().getXCoordinate();
                            y = mainPlayerJComponent().getYCoordinate() + 1;
                            break;
                        default:
                            x = -100;
                            y = -100;
                    }
                    if (x != -100 && y != -100 && getMinecraftPanel().getBlock(x, y) != null) {
                        MessageDeleteBlock message = new MessageDeleteBlock(x, y, roomId, connectionId);
                        client.sendMessage(message);
                    }
                } else if ((mods & ActionEvent.SHIFT_MASK) == ActionEvent.SHIFT_MASK) {
                    int x;
                    int y;
                    switch (key) {
                        case KeyEvent.VK_A:
                            x = mainPlayerJComponent().getXCoordinate() - 1;
                            y =  mainPlayerJComponent().getYCoordinate();
                            break;
                        case KeyEvent.VK_W:
                            x = mainPlayerJComponent().getXCoordinate();
                            y = mainPlayerJComponent().getYCoordinate() - 1;
                            break;
                        case KeyEvent.VK_D:
                            x = mainPlayerJComponent().getXCoordinate() + 1;
                            y = mainPlayerJComponent().getYCoordinate();
                            break;
                        case KeyEvent.VK_S:
                            x = mainPlayerJComponent().getXCoordinate();
                            y = mainPlayerJComponent().getYCoordinate() + 1;
                            break;
                        default:
                            x = -100;
                            y = -100;
                    }
                    if (x != -100 && y != -100 && getMinecraftPanel().getBlock(x, y) != null && getMinecraftPanel().getBlock(x, y).getType().equals(BlockType.TNT)) {
                        MessageExplode message = new MessageExplode(x, y, roomId, connectionId);
                        client.sendMessage(message);
                    }
                } else {
                    switch (key) {
                        case KeyEvent.VK_LEFT:
                            Point point = movePlayerLeft(mainPlayerJComponent());
                            MessagePutPlayer message = new MessagePutPlayer(point.x, point.y, roomId, connectionId);
                            client.sendMessage(message);
                            break;
                        case KeyEvent.VK_RIGHT:
                            point = movePlayerRight(mainPlayerJComponent());
                            message = new MessagePutPlayer(point.x, point.y, roomId, connectionId);
                            client.sendMessage(message);
                            break;
                        case KeyEvent.VK_UP:
                            point = movePlayerUp(mainPlayerJComponent());
                            message = new MessagePutPlayer(point.x, point.y, roomId, connectionId);
                            client.sendMessage(message);
                            break;
                        case KeyEvent.VK_DOWN:
                            point = movePlayerDown(mainPlayerJComponent());
                            message = new MessagePutPlayer(point.x, point.y, roomId, connectionId);
                            client.sendMessage(message);
                            break;
                        case KeyEvent.VK_A:
                            MessagePutBlock messagePutBlock = new MessagePutBlock(blockId, mainPlayerJComponent().getXCoordinate()-1, mainPlayerJComponent().getYCoordinate(), roomId, connectionId);
                            client.sendMessage(messagePutBlock);
                            break;
                        case KeyEvent.VK_W:
                            messagePutBlock = new MessagePutBlock(blockId, mainPlayerJComponent().getXCoordinate(), mainPlayerJComponent().getYCoordinate()-1, roomId, connectionId);
                            client.sendMessage(messagePutBlock);
                            break;
                        case KeyEvent.VK_D:
                            messagePutBlock = new MessagePutBlock(blockId, mainPlayerJComponent().getXCoordinate()+1, mainPlayerJComponent().getYCoordinate(), roomId, connectionId);
                            client.sendMessage(messagePutBlock);
                            break;
                        case KeyEvent.VK_S:
                            messagePutBlock = new MessagePutBlock(blockId, mainPlayerJComponent().getXCoordinate(), mainPlayerJComponent().getYCoordinate()+1, roomId, connectionId);
                            client.sendMessage(messagePutBlock);
                            break;
                    }
            }

            } catch (ClientException ex) {
                throw new RuntimeException(ex);
            }
        }


        public Point movePlayerUp(PlayerJComponent playerJComponent) {
            int y = (playerJComponent.getYCoordinate()-1);
            if (y < MIN_COORDINATE) {
                y = MAX_COORDINATE-1;
            }
            return new Point(playerJComponent.getXCoordinate(), y);
        }

        public Point movePlayerDown(PlayerJComponent playerJComponent) {
            int y = (playerJComponent.getYCoordinate()+1);
            if (y > MAX_COORDINATE-1) {
                y = MIN_COORDINATE;
            }
            return new Point(playerJComponent.getXCoordinate(), y);
        }

        public Point movePlayerRight(PlayerJComponent playerJComponent) {
            int x = playerJComponent.getXCoordinate() + 1;
            if (x > MAX_COORDINATE-1) {
                x = MIN_COORDINATE;
            }
            return new Point(x, playerJComponent.getYCoordinate());
        }

        public Point movePlayerLeft(PlayerJComponent playerJComponent) {
            int x = playerJComponent.getXCoordinate() - 1;
            if (x < MIN_COORDINATE) {
                x = MAX_COORDINATE-1;
            }
            return new Point(x, playerJComponent.getYCoordinate());
        }

    }

}
