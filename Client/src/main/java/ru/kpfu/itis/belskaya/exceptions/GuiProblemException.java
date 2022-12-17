package ru.kpfu.itis.belskaya.exceptions;

public class GuiProblemException extends Exception {
    public GuiProblemException() {
    }

    public GuiProblemException(String message) {
        super(message);
    }

    public GuiProblemException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuiProblemException(Throwable cause) {
        super(cause);
    }
}
