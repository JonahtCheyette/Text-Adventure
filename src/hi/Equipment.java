package hi;

public class Equipment {
	int stat;
	int price;
	int last;
	Boolean Armour;
	Boolean Item;
	String name;
	String function;
	String text;

	Equipment(String function, String name, int price, int Stat, int last, String text) {
		this.name = name;
		this.stat = Stat;
		this.price = price;
		this.last = last;
		this.text = text;
		this.function = function;
		if (function.equals("armor")) {
			this.Armour = true;
			this.Item = false;
		} else if (function.equals("weapon")) {
			this.Armour = false;
			this.Item = false;
		} else if (function.equals("Damage") || function.equals("Healing") || function.equals("buffAtk") || function.equals("buffDef")) {
			this.Armour = false;
			this.Item = true;
		} else {
			System.out.println("Check " + name + "'s constructor idiot");
		}
	}

	Equipment(String name, int price, String text) {
		this.name = name;
		this.price = price;
		this.text = text;
		this.Armour = false;
		this.Item = true;
		this.function = "passive";
	}

	Equipment() {
		this.stat = 0;
		this.price = 0;
		this.name = "ERROR";
		this.Armour = true;
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
		return this.Armour;
	}

	public Boolean isItem() {
		return this.Item;
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