package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.listener.ServerEventListener;
import ru.kpfu.itis.belskaya.protocol.messages.Message;

public interface ServerWorking {

    void sendBroadCastMessage(Message message) throws ServerException;

    void init() throws ServerException;

}
