package beauj.workshop05.view;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named
public class Fruit implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String[] FRUIT_NAMES = { 
		"acorn_squash", "apple", "bell_pepper", "blueberries", "broccoli",
		"carrot", "celery", "chili_pepper", "corn", "eggplant", "lettuce", 
		"mushroom", "onion", "potato", "pumpkin", "radish", "squash",
		"strawberry", "sugar_snap", "tomato", "zucchini" 
	};

	private List<Fruit> FRUIT_LISTS = null;

	private String name;
	private Integer quantity = 0;

	public Fruit() { }

	public Fruit(String f) {
		name = f;
	}

	@PostConstruct
	private void init() {
		FRUIT_LISTS = new LinkedList<>();
		for (String f: FRUIT_NAMES)
			FRUIT_LISTS.add(new Fruit(f));
	}

	public List<Fruit> fruits() {
		return (FRUIT_LISTS);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
	}

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public void incrementQuantity() {
		quantity++;
	}

	public Fruit createCopy() {
		return (new Fruit(name));
	}

	@Override
	public boolean equals(Object obj) {
		return ((obj instanceof Fruit) && (name.equals(((Fruit)obj).name)));
	}

	@Override
	public int hashCode() {
		return (name.hashCode());
	}
	
}
