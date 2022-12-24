package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.server.Server;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;

public interface ServerEventListener {

    public void init(Server server);
    public void handle(Message message) throws MessageWorkException;

}
