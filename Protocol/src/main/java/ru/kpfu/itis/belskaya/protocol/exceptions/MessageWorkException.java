package ru.kpfu.itis.belskaya.protocol.exceptions;

public class MessageWorkException extends Exception {
    public MessageWorkException() {
    }

    public MessageWorkException(String message) {
        super(message);
    }

    public MessageWorkException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageWorkException(Throwable cause) {
        super(cause);
    }
}
