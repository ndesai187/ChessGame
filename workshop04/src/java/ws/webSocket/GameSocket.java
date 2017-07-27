package ws.webSocket;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import ws4.model.ChessActiveGames;

@Dependent
@ServerEndpoint("/chess-event/{gid}")
public class GameSocket {
    
    @Inject private ChessActiveGames activeGamses;
    
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
    
}
