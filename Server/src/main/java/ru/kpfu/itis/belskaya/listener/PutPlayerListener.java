package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;
import ru.kpfu.itis.belskaya.protocol.messages.MessagePutPlayer;

public class PutPlayerListener extends AbstractServerEventListener {

    @Override
    public void handle(Message message) throws MessageWorkException {
        MessagePutPlayer messagePutPlayer = (MessagePutPlayer) message;
        server.changedPlayerCoordinates(messagePutPlayer);
        server.sendBroadCastMessage(messagePutPlayer);
    }


}
