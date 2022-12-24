package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.Server;
import ru.kpfu.itis.belskaya.ServerException;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;

public interface ServerEventListener {

    public void init(Server server);
    public void handle(Message message) throws MessageWorkException;

}
