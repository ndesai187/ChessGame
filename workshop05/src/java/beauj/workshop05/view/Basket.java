package beauj.workshop05.view;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class Basket implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Fruit> basket = new LinkedList<>();

	public List<Fruit> getBasket() {
		return basket;
	}
	public void setBasket(List<Fruit> basket) {
		this.basket = basket;
	}

	public String addToBasket(Fruit fruit) {
		basket.stream()
				.filter(f -> f.equals(fruit))
				.findFirst()
				.orElseGet(() -> {
					Fruit f = fruit.createCopy();
					basket.add(f);
					return (f);
				})
				.incrementQuantity();

		return ("basket");
	}
}
