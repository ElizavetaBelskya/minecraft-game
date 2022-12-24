package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;

public class RemovePlayerListener extends AbstractServerEventListener {

    @Override
    public void handle(Message message) {

        server.removeConnectionFromRoom(message.getRoomId(), message.getConnectionId());
        if (message.getRoomId() != -1) {
            try {
                server.sendBroadCastMessage(message);
            } catch (MessageWorkException e) {
                System.out.println("The message about the player's exit from the game " +
                        "has not been sent to all players in the room.");
            }
        }
    }

}
