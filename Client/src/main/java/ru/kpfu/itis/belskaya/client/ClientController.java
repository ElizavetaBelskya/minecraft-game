package ru.kpfu.itis.belskaya.client;

import ru.kpfu.itis.belskaya.exceptions.ResourceLoadingException;
import ru.kpfu.itis.belskaya.gui.GameFrame;
import ru.kpfu.itis.belskaya.protocol.entities.BlockEntity;
import ru.kpfu.itis.belskaya.protocol.messages.*;

public class ClientController {

    private GameFrame frame;

    public ClientController(GameFrame frame) {
        this.frame = frame;
    }

    public void putPlayer(Message message) {
        MessagePutPlayer messagePutPlayer = (MessagePutPlayer) message;
        frame.getMinecraftPanel().putPlayer(messagePutPlayer.getPlayerId(), messagePutPlayer.getxCoordinate(), messagePutPlayer.getyCoordinate());
    }

    public void putBlock(Message message) throws ResourceLoadingException {
        MessagePutBlock messagePutBlock = (MessagePutBlock) message;
        BlockEntity block = new BlockEntity(messagePutBlock.getBlockType(), messagePutBlock.getxCoordinate(), messagePutBlock.getyCoordinate());
        frame.getMinecraftPanel().putBlock(block);
    }

    public void removeBlock(Message message) {
        MessageDeleteBlock messageDeleteBlock = (MessageDeleteBlock) message;
        frame.getMinecraftPanel().destroyBlock(messageDeleteBlock.getxCoordinate(), messageDeleteBlock.getyCoordinate());
    }

    public boolean join(Message message, boolean connected) throws ResourceLoadingException {
        MessageJoinGame messageJoinGame = (MessageJoinGame) message;
        frame.getMinecraftPanel().initPlayers(messageJoinGame.getPlayers());
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
