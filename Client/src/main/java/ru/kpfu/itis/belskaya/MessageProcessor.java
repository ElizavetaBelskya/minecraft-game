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
                    case 1: {
                        MessagePutPlayer messagePutPlayer = (MessagePutPlayer) message;
                        frame.getMinecraftPanel().putPlayer(messagePutPlayer.getPlayerId(), messagePutPlayer.getxCoordinate(), messagePutPlayer.getyCoordinate());
                        break;
                    }
                    case 2: {
                        MessagePutBlock messagePutBlock = (MessagePutBlock) message;
                        JBlockPanel block = new JBlockPanel(messagePutBlock.getBlockId(), messagePutBlock.getxCoordinate(), messagePutBlock.getyCoordinate());
                        frame.getMinecraftPanel().putBlock(block);
                        break;
                    }
                    case 3: {
                        MessageDeleteBlock messageDeleteBlock = (MessageDeleteBlock) message;
                        frame.getMinecraftPanel().destroyBlock(messageDeleteBlock.getxCoordinate(), messageDeleteBlock.getyCoordinate());
                        break;
                    }
                    case 4: {
                        MessageJoinGame messageJoinGame = (MessageJoinGame) message;
                        frame.getMinecraftPanel().initPlayer(messageJoinGame.getPlayers());
                        if (!connected) {
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
