package ru.kpfu.itis.belskaya.exceptions;

public class PlayerToRoomAddingException extends Exception {
    public PlayerToRoomAddingException() {
    }

    public PlayerToRoomAddingException(String message) {
        super(message);
    }

    public PlayerToRoomAddingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerToRoomAddingException(Throwable cause) {
        super(cause);
    }
}
