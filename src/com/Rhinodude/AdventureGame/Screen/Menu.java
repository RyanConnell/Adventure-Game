package com.Rhinodude.AdventureGame.Screen;

import java.awt.Color;
import java.awt.Rectangle;

import com.Rhinodude.AdventureGame.Display;

public class Menu {

	public static int currentTab;
	public static String[] nameArray;
	public static Rectangle[] buttonRect;
	public static int size = 5;
	public static int menuHeight = 194, menuWidth = 683;
	public static int menuX = 147, menuY = 357;

	public Menu(int currentTab) {
		Menu.currentTab = currentTab;
		init();
	}

	public void changeTab(int i) {
		currentTab = i;
	}

	public void init() {
		nameArray = new String[] { "Stats", "Inventory", "Equipment", "?", "Flee" };
		buttonRect = new Rectangle[5];
		for (int i = 0; i < 5; i++) {
			buttonRect[i] = new Rectangle(8, 378 + (34 * i) + i, 133, 34);
		}
	}

	public void render() {
		for (int i = 0; i < 5; i++) {
			Display.drawText(nameArray[i], 75, 402 + (34 * i) + i, Color.white, true);
		}
		switch (currentTab) {
		case 0:
			renderStats();
			break;
		case 1:
			renderInventory();
			break;
		case 2:
			renderEquipment();
			break;
		case 3:
			break;
		case 4:
			flee();
			break;
		}
	}

	public void renderStats() {
		Display.drawText("Statistics Tab", 215, 380, Color.white, true);
		int divisor = 32;
		int spaceWidth = menuWidth / divisor, spaceHeight = menuHeight / divisor;
		int sectionWidth = (spaceWidth * (divisor - 4)) / 3, sectionHeight = (spaceHeight * (divisor - 3)) / 2;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				Display.drawRect(menuX + (spaceWidth * (i + 1)) + (sectionWidth * i), menuY + (spaceHeight * (j + 1)) + (sectionHeight * j), sectionWidth, sectionHeight, Color.red);
			}
		}
	}

	public void renderInventory() {
		Display.drawText("Inventory Tab", 215, 380, Color.white, true);
		int divisor = 32;
		int spaceWidth = menuWidth / (divisor * 2), spaceHeight = menuHeight / divisor;
		int sectionWidth = (spaceWidth * (divisor - 4)) / 3, sectionHeight = (spaceHeight * (divisor - 3)) / 2;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 2; j++) {
				Display.drawRect(menuX + (spaceWidth * (i + 1)) + (sectionWidth * i), menuY + (spaceHeight * (j + 1)) + (sectionHeight * j), sectionWidth, sectionHeight, Color.red);
			}
		}
	}

	public void renderEquipment() {
		Display.drawText("Equipment Tab", 220, 380, Color.white, true);
	}

	public void flee() {
		GameScreen.exitBattleScreen();
	}

	public void update() {

	}
}
