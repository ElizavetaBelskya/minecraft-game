package ru.kpfu.itis.belskaya.protocol.ioServices;

import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class OutputService {

    public static final byte[] PROTOCOL_VERSION = {0x1, 0x1};

    private OutputStream out;

    public OutputService(OutputStream out) {
        this.out = out;
    }

    public void writeMessage(Message message) throws MessageWorkException {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeByte(PROTOCOL_VERSION[0]);
            objectOutputStream.writeByte(PROTOCOL_VERSION[1]);
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            throw new MessageWorkException("Error sending the message", e);
        }
    }


}
