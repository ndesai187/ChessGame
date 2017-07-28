package ws.webSocket;

import java.io.IOException;
import java.util.List;
import javax.websocket.Session;


public class BroadcastMove implements Runnable {

    private String message;
    private List<Session> players;

    public BroadcastMove(String m, List<Session> p) {
        this.message = m;
        this.players = p;
    }
    
    @Override
    public void run() {
        
        System.out.println(">>> broadcasting message: " + message);
        for (Session s: players){
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException ex) {
                try { s.close(); } catch (Exception e) { }
            }
        } 
        
    }
    
}
