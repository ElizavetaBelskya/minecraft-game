package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.server.Server;
import ru.kpfu.itis.belskaya.protocol.exceptions.WrongMessageTypeException;
import ru.kpfu.itis.belskaya.protocol.messages.MessageTypes;

public class ListenerHandler {

    private Server server;
    private PutPlayerListener putPlayerListener;
    private PutBlockListener putBlockListener;
    private DeleteBlockListener deleteBlockListener;
    private JoinRoomListener joinRoomListener;
    private JoinListener joinListener;

    private ExplodeListener explodeListener;

    public ListenerHandler(Server server) {
        this.server = server;
    }

    public ServerEventListener getListenerByType(MessageTypes type) throws WrongMessageTypeException {
        ServerEventListener listener;
        switch (type) {
            case PUT_PLAYER_MESSAGE:
                if (putPlayerListener == null) {
                    putPlayerListener = new PutPlayerListener();
                }
                listener = putPlayerListener;
                break;
            case PUT_BLOCK_MESSAGE:
                if (putBlockListener == null) {
                    putBlockListener = new PutBlockListener();
                }
                listener = putBlockListener;
                break;
            case DELETE_BLOCK_MESSAGE:
                if (deleteBlockListener == null) {
                    deleteBlockListener = new DeleteBlockListener();
                }
                listener = deleteBlockListener;
                break;
            case JOIN_ROOM_MESSAGE:
                if (joinRoomListener == null) {
                    joinRoomListener = new JoinRoomListener();
                }
                listener = joinRoomListener;
                break;
            case JOIN_MESSAGE:
                if (joinListener == null) {
                    joinListener = new JoinListener();
                }
                listener = joinListener;
                break;
            case EXPLODE_MESSAGE:
                if (explodeListener == null) {
                    explodeListener = new ExplodeListener();
                }
                listener = explodeListener;
                break;
            default:
                throw new WrongMessageTypeException();
        }
        listener.init(server);
        return listener;
    }

}
