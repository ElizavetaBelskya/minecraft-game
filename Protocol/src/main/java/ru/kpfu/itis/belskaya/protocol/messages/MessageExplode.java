package ru.kpfu.itis.belskaya.protocol.messages;

public class MessageExplode extends Message {

    private int x;
    private int y;

    public MessageExplode(int x, int y, int roomId, int connectionId) {
        super(MessageTypes.EXPLODE_MESSAGE, roomId, connectionId);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
