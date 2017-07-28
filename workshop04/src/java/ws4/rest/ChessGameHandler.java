package ws4.rest;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import ws4.model.ChessActiveGames;

@RequestScoped
@Path("/game")
public class ChessGameHandler {
    
    @Inject private ChessActiveGames activeGames;
    
    @GET
    @Path("{gid}")
    public void get(@PathParam("gid") String gid){
        Optional<List<Session>> game = activeGames.getGame(gid);
    }
    
}
