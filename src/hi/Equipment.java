package hi;

public class Equipment {
	int stat;
	int price;
	int last;
	String name;
	String function;
	String text;

	Equipment(String function, String name, int price, int stat, int last, String text) {
		this.name = name;
		this.stat = stat;
		this.price = price;
		this.last = last;
		this.text = text;
		this.function = function;
	}

	Equipment(String name, int price, String text) {
		this.name = name;
		this.price = price;
		this.text = text;
		this.function = "passive";
	}

	Equipment() {
		this.stat = 0;
		this.price = 0;
		this.name = "ERROR";
	}

	public String getText() {
		return this.text;
	}
	
	public String getFunction() {
		return this.function;
	}

	public String getName() {
		return this.name;
	}

	public Boolean isArmor() {
		return this.function.equalsIgnoreCase("armor");
	}

	public Boolean isItem() {
		return function.equals("Damage") || function.equals("Healing") || function.equals("buffAtk") || function.equals("buffDef") || function.equals("passive");
	}
	
	public Boolean isPassive() {
		return this.getFunction().equalsIgnoreCase("passive");
	}

	public int getLast() {
		return this.last;
	}

	public int getStat() {
		return this.stat;
	}
}