package ru.kpfu.itis.belskaya;


public class AppServer {
    public static void main(String[] args) throws ServerException {
        Server server = new Server();
        server.init();
    }
}
