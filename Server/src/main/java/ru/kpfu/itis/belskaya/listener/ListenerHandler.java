package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.protocol.exceptions.WrongMessageTypeException;

public class ListenerHandler {

    public static ServerEventListener getListenerByType(int type) throws WrongMessageTypeException {
        ServerEventListener listener;
        switch (type) {
            case 1:
                listener = new PutPlayerListener();
                break;
            case 2:
                listener = new PutBlockListener();
                break;
            case 3:
                listener = new DeleteBlockListener();
                break;
            default:
                throw new WrongMessageTypeException();
        }
        return listener;
    }

}
