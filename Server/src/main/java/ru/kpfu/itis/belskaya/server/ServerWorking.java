package ru.kpfu.itis.belskaya.server;

import ru.kpfu.itis.belskaya.exceptions.ServerException;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;
import ru.kpfu.itis.belskaya.protocol.messages.MessageDeleteBlock;
import ru.kpfu.itis.belskaya.protocol.messages.MessagePutBlock;
import ru.kpfu.itis.belskaya.protocol.messages.MessagePutPlayer;

public interface ServerWorking {

    void sendMessage(Message message, int connectionId) throws MessageWorkException;

    void sendBroadCastMessage(Message message) throws MessageWorkException;

    void init() throws ServerException;

    void changedPlayerCoordinates(MessagePutPlayer messagePutPlayer);

    void addBlockToRoom(MessagePutBlock messagePutBlock);

    void removeBlockToRoom(MessageDeleteBlock messageDeleteBlock);


}
