package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;
import ru.kpfu.itis.belskaya.protocol.messages.MessageDeleteBlock;
import ru.kpfu.itis.belskaya.protocol.messages.MessageExplode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExplodeListener extends AbstractServerEventListener {

    @Override
    public void handle(Message message) throws MessageWorkException {
        MessageExplode messageExplode = (MessageExplode) message;
        int x = messageExplode.getX();
        int y = messageExplode.getY();
        int roomId = messageExplode.getRoomId();
        int connectionId = messageExplode.getConnectionId();
        List<MessageDeleteBlock> messageDeleteBlocks = new ArrayList<>();
        messageDeleteBlocks.add(new MessageDeleteBlock(x, y, roomId, connectionId));
        messageDeleteBlocks.add(new MessageDeleteBlock(x-1, y, roomId, connectionId));
        messageDeleteBlocks.add(new MessageDeleteBlock(x,y-1, roomId, connectionId));
        messageDeleteBlocks.add(new MessageDeleteBlock(x+1, y, roomId, connectionId));
        messageDeleteBlocks.add(new MessageDeleteBlock(x, y+1, roomId, connectionId));
        messageDeleteBlocks.add(new MessageDeleteBlock(x-1, y-1, roomId, connectionId));
        messageDeleteBlocks.add(new MessageDeleteBlock(x+1, y+1, roomId, connectionId));
        messageDeleteBlocks.add(new MessageDeleteBlock(x-1, y+1, roomId, connectionId));
        messageDeleteBlocks.add(new MessageDeleteBlock(x+1, y-1, roomId, connectionId));
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                DeleteBlockListener listener = new DeleteBlockListener();
                listener.init(server);
                for (MessageDeleteBlock messageDeleteBlock : messageDeleteBlocks) {
                    try {
                        listener.handle(messageDeleteBlock);
                    } catch (MessageWorkException e) {
                        System.out.println("The problem of sending an explosion message...");
                    }
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 4000);
    }

}
