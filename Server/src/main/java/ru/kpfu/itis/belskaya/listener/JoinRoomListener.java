package ru.kpfu.itis.belskaya.listener;

import org.postgresql.gss.GSSOutputStream;
import ru.kpfu.itis.belskaya.Room;
import ru.kpfu.itis.belskaya.ServerException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;
import ru.kpfu.itis.belskaya.protocol.messages.MessageJoinGame;
import ru.kpfu.itis.belskaya.protocol.messages.MessageJoinRoom;

public class JoinRoomListener extends AbstractServerEventListener {
    @Override
    public void handle(Message message) throws ServerException {
        MessageJoinRoom messageJoinRoom = (MessageJoinRoom) message;
        int roomId = server.moveOutOfWaitingRoom(messageJoinRoom.getRoomId(), message.getConnectionId());
        Room room = server.getRoom(roomId);
        room.getConnection(message.getConnectionId()).setStarted(true);

        MessageJoinGame messageJoinGame = new MessageJoinGame(room.getPlayers(), room.getBlockEntities(), roomId, messageJoinRoom.getConnectionId());
        server.sendBroadCastMessage(messageJoinGame);
    }

}
