package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.exceptions.ClientException;
import ru.kpfu.itis.belskaya.protocol.InputService;
import ru.kpfu.itis.belskaya.protocol.OutputService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnection {

    private Socket socket;
    private InputService inputService;
    private OutputService outputService;

    public ClientConnection(InetAddress address, int port) throws ClientException {
        try {
            this.socket = new Socket(address, port);
            outputService = new OutputService(socket.getOutputStream());
            inputService = new InputService(socket.getInputStream());
        } catch (IOException ex) {
            throw new ClientException("Failed to create connection", ex);
        }
    }

    public InputService getInputService() {
        System.out.println(socket.isClosed());
        return inputService;
    }

    public OutputService getOutputService() {
        System.out.println(socket.isClosed());
        return outputService;
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {

        }
    }


}
