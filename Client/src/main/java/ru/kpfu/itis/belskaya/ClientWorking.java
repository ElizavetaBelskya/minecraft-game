package ru.kpfu.itis.belskaya;

import ru.kpfu.itis.belskaya.protocol.messages.Message;

public interface ClientWorking {

    public void init() throws ClientException;

    public Message readMessage() throws ClientException;

    public void sendMessage(Message message) throws ClientException;

}