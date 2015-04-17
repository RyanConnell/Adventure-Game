package com.Rhinodude.AdventureGame.Screen;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Queue;

import com.Rhinodude.AdventureGame.Display;
import com.Rhinodude.AdventureGame.Frame;
import com.Rhinodude.AdventureGame.ImageLoader;
import com.Rhinodude.AdventureGame.LevelLoader;
import com.Rhinodude.AdventureGame.Attacks.Attack;
import com.Rhinodude.AdventureGame.Entities.Entity;

public class BattleScreen extends Screen {
	public static ArrayList<Entity> entity;
	public static Attack currentAttack;
	public static int xScaleBattle = 64, yScaleBattle = 32;
	public static Entity currentTarget, currentAttacker;
	public static boolean isActive;
	public static int currentTurn;
	public static boolean battleOver = false;
	public static int endTick, battleTextTick;
	public static String battleText;
	public static int currentMenu = 0;
	public static Menu menu;
	public static ArrayList<Entity> que;

	public BattleScreen(ArrayList<Entity> entity) {
		super(2);
		BattleScreen.entity = entity;
		LevelLoader.loadBattleBackground();
		nextTurn();
	}

	public void init() {
		if (GameScreen.player == null || entity == null) {
			GameScreen.exitBattleScreen();
		}
		menu = new Menu(0);
		currentAttacker = null;
		currentAttack = null;
		currentTarget = null;
		currentTurn = 0;
		isActive = true;
		endTick = 0;

		// Temporary Stuff to reset health.
		for (int i = 0; i < GameScreen.player.party.size(); i++) {
			GameScreen.player.party.get(i).isDead = false;
			GameScreen.player.party.get(i).health = GameScreen.player.party.get(i).maxHealth;
		}
	}

	public void update() {
		// checkWin();
		if (!battleOver) {
			if (currentAttack != null) {
				currentAttack.update();
			}
			for (int i = 0; i < GameScreen.player.party.size(); i++) {
				GameScreen.player.party.get(i).updateBattle();
			}
			for (int i = 0; i < entity.size(); i++) {
				entity.get(i).updateBattle();
			}
			menu.update();
		} else {
			endTick++;
			if (endTick >= 250) {
				GameScreen.exitBattleScreen();
			}
		}
		if (battleText != null && !battleText.equals("")) {
			battleTextTick++;
			if (battleTextTick >= 100) {
				battleTextTick = 0;
				battleText = "";
			}
		}
	}

	public static void perTurnUpdates() {
		for (int i = 0; i < GameScreen.player.party.size(); i++) {
			GameScreen.player.party.get(i).perTurnUpdates();
		}
		for (int i = 0; i < entity.size(); i++) {
			entity.get(i).perTurnUpdates();
		}
	}

	public static void nextTurn() {
		checkWin();
		updateQ();
		currentAttack = null;
		currentTarget = null;

		if (que.size() > 0) {
			System.out.println(que.size());
			currentAttacker = que.get(0);
			currentAttacker.turnReset();
			que.remove(currentAttacker);
		}

		currentTurn++;
		perTurnUpdates();

	}

	public static void skipTurn() {
		currentTurn++;
		nextTurn();
	}

	public static void updateQ() {
		if (que == null) {
			que = new ArrayList<Entity>();
		}
		if (que.size() == 0) {
			for (int i = 0; i < GameScreen.player.party.size(); i++) {
				if (!GameScreen.player.party.get(i).isDead && GameScreen.player.party.get(i).canAttack()) {
					que.add(GameScreen.player.party.get(i));
				}
			}
			for (int i = 0; i < entity.size(); i++) {
				if (!entity.get(i).isDead && entity.get(i).canAttack()) {
					que.add(entity.get(i));
				}
			}
		}
	}

	public static void entityAttack(Entity entity) {
		setTarget(randomTarget(entity));
		entity.cast(0, currentTarget);
	}

	public void drawHUD() {
		if (currentAttacker != null && currentAttacker.isPlayerControlled) {
			for (int i = 0; i < currentAttacker.abilities.length; i++) {
				if (currentAttacker.abilities[i] != null) {
					Display.drawOverlay(currentAttacker.abilities[i].img[0][0], 8, 8 + (i * 58), 64, 58);
				}
			}
		}
	}

	public void render() {
		int yOffset = 0;
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 12; i++) {
				if (i >= 0 && i < LevelLoader.mapWidth && j + yOffset >= 0 && j + yOffset < LevelLoader.mapHeight) {
					if (LevelLoader.tileMapFloor[i][j + yOffset] != null) {
						LevelLoader.tileMapFloor[i][j + yOffset].renderBattle();
					}
					if (LevelLoader.tileMap[i][j + yOffset] != null) {
						LevelLoader.tileMap[i][j + yOffset].renderBattle();
					}
				}
			}
		}
		Display.drawOverlay(ImageLoader.battleScreenOverlay, 0, 0, Frame.fWidth, Frame.fHeight);
		if (currentAttacker.isPlayerControlled) {
			drawHUD();
		}

		for (int i = GameScreen.player.party.size() - 1; i >= 0; i--) {
			GameScreen.player.party.get(i).renderBattle(2 + i, 8 - (i * 2), 1);
		}

		for (int i = entity.size() - 1; i >= 0; i--) {
			entity.get(i).renderBattle(11 - i, 8 - (i * 2), 0);
		}

		renderSelected(currentAttacker);
		renderTargeted(currentTarget);

		if (currentAttack != null) {
			currentAttack.render();
		}
		menu.render();
		if (battleText != null && !battleText.equals("")) {
			Display.drawText(battleText, 73 + (759 / 2), 340, Color.cyan, false);
		}
	}

	public void renderSelected(Entity entity) {
		if (entity != null) {
			Display.drawRect(entity.battleX * xScaleBattle, entity.battleY * yScaleBattle, 64, 64, Color.magenta);
		}
		if (currentAttacker != null && currentAttacker.currentSpell != -1 && currentAttacker.isPlayerControlled) {
			Display.drawOverlay(ImageLoader.spellSelected, 8, 8 + (currentAttacker.currentSpell * 56) + currentAttacker.currentSpell, 63, 56);
		}
	}

	public void renderTargeted(Entity entity) {
		if (entity != null) {
			Display.drawOverlay(ImageLoader.targetedImage, entity.battleX * xScaleBattle, entity.battleY * yScaleBattle - yScaleBattle / 2, entity.width, yScaleBattle / 2);
		}
	}

	public static void cast() {
		if (currentAttacker != null && currentTarget != null && currentAttacker.currentSpell != -1) {
			currentAttacker.cast(currentAttacker.currentSpell, currentTarget);
		}
	}

	public static void setBattleText(String string) {
		battleText = string;
		battleTextTick = 0;
	}

	public static void mouseClicked(int x, int y) {
		Rectangle mouseRect = new Rectangle(x, y, 1, 1);
		for (int i = 0; i < GameScreen.player.party.size(); i++) {
			if (mouseRect.intersects(GameScreen.player.party.get(i).battleRect)) {
				if (currentAttacker.currentSpell != -1) {
					setTarget(GameScreen.player.party.get(i));
					cast();
				} else {
					setBattleText("Choose a friendly spell first...");
				}
			}
		}
		for (int i = 0; i < entity.size(); i++) {
			if (mouseRect.intersects(entity.get(i).battleRect)) {
				if (currentAttacker.currentSpell != -1) {
					setTarget(entity.get(i));
					cast();
				} else {
					setBattleText("Choose a spell first...");
				}
			}
		}
		if (currentAttacker != null && currentAttacker.isPlayerControlled) {
			for (int i = 0; i < currentAttacker.abilities.length; i++) {
				Rectangle SpellButton = new Rectangle(8, 8 + (i * 58), 64, 58);
				if (mouseRect.intersects(SpellButton)) {
					currentAttacker.currentSpell = i;
				}
			}
		}
		for (int i = 0; i < Menu.size; i++) {
			if (mouseRect.intersects(Menu.buttonRect[i])) {
				menu.changeTab(i);
			}
		}
	}

	public static void setTarget(Entity entity) {
		currentTarget = entity;
	}

	public static Entity randomTarget(Entity attacker) {
		checkWin();
		attacker.currentSpell = attacker.r.nextInt(attacker.abilities.length);

		// if(entity.contains(attacker)){
		// return getTargetFromGroup(attacker, GameScreen.player.party);
		// }

		if (entity.contains(attacker)) {
			int target = attacker.r.nextInt(GameScreen.player.party.size());

			if (GameScreen.player.party.get(target).isDead) {
				entityAttack(attacker);
			} else {
				return GameScreen.player.party.get(target);
			}

		} else if (GameScreen.player.party.contains(attacker)) {
			int target = attacker.r.nextInt(entity.size());

			if (entity.get(target).isDead) {
				entityAttack(attacker);
			} else {
				return entity.get(target);
			}
		}

		return null;
	}

	public static Entity getTargetFromGroup(Entity attacker, ArrayList<Entity> party) {
		return null;
	}

	private static void battleWon(ArrayList<Entity> entity) {
		battleOver = true;
	}

	public static void checkWin() {
		if (isAllDead(GameScreen.player.party)) {
			battleWon(GameScreen.player.party);
			// GameScreen.exitBattleScreen();
		} else if (isAllDead(entity)) {
			battleWon(entity);
			// GameScreen.exitBattleScreen();
		}
	}

	public static boolean isAllDead(ArrayList<Entity> entity) {
		for (int i = 0; i < entity.size(); i++) {
			if (!entity.get(i).isDead) {
				return false;
			}
		}
		return true;
	}
}
