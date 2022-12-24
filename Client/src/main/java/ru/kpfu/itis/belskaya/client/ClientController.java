package ru.kpfu.itis.belskaya.client;

import ru.kpfu.itis.belskaya.exceptions.ResourceLoadingException;
import ru.kpfu.itis.belskaya.gui.GameFrame;
import ru.kpfu.itis.belskaya.gui.MinecraftPanel;
import ru.kpfu.itis.belskaya.protocol.entities.BlockEntity;
import ru.kpfu.itis.belskaya.protocol.messages.*;

public class ClientController {

    private GameFrame frame;
    private MinecraftPanel panel;

    public ClientController(GameFrame frame) {
        this.frame = frame;
        panel = frame.getMinecraftPanel();
    }

    public void putPlayer(Message message) {
        MessagePutPlayer messagePutPlayer = (MessagePutPlayer) message;
        panel.putPlayer(messagePutPlayer.getPlayerId(), messagePutPlayer.getxCoordinate(), messagePutPlayer.getyCoordinate());
    }

    public void putBlock(Message message) throws ResourceLoadingException {
        MessagePutBlock messagePutBlock = (MessagePutBlock) message;
        BlockEntity block = new BlockEntity(messagePutBlock.getBlockType(), messagePutBlock.getxCoordinate(), messagePutBlock.getyCoordinate());
        panel.putBlock(block);
    }

    public void removeBlock(Message message) {
        MessageDeleteBlock messageDeleteBlock = (MessageDeleteBlock) message;
        panel.destroyBlock(messageDeleteBlock.getxCoordinate(), messageDeleteBlock.getyCoordinate());
    }

    public boolean join(Message message, boolean connected) throws ResourceLoadingException {
        MessageJoinGame messageJoinGame = (MessageJoinGame) message;
        panel.initPlayers(messageJoinGame.getPlayers());
        if (!connected) {
            connected = true;
            frame.initBlocks(messageJoinGame.getBlocks());
        }
        frame.initGameListener();
        return connected;
    }

    public void removePlayer(Message message) {
        MessageRemovePlayer messageRemovePlayer = (MessageRemovePlayer) message;
        frame.getMinecraftPanel().removePlayer(messageRemovePlayer.getPlayerId());
    }


}
