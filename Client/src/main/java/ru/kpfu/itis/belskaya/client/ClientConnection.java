package ru.kpfu.itis.belskaya.client;

import ru.kpfu.itis.belskaya.exceptions.ClientException;
import ru.kpfu.itis.belskaya.protocol.ioServices.InputService;
import ru.kpfu.itis.belskaya.protocol.ioServices.OutputService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnection {
    private Socket socket;
    private InputService inputService;
    private OutputService outputService;

    private int roomId;

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
        return inputService;
    }

    public OutputService getOutputService() {
        return outputService;
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }

}
