package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;

public class ServerOverloadedListener extends AbstractServerEventListener {

    @Override
    public void handle(Message message) throws MessageWorkException {
        server.sendMessage(message, message.getConnectionId());
        server.removeConnectionFromRoom(-1, message.getConnectionId());
    }

}
