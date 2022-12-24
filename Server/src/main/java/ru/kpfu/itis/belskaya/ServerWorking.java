package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.listener.ServerEventListener;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;

public interface ServerWorking {

    void sendMessage(Message message, int connectionId) throws MessageWorkException;

    void sendBroadCastMessage(Message message) throws MessageWorkException;

    void init() throws ServerException;


}
