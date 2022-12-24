package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;
import ru.kpfu.itis.belskaya.protocol.messages.MessagePutBlock;

public class PutBlockListener extends AbstractServerEventListener {

    @Override
    public void handle(Message message) throws MessageWorkException {
        MessagePutBlock blockMessage = (MessagePutBlock) message;
        server.addBlockToRoom(blockMessage);
        server.sendBroadCastMessage(message);
    }

}
