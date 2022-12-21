package ru.kpfu.itis.belskaya.protocol.messages;

import java.io.Serializable;

public enum MessageTypes implements Serializable {

    JOIN_MESSAGE(0),
    PUT_PLAYER_MESSAGE(1),
    PUT_BLOCK_MESSAGE(2),
    DELETE_BLOCK_MESSAGE(3),

    REMOVE_PLAYER_MESSAGE(4);

    //CHOOSE_ROOM_MESSAGE(4);
    final int id;
    MessageTypes(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
