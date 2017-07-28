package ws.webSocket;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import ws4.model.ChessActiveGames;

@Dependent
@ServerEndpoint("/chess-event/{gid}")
public class GameSocket {
    
    @Inject private ChessActiveGames activeGamses;
    
    @Resource(name = "DefaultManagedScheduledExecutorService")   
    private ManagedScheduledExecutorService service;
    
    private String gameId = null;
    private Session session = null;
    
    @OnOpen
    public void open(Session session, @PathParam("gid") String gameId){
        this.session = session;
        this.gameId = gameId;
        
        Optional<List<Session>> opt = activeGamses.getGame(gameId);
        List<Session> players = opt.get();
        players.add(session);
    }
    
    @OnMessage
    public void message(String msg){
        
        System.out.println(">>> Broadcasting... ");
        System.out.println(">>> msg : " + msg);

        Optional<List<Session>> opt = activeGamses.getGame(gameId);
        List<Session> players = opt.get();
        
        BroadcastMove move = new BroadcastMove(msg, players);
        service.submit(move);
        
        /* 
        //commented and replaced with Threading
        for (Session s: players){
            try {
                s.getBasicRemote().sendText(msg);
            } catch (Exception ex) {
                try { s.close(); } catch (Exception e) { }
            }
        } 
        */
    }
}
