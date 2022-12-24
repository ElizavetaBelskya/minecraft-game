package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.client.ClientConnection;
import ru.kpfu.itis.belskaya.client.ClientController;
import ru.kpfu.itis.belskaya.exceptions.ResourceLoadingException;
import ru.kpfu.itis.belskaya.gui.GameFrame;
import ru.kpfu.itis.belskaya.protocol.ioServices.InputService;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.exceptions.UnsupportedProtocolException;
import ru.kpfu.itis.belskaya.protocol.messages.*;

import javax.swing.*;

public class MessageProcessor implements Runnable {
    private boolean connected = false;

    private ClientController clientController;
    private InputService inputService;
    private ClientConnection connection;
    private GameFrame frame;
    private Thread thread;

    public MessageProcessor(ClientConnection connection, GameFrame frame) {
        this.inputService = connection.getInputService();
        this.connection = connection;
        this.frame = frame;
        clientController = new ClientController(frame);
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
                        clientController.putPlayer(message);
                        break;
                    }
                    case PUT_BLOCK_MESSAGE: {
                        clientController.putBlock(message);
                        break;
                    }
                    case DELETE_BLOCK_MESSAGE: {
                        clientController.removeBlock(message);
                        break;
                    }
                    case JOIN_MESSAGE: {
                        if (!connected) {
                            connection.setRoomId(message.getRoomId());
                        }
                        connected = clientController.join(message, connected);
                        break;
                    }
                    case REMOVE_PLAYER_MESSAGE: {
                        clientController.removePlayer(message);
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
