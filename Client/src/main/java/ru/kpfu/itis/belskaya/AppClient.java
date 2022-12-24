package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.client.Client;
import ru.kpfu.itis.belskaya.exceptions.ClientException;
import ru.kpfu.itis.belskaya.gui.GameFrame;
import ru.kpfu.itis.belskaya.listener.MessageProcessor;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class AppClient {

    public static void main(String[] args) {
        Client client;
        GameFrame frame = new GameFrame();
        try {
            client = new Client(InetAddress.getLocalHost(), 8077);
            client.init();
            frame.createGUI(client);
            MessageProcessor processor = new MessageProcessor(client.getConnection(), frame);
            frame.setVisible(true);
        } catch (UnknownHostException | ClientException e) {
            JOptionPane.showMessageDialog(null, "Error", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            frame.closeFrame();
        }

    }

}