package ws4.web;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.websocket.Session;
import ws4.model.ChessActiveGames;

@RequestScoped
@Named
public class GamesView {
    
    @Inject private ChessActiveGames gameData;
    
    public Set<String> getGames(){
        return (gameData.getActiveGames().keySet());
    }
    public void SetGames(){}
    
    public Integer getCount(String gid){
        Optional<List<Session>> session = gameData.getGame(gid);
        List<Session> players = session.get();
        return players.size();
    }
    
}
