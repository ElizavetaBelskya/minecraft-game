package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.protocol.exceptions.WrongMessageTypeException;
import ru.kpfu.itis.belskaya.protocol.messages.MessageJoinGame;
import ru.kpfu.itis.belskaya.protocol.messages.MessageTypes;

public class ListenerHandler {

    public static ServerEventListener getListenerByType(MessageTypes type) throws WrongMessageTypeException {
        ServerEventListener listener;
        switch (type) {
            case PUT_PLAYER_MESSAGE:
                listener = new PutPlayerListener();
                break;
            case PUT_BLOCK_MESSAGE:
                listener = new PutBlockListener();
                break;
            case DELETE_BLOCK_MESSAGE:
                listener = new DeleteBlockListener();
                break;
            case REMOVE_PLAYER_MESSAGE:
                listener = new RemovePlayerListener();
                break;
            case JOIN_ROOM_MESSAGE:
                listener = new JoinRoomListener();
                break;
            case JOIN_MESSAGE:
                listener = new JoinListener();
                break;
            default:
                throw new WrongMessageTypeException();
        }
        return listener;
    }

}
