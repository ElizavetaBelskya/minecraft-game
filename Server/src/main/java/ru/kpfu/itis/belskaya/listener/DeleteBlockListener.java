package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.ServerException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;
import ru.kpfu.itis.belskaya.protocol.messages.MessageDeleteBlock;

public class DeleteBlockListener extends AbstractServerEventListener {

    @Override
    public void handle(Message message) throws ServerException {
        MessageDeleteBlock messageDeleteBlock = (MessageDeleteBlock) message;
        server.removeBlockToRoom(messageDeleteBlock);
        server.sendBroadCastMessage(message);
    }

}
