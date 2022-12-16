package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.ServerException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;

public class JoinListener extends AbstractServerEventListener {

    @Override
    public void handle(int connectionId, Message message) throws ServerException {
        server.sendBroadCastMessage(message);
    }

}