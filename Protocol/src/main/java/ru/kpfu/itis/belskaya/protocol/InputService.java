package ru.kpfu.itis.belskaya.protocol;

import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.exceptions.UnsupportedProtocolException;
import ru.kpfu.itis.belskaya.protocol.exceptions.WrongMessageTypeException;
import ru.kpfu.itis.belskaya.protocol.messages.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class InputService {

    private static final byte[] PROTOCOL_VERSION = {0x1, 0x1};

    private static final HashMap<Byte, String> messages = new HashMap<Byte, String>();
    private InputStream in;

    public InputService(InputStream in) {
        this.in = in;
    }

    public Message readMessage() throws MessageWorkException, UnsupportedProtocolException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            byte[] bytes = objectInputStream.readNBytes(2);
            if (bytes[0] == PROTOCOL_VERSION[0] && bytes[1] == PROTOCOL_VERSION[1]) {
                int type = objectInputStream.readInt();
                Class<? extends Message> messageClass = (getMessageClass(type));
                Object message = objectInputStream.readObject();
                return messageClass.cast(message);
            } else {
                throw new UnsupportedProtocolException("Unsuitable protocol version");
            }
        } catch (IOException | ClassNotFoundException | WrongMessageTypeException e) {
            throw new MessageWorkException("Error reading the message", e);
        }

    }


    public Class<? extends Message> getMessageClass(int type) throws WrongMessageTypeException {
        Class<? extends Message> messageClass;
        switch (type) {
            case 1:
                messageClass = MessagePutPlayer.class;
                break;
            case 2:
                messageClass = MessagePutBlock.class;
                break;
            case 3:
                messageClass = MessageDeleteBlock.class;
                break;
            case 4:
                messageClass = MessageJoinGame.class;
                break;
            default:
                throw new WrongMessageTypeException();
        }
        return messageClass;
    }



}
