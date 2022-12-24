package ru.kpfu.itis.belskaya;


import ru.kpfu.itis.belskaya.exceptions.ServerException;
import ru.kpfu.itis.belskaya.server.Server;

public class AppServer {
    public static void main(String[] args) throws ServerException {
        Server server = new Server();
        server.init();
    }
}
