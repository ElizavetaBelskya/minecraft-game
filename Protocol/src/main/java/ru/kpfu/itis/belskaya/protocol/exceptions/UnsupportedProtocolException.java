package ru.kpfu.itis.belskaya.protocol.exceptions;

public class UnsupportedProtocolException extends Exception {

    public UnsupportedProtocolException() {
    }

    public UnsupportedProtocolException(String message) {
        super(message);
    }

    public UnsupportedProtocolException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedProtocolException(Throwable cause) {
        super(cause);
    }
}
