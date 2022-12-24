package ru.kpfu.itis.belskaya.client;

import ru.kpfu.itis.belskaya.exceptions.ClientException;
import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.exceptions.UnsupportedProtocolException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;

import java.net.InetAddress;

public class Client implements ClientWorking {

    protected final InetAddress address;
    protected final int port;
    private ClientConnection connection;

    public Client(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }
    @Override
    public void init() throws ClientException {
        connection = new ClientConnection(address, port);
    }

    @Override
    public void sendMessage(Message message) throws ClientException {
        try {
            connection.getOutputService().writeMessage(message);
        } catch (MessageWorkException e) {
            throw new ClientException(e);
        }
    }

    @Override
    public Message readMessage() throws ClientException {
        try {
            return connection.getInputService().readMessage();
        } catch (MessageWorkException e) {
            throw new ClientException(e);
        } catch (UnsupportedProtocolException e) {
            throw new ClientException(e);
        }
    }

    public ClientConnection getConnection() {
        return connection;
    }
}
