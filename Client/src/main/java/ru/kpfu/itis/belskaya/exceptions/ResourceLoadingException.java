package ru.kpfu.itis.belskaya.exceptions;

public class ResourceLoadingException extends Exception {

    public ResourceLoadingException() {
    }

    public ResourceLoadingException(String message) {
        super(message);
    }

    public ResourceLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceLoadingException(Throwable cause) {
        super(cause);
    }
}
