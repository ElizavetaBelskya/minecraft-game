package ru.kpfu.itis.belskaya.protocol.exceptions;

public class WrongMessageTypeException extends Exception {
    public WrongMessageTypeException() {
    }

    public WrongMessageTypeException(String message) {
        super(message);
    }

    public WrongMessageTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongMessageTypeException(Throwable cause) {
        super(cause);
    }
}
