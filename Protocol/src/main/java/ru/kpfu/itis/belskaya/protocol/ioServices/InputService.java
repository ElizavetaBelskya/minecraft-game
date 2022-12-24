package ru.kpfu.itis.belskaya.protocol.ioServices;

import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.exceptions.UnsupportedProtocolException;
import ru.kpfu.itis.belskaya.protocol.exceptions.WrongMessageTypeException;
import ru.kpfu.itis.belskaya.protocol.messages.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.HashMap;

public class InputService {

    private static final byte[] PROTOCOL_VERSION = {0x1, 0x1};
    private InputStream in;
    public InputService(InputStream in) {
        this.in = in;
    }

    public Message readMessage() throws MessageWorkException, UnsupportedProtocolException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            byte[] bytes = objectInputStream.readNBytes(2);
            if (bytes[0] == PROTOCOL_VERSION[0] && bytes[1] == PROTOCOL_VERSION[1]) {
                Object message = objectInputStream.readObject();
                return (Message) message;
            } else {
                throw new UnsupportedProtocolException("Unsuitable protocol version");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new MessageWorkException("Error reading the message", e);
        }

    }


}
