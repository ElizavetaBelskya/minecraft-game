package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;
import ru.kpfu.itis.belskaya.protocol.messages.MessageChooseRoom;

public class ChooseRoomListener extends AbstractServerEventListener {

    @Override
    public void handle(Message message) throws MessageWorkException {
        MessageChooseRoom messageChooseRoom = (MessageChooseRoom) message;
        server.sendMessage(messageChooseRoom, messageChooseRoom.getConnectionId());
    }

}
