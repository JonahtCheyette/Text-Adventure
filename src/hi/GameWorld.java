package hi;

import java.util.Scanner;
import java.util.Random;

public class GameWorld {
	public static void main(String[] args) {
		int makeSure;
		Boolean wentToTown = false;
		Boolean gameStart = true;
		Random rand = new Random();
		String[] choices = new String[] { "cave", "forest", "town" };
		Equipment mKey = new Equipment("Mysterious Key", 700, "");
		Equipment woodArmor = new Equipment("armor", "Wood Armor", 60, 15, 0, "");
		Equipment bronzeArmor = new Equipment("armor", "Bronze Armor", 150, 30, 0, "");
		Equipment silverArmor = new Equipment("armor", "Silver Armor", 200, 60, 0, "");
		Equipment goldArmor = new Equipment("armor", "Gold Armor", 500, 120, 0, "");
		Equipment woodSword = new Equipment("weapon", "Wood Sword", 30, 25, 0, "");
		Equipment bronzeSword = new Equipment("weapon", "Bronze Sword", 50, 75, 0, "");
		Equipment silverSword = new Equipment("weapon", "Silver Sword", 75, 120, 0, "");
		Equipment goldSword = new Equipment("weapon", "Gold Sword", 100, 250, 0, "");
		Equipment sHPotion = new Equipment("Healing", "Small Healing Potion", 70, 20, 0, "");
		Equipment mHPotion = new Equipment("Healing", "Medium Healing Potion", 140, 40, 0, "");
		Equipment lHPotion = new Equipment("Healing", "Large Healing Potion", 230, 90, 0, "");
		Equipment sDPotion = new Equipment("Damage", "Small Damage Potion", 70, 10, 0, "");
		Equipment mDPotion = new Equipment("Damage", "Medium Damage Potion", 140, 25, 0, "");
		Equipment lDPotion = new Equipment("Damage", "Large Damage Potion", 300, 50, 0, "");
		Equipment sABPotion = new Equipment("buffAtk", "Small Attack Potion", 100, 10, 2, "");
		Equipment mABPotion = new Equipment("buffAtk", "Medium Attack Potion", 200, 20, 4, "");
		Equipment lABPotion = new Equipment("buffAtk", "Large Attack Potion", 300, 40, 6, "");
		Equipment sDBPotion = new Equipment("buffDef", "Small Defense Potion", 100, 10, 2, "");
		Equipment mDBPotion = new Equipment("buffDef", "Medium Defense Potion", 200, 20, 4, "");
		Equipment lDBPotion = new Equipment("buffDef", "Large Defense Potion", 300, 40, 6, "");
		Equipment[] fullEList = new Equipment[] { woodSword, bronzeSword, silverSword, goldSword, woodArmor, bronzeArmor, silverArmor, goldArmor, sHPotion,
				mHPotion, lHPotion, sDPotion, mDPotion, lDPotion, sABPotion, mABPotion, lABPotion, sDBPotion, mDBPotion, lDBPotion };
		Equipment[] sellList = new Equipment[fullEList.length];
		Equipment[] pastSellList = new Equipment[fullEList.length];
		Enemy slime = new Enemy("Slime", "Mini Slime", 10, 5, 1, 2, 5, 20);
		Enemy golem = new Enemy("Golem", null, 20, 5, 2, 2, 0, 15);
		Enemy orc = new Enemy("Orc", "Underling", 5, 10, 2, 4, 3, 25);
		Enemy ghost = new Enemy("Ghost", "Cursed Spirit", 5, 5, 0, 1, 20, 25);
		Enemy giantSnake = new Enemy("Giant Snake", null, 20, 15, 1, 1, 0, 30);
		Enemy anaconda = new Enemy("Anaconda", null, 10, 5, 1, 2, 0, 20);
		Enemy scorpion = new Enemy("Scorpion", null, 2, 10, 2, 2, 0, 0);
		Enemy treant = new Enemy("Treant", null, 20, 15, 1, 1, 0, 25);
		Enemy witchDoctor = new Enemy("WitchDoctor", "Shaman", 10, 25, 1, 1, 8, 25);
		Enemy manEatingPlant = new Enemy("Man Eating Plant", null, 15, 30, 1, 1, 0, 30);
		Enemy goblin = new Enemy("Goblin", null, 5, 5, 1, 4, 0, 25);
		Enemy naiad = new Enemy("Naiad", null, 2, 10, 2, 2, 0, 20);
		Enemy kappa = new Enemy("Kappa", null, 15, 15, 1, 1, 0, 25);
		Enemy snappingTurtle = new Enemy("SnappingTurtle", null, 20, 15, 0, 1, 0, 10);
		Enemy koiDragon = new Enemy("Koi Dragon", "Koifish", 15, 30, 30, 1, 1, 0);
		Enemy[] caveEnemies = new Enemy[] { slime, golem, orc, ghost, giantSnake };
		Enemy[][] forestEnemies = new Enemy[][] { { goblin, naiad, kappa, snappingTurtle, koiDragon },
				{ anaconda, scorpion, treant, witchDoctor, manEatingPlant } };
		Boss caveDragon = new Boss("Cave Dragon", null, 250, 80, 100, 0);
		Boss jadeDragon = new Boss("Jade Dragon", "Jade Lizard", 500, 120, 1000, 50);
		Boss eclipse = new Boss("Eclipse", "java.lang.nullPointerExeption", 1000, 140, 2000, 50);
		Boss bridgeTroll = new Boss("Bridge Troll", null, 130, 70, 300, 0);
		Boss giantSpider = new Boss("Giant Spider", "Tarantula", 90, 45, 90, 50);
		Boss[] caveBosses = new Boss[] { caveDragon };
		Boss[] forestBosses = new Boss[] { bridgeTroll, giantSpider };
		Character player = new Character(woodSword);
		Scanner input = new Scanner(System.in);
		Battle battle = new Battle();
		Prompt prompt = new Prompt(player);
		Cave startCave = new Cave(player, caveBosses, caveEnemies, jadeDragon, battle);
		Forest startForest = new Forest(player, forestBosses, forestEnemies, battle);
		Town startTown = new Town(player, eclipse, battle);
		System.out.println("ADVICE: YOU CAN ENTER \"s\" AT ANY TIME TO VIEW YOUR STATUS AND INVENTORY");
		while (true) {
			if (gameStart) {
				System.out.println("Please type character name");
				player.setName(input.nextLine());
				gameStart = false;
			}
			if (startCave.caveComplete() && startForest.forestComplete()) {
				System.out.println(player.getName() + " hears a low rumble, as if a heavy door were being pulled open");
				startCave.openDoor();
			}
			pastSellList = sellList;
			makeSure = rand.nextInt(fullEList.length);
			for (int j = 0; j < makeSure; j++) {
				sellList[j] = fullEList[rand.nextInt(fullEList.length)];
			}
			if (player.getName().equals("tim")) {
				sellList[0] = mKey;
			}
			if (wentToTown) {
				sellList = pastSellList;
			}
			System.out.println(player.getName()
					+ " is at a crossroads. There is a path that leads to a cave, another that leads to a forest, \nand one more that leads into a town.");
			prompt.usePrompt(choices);
			if (prompt.playerChoice.equalsIgnoreCase("cave")) {
				wentToTown = false;
				startCave.runCave();
			} else if (prompt.playerChoice.equalsIgnoreCase("forest")) {
				wentToTown = false;
				startForest.runForest();
			} else if (prompt.playerChoice.equalsIgnoreCase("town")) {
				if (wentToTown) {
					startTown.runTown(sellList, true);
				} else {
					startTown.runTown(sellList, false);
				}
				wentToTown = true;
			}
			if (player.health <= 0) {
				startCave.playerDied();
				prompt.deathPrompt();
				gameStart = true;
			} else if (startCave.gameFinished()) {
				return;
			}
		}
	}
}