package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.ServerException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;

public class RemovePlayerListener extends AbstractServerEventListener {

    @Override
    public void handle(Message message) throws ServerException {
        server.sendBroadCastMessage(message);
    }

}
