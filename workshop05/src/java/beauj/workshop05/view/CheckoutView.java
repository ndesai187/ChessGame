package beauj.workshop05.view;

import java.io.Serializable;
import java.security.Principal;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@ViewScoped
public class CheckoutView implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject private Principal who;

	public String getName() {
		if (null == who)
			return ("anonymous");
		return (who.getName());
	}
	public void setName(String n) { }

	public String performCheckout() {
		HttpServletRequest req = (HttpServletRequest)FacesContext
				.getCurrentInstance().getExternalContext()
				.getRequest();
		req.getSession().invalidate();
		return ("/index?faces-redirect=true");
	}

}
