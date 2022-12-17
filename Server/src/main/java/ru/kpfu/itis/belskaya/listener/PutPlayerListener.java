package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.ServerException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;
import ru.kpfu.itis.belskaya.protocol.messages.MessagePutPlayer;

public class PutPlayerListener extends AbstractServerEventListener {

    @Override
    public void handle(int connectionId, Message message) throws ServerException {
        MessagePutPlayer messagePutPlayer = (MessagePutPlayer) message;
        server.changedPlayerCoordinates(messagePutPlayer.getPlayerId(),
                messagePutPlayer.getxCoordinate(),
                messagePutPlayer.getyCoordinate());
        server.sendBroadCastMessage(messagePutPlayer);
    }


}
