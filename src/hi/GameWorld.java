package hi;

import java.util.Scanner;
import java.util.Random;

/**
 * 
 * TODO: add a class system to the game, add exp and leveling (possibly tied to class), balance stuff
 *
 */

public class GameWorld {
	public static void main(String[] args) {
		int makeSure;
		Boolean wentToTown = false;
		Boolean gameStart = true;
		Random rand = new Random();
		String[] choices = new String[] { "cave", "forest", "town" };
		Equipment mKey = new Equipment("Mysterious Key", 700, "An old, weathered key. Perhaps it fits somewhere.");
		Equipment woodArmor = new Equipment("armor", "Wood Armor", 60, 15, 0, "A wooden set of armor. It doesn't seem like it would offer much protection, but it'll do for smaller monsters.");
		Equipment bronzeArmor = new Equipment("armor", "Bronze Armor", 150, 30, 0, "A bronze set of armor. It seems to have seen it's fair share of battles.");
		Equipment silverArmor = new Equipment("armor", "Silver Armor", 200, 60, 0, "A gleaming set of silver armor. Smells like polish.");
		Equipment goldArmor = new Equipment("armor", "Gold Armor", 500, 120, 0, "A set of gold armor. Gold is too soft to make armor out of, so this armor set is actually titanium painted gold.");
		Equipment woodSword = new Equipment("weapon", "Wood Sword", 30, 25, 0, "A wooden sword. More suited for practice bouts than actual combat.");
		Equipment bronzeSword = new Equipment("weapon", "Bronze Sword", 75, 50, 0, "A bronze sword. Full of nicks and scratches.");
		Equipment silverSword = new Equipment("weapon", "Silver Sword", 120, 75, 0, "A silver sword. Most effective against werewolves.");
		Equipment goldSword = new Equipment("weapon", "Gold Sword", 250, 100, 0, "A gold sword. Heavy, but hits like a truck.");
		Equipment sHPotion = new Equipment("Healing", "Small Healing Potion", 70, 20, 0, "A small green potion. Smells like herbs.");
		Equipment mHPotion = new Equipment("Healing", "Medium Healing Potion", 140, 40, 0, "A medium-sized green potion. Works best taken orally. Side effects may include Mesothelioma.");
		Equipment lHPotion = new Equipment("Healing", "Large Healing Potion", 230, 90, 0, "A large green potion. The label says \"For those \'Hold my beer!\' moments.\"");
		Equipment sDPotion = new Equipment("Damage", "Small Damage Potion", 70, 10, 0, "A small black potion. The forbidden capri sun");
		Equipment mDPotion = new Equipment("Damage", "Medium Damage Potion", 140, 25, 0, "A medium-sized black potion. Smells like ozone.");
		Equipment lDPotion = new Equipment("Damage", "Large Damage Potion", 300, 50, 0, "A large black potion. Some peole say it's the same stuff vaccines use, and therefore you should never get vaccines. Those people's children are known for dying at 4.");
		Equipment sABPotion = new Equipment("buffAtk", "Small Attack Potion", 100, 10, 2, "A small red potion. Has the consistency of meat sauce.");
		Equipment mABPotion = new Equipment("buffAtk", "Medium Attack Potion", 200, 20, 4, "A medium-sized red potion. It's constantly warm");
		Equipment lABPotion = new Equipment("buffAtk", "Large Attack Potion", 300, 40, 6, "A large red potion. Glows like fire");
		Equipment sDBPotion = new Equipment("buffDef", "Small Defense Potion", 100, 10, 2, "A small blue potion. The label says \"If the effects of the potion last for more than 6 hours, consult a doctor.\"");
		Equipment mDBPotion = new Equipment("buffDef", "Medium Defense Potion", 200, 20, 4, "A medium-sized blue potion. You can hear it sloshing around in it's bottle.");
		Equipment lDBPotion = new Equipment("buffDef", "Large Defense Potion", 300, 40, 6, "A large blue potion. The bottle it's in has has froston the glass.");
		Equipment[] fullEList = new Equipment[] { woodSword, bronzeSword, silverSword, goldSword, woodArmor, bronzeArmor, silverArmor, goldArmor, sHPotion,
				mHPotion, lHPotion, sDPotion, mDPotion, lDPotion, sABPotion, mABPotion, lABPotion, sDBPotion, mDBPotion, lDBPotion };
		Equipment[] sellList = new Equipment[fullEList.length];
		Equipment[] pastSellList = new Equipment[fullEList.length];
		Mob slime = new Mob("Slime", "Mini Slime", 10, 5, 1, 2, 5, 20);
		Mob golem = new Mob("Golem", null, 20, 5, 2, 2, 0, 15);
		Mob orc = new Mob("Orc", "Underling", 30, 10, 2, 4, 10, 25);
		Mob ghost = new Mob("Ghost", "Cursed Spirit", 25, 5, 0, 1, 25, 25);
		Mob giantSnake = new Mob("Giant Snake", null, 40, 15, 1, 1, 0, 20);
		Mob anaconda = new Mob("Anaconda", null, 10, 5, 1, 2, 0, 20);
		Mob scorpion = new Mob("Scorpion", null, 2, 10, 2, 2, 0, 0);
		Mob treant = new Mob("Treant", null, 20, 15, 1, 1, 0, 25);
		Mob witchDoctor = new Mob("WitchDoctor", "Shaman", 10, 25, 1, 1, 8, 25);
		Mob manEatingPlant = new Mob("Man Eating Plant", null, 15, 30, 1, 1, 0, 30);
		Mob goblin = new Mob("Goblin", null, 5, 5, 1, 4, 0, 25);
		Mob naiad = new Mob("Naiad", null, 2, 10, 2, 2, 0, 20);
		Mob kappa = new Mob("Kappa", null, 15, 15, 1, 1, 0, 25);
		Mob snappingTurtle = new Mob("SnappingTurtle", null, 20, 15, 0, 1, 0, 10);
		Mob koiDragon = new Mob("Koi Dragon", "Koifish", 15, 30, 30, 1, 1, 0);
		Mob[] caveEnemies = new Mob[] { slime, golem, orc, ghost, giantSnake };
		Mob[][] forestEnemies = new Mob[][] { { goblin, naiad, kappa, snappingTurtle, koiDragon },
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
			for (int ib = 0; ib < makeSure; ib++) {
				sellList[ib] = fullEList[rand.nextInt(fullEList.length)];
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