package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.exceptions.ResourceLoadingException;
import ru.kpfu.itis.belskaya.gui.GameFrame;
import ru.kpfu.itis.belskaya.gui.JBlockPanel;
import ru.kpfu.itis.belskaya.protocol.InputService;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.exceptions.UnsupportedProtocolException;
import ru.kpfu.itis.belskaya.protocol.messages.*;

public class MessageProcessor implements Runnable {
    private boolean connected = false;
    private InputService inputService;
    private ClientConnection connection;
    private GameFrame frame;
    private Thread thread;

    public MessageProcessor(ClientConnection connection, GameFrame frame) {
        this.inputService = connection.getInputService();
        this.connection = connection;
        this.frame = frame;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        Message message;
        try {
            while ((message = inputService.readMessage()) != null) {
                switch (message.getMessageType()) {
                    case PUT_PLAYER_MESSAGE: {
                        MessagePutPlayer messagePutPlayer = (MessagePutPlayer) message;
                        frame.getMinecraftPanel().putPlayer(messagePutPlayer.getPlayerId(), messagePutPlayer.getxCoordinate(), messagePutPlayer.getyCoordinate());
                        break;
                    }
                    case PUT_BLOCK_MESSAGE: {
                        MessagePutBlock messagePutBlock = (MessagePutBlock) message;
                        JBlockPanel block = new JBlockPanel(messagePutBlock.getBlockType(), messagePutBlock.getxCoordinate(), messagePutBlock.getyCoordinate());
                        frame.getMinecraftPanel().putBlock(block);
                        break;
                    }
                    case DELETE_BLOCK_MESSAGE: {
                        MessageDeleteBlock messageDeleteBlock = (MessageDeleteBlock) message;
                        frame.getMinecraftPanel().destroyBlock(messageDeleteBlock.getxCoordinate(), messageDeleteBlock.getyCoordinate());
                        break;
                    }
                    case JOIN_MESSAGE: {
                        MessageJoinGame messageJoinGame = (MessageJoinGame) message;
                        frame.getMinecraftPanel().initPlayer(messageJoinGame.getPlayers());
                        if (!connected) {
                            connection.setRoomId(message.getRoomId());
                            connected = true;
                            frame.initMainPlayer(messageJoinGame.getConnectionId());

                        }
                        frame.initGameListener();
                        break;
                    }
                }

            }
        } catch (MessageWorkException | ResourceLoadingException | UnsupportedProtocolException e) {
            frame.showErrorMessageDialog(e.getMessage());
        }
    }


}
