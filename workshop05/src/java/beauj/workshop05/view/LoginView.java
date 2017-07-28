package beauj.workshop05.view;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named
@SessionScoped
public class LoginView implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String username = null;
    private String password = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String performLogin() throws ServletException{
        
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ext = fctx.getExternalContext();
        HttpServletRequest req = (HttpServletRequest) ext.getRequest();
        
        try {
            req.login(username, password);
            
        } catch(ServletException ex){
            FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Incorrect login"));
            return (null);

        }
        username = "";
        password = "";
        return("secure/checkout");
    }
    
    
}
