package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.gui.GameFrame;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AppClient {
    public static void main(String[] args) throws ClientException, UnknownHostException {
        Client client = new Client(InetAddress.getLocalHost(), 8077);
        client.init();
        GameFrame frame = new GameFrame(client);
        frame.createGUI();
        MessageProcessor processor = new MessageProcessor(client.getConnection(), frame);
        frame.setVisible(true);
    }
}