package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.client.ClientConnection;
import ru.kpfu.itis.belskaya.exceptions.ResourceLoadingException;
import ru.kpfu.itis.belskaya.gui.GameFrame;
import ru.kpfu.itis.belskaya.protocol.entities.BlockEntity;
import ru.kpfu.itis.belskaya.protocol.ioServices.InputService;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.exceptions.UnsupportedProtocolException;
import ru.kpfu.itis.belskaya.protocol.messages.*;

import javax.swing.*;
import java.lang.management.ThreadInfo;
import java.util.Timer;
import java.util.TimerTask;

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
                        BlockEntity block = new BlockEntity(messagePutBlock.getBlockType(), messagePutBlock.getxCoordinate(), messagePutBlock.getyCoordinate());
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
                        frame.getMinecraftPanel().initPlayers(messageJoinGame.getPlayers());
                        if (!connected) {
                            connection.setRoomId(message.getRoomId());
                            connected = true;
                            frame.initBlocks(messageJoinGame.getBlocks());
                        }
                        frame.initGameListener();
                        break;
                    }
                    case REMOVE_PLAYER_MESSAGE: {
                        MessageRemovePlayer messageRemovePlayer = (MessageRemovePlayer) message;
                        frame.getMinecraftPanel().removePlayer(messageRemovePlayer.getPlayerId());
                        break;
                    }
                    case CHOOSE_ROOM_MESSAGE: {
                        MessageChooseRoom messageChooseRoom = (MessageChooseRoom) message;
                        connection.setRoomId(messageChooseRoom.getRoomId());
                        frame.initMainPlayer(message.getConnectionId());
                        Integer roomId =  frame.chooseRoom(messageChooseRoom.getRoomIndexes());

                        if (roomId != -1) {
                            MessageJoinRoom messageJoinRoom = new MessageJoinRoom(roomId, message.getConnectionId());
                            connection.getOutputService().writeMessage(messageJoinRoom);
                        } else {
                            frame.closeFrame();
                        }
                        break;
                    }
                    case SERVER_IS_FULL_MESSAGE: {
                        frame.showErrorMessageDialog("At the moment the server is overloaded.");
                        frame.closeFrame();
                        break;
                    }
                }
            }
        } catch (MessageWorkException | ResourceLoadingException | UnsupportedProtocolException e) {
            JOptionPane.showMessageDialog(null, "Error", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            frame.closeFrame();
        }

    }


}
