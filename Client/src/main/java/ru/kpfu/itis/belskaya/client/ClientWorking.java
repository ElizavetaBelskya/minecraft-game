package ru.kpfu.itis.belskaya.client;

import ru.kpfu.itis.belskaya.exceptions.ClientException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;

public interface ClientWorking {

    public void init() throws ClientException;

    public Message readMessage() throws ClientException;

    public void sendMessage(Message message) throws ClientException;

}
