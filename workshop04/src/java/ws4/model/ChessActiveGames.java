package ws4.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

@ApplicationScoped
public class ChessActiveGames {
    
    private HashMap<String, List<Session>> activeGames = new HashMap<>();

    public ChessActiveGames() {
    }
    
    public void createGame(String gid){
        List<Session> players = new LinkedList<>();
        activeGames.put(gid, players);
    }
    
    public boolean hasGame(String gid){
        return (activeGames.containsKey(gid));
    }
    
    public Optional<List<Session>> getGame(String gid){
        if(hasGame(gid)){
           return (Optional.of(activeGames.get(gid)));
        }
        
        return (Optional.empty());
    }

    public HashMap<String, List<Session>> getActiveGames() {
        return activeGames;
    }

    public void setActiveGames(HashMap<String, List<Session>> activeGames) {
        this.activeGames = activeGames;
    }
    
    
    
}
