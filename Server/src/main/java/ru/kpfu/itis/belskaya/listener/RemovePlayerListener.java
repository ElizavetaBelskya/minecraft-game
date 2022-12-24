package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;
import ru.kpfu.itis.belskaya.server.Connection;
import ru.kpfu.itis.belskaya.server.Room;

public class RemovePlayerListener extends AbstractServerEventListener {

    @Override
    public void handle(Message message) {
        int roomId = message.getRoomId();
        int connectionId = message.getConnectionId();
        server.removeConnectionFromRoom(roomId, connectionId);
        if (message.getRoomId() != -1) {
            Room room = server.getRoom(roomId);
            for (Connection connection : room.getRoomConnections()) {
                try {
                    connection.getOutputService().writeMessage(message);
                } catch (MessageWorkException e) {
                    System.out.println("The problem of sending a message to some users of the " + roomId + "th room");
                }
            }
        }
    }

}
