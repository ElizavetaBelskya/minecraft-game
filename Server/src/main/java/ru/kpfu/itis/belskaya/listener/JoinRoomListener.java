package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.server.Room;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.exceptions.PlayerToRoomAddingException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;
import ru.kpfu.itis.belskaya.protocol.messages.MessageChooseRoom;
import ru.kpfu.itis.belskaya.protocol.messages.MessageJoinGame;
import ru.kpfu.itis.belskaya.protocol.messages.MessageJoinRoom;

public class JoinRoomListener extends AbstractServerEventListener {
    @Override
    public void handle(Message message) throws MessageWorkException {
        MessageJoinRoom messageJoinRoom = (MessageJoinRoom) message;
        try {
            int roomId = server.moveOutOfWaitingRoom(messageJoinRoom.getRoomId(), message.getConnectionId());
            Room room = server.getRoom(roomId);
            room.getConnection(message.getConnectionId()).setStarted(true);
            MessageJoinGame messageJoinGame = new MessageJoinGame(room.getPlayers(), room.getBlockEntities(), roomId, messageJoinRoom.getConnectionId());
            server.sendBroadCastMessage(messageJoinGame);
        } catch (PlayerToRoomAddingException e) {
            Integer[] roomIds = server.findAvailableRooms();
            MessageChooseRoom messageChooseRoom = new MessageChooseRoom(messageJoinRoom.getConnectionId(), roomIds);
            ChooseRoomListener chooseRoomListener = new ChooseRoomListener();
            chooseRoomListener.init(server);
            chooseRoomListener.handle(messageChooseRoom);
        }

    }

}
