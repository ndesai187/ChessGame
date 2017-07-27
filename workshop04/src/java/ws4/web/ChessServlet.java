package ws4.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import ws4.model.ChessActiveGames;

@WebServlet(urlPatterns = "/game")
public class ChessServlet extends HttpServlet{

    @Inject private ChessActiveGames activeGames;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String gameId = UUID.randomUUID().toString().substring(0, 8);
        activeGames.createGame(gameId);
        
        log("Game Created: " + gameId);
        
        JsonObject result = Json.createObjectBuilder()
                            .add("gameId", gameId)
                            .build();
        
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType(MediaType.APPLICATION_JSON);
        
        try (PrintWriter wr = resp.getWriter()) {
            wr.print(result.toString());
        }
             
        
    }
    
    
    
}
