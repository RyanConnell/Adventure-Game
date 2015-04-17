package com.Rhinodude.AdventureGame;

import java.awt.image.BufferedImage;

import com.Rhinodude.AdventureGame.Entities.Player;
import com.Rhinodude.AdventureGame.Screen.BattleScreen;
import com.Rhinodude.AdventureGame.Screen.GameScreen;
import com.Rhinodude.AdventureGame.Tiles.GrassTile;
import com.Rhinodude.AdventureGame.Tiles.SandTile;
import com.Rhinodude.AdventureGame.Tiles.Tile;
import com.Rhinodude.AdventureGame.Tiles.TreeTile;
import com.Rhinodude.AdventureGame.Tiles.WaterTile;

public class LevelLoader {

	public static int mapWidth, mapHeight;
	public static BufferedImage currentMap;
	public static Tile[][] tileMap, tileMapFloor;
	public static int[][] colorCodes;
	public static int levelIndex;

	public static void loadMap(int levelIndex) {
		if (BattleScreen.isActive) {
			if (levelIndex < ImageLoader.battleMaps.size()) {
				currentMap = ImageLoader.battleMaps.get(levelIndex);
			} else {
				System.err.println("Error: Map Unavailable");
				return;
			}
		} else {
			if (levelIndex < ImageLoader.maps.size()) {
				currentMap = ImageLoader.maps.get(levelIndex);
			} else {
				System.err.println("Error: Map Unavailable");
				return;
			}
		}

		mapWidth = currentMap.getWidth();
		mapHeight = currentMap.getHeight();

		loadColorCodes();

		tileMap = new Tile[mapWidth][mapHeight];
		tileMapFloor = new Tile[mapWidth][mapHeight];
		for (int i = 0; i < mapWidth; i++) {
			for (int j = 0; j < mapHeight; j++) {
				int color = currentMap.getRGB(i, j);
				tileMapFloor[i][j] = new GrassTile(i, j);

				if (color == colorCodes[0][0]) {
					tileMapFloor[i][j] = new GrassTile(i, j);
					tileMap[i][j] = new TreeTile(i, j);
				} else if (color == colorCodes[1][0]) {
					tileMapFloor[i][j] = new GrassTile(i, j); // Grass
				} else if (color == colorCodes[2][0]) {
					tileMapFloor[i][j] = new SandTile(i, j); // Sand
				} else if (color == colorCodes[3][0]) {
					tileMap[i][j] = new WaterTile(i, j); // Water
				} else if (color == colorCodes[0][1]) {
					tileMapFloor[i][j] = new GrassTile(i, j);
					GameScreen.player = new Player(i, j);
				}
			}
		}
	}

	public static void loadColorCodes() {

		colorCodes = new int[ImageLoader.colorCodes.getWidth()][ImageLoader.colorCodes.getHeight()];
		if (ImageLoader.colorCodes != null) {
			for (int i = 0; i < ImageLoader.colorCodes.getWidth(); i++) {
				for (int j = 0; j < ImageLoader.colorCodes.getHeight(); j++) {
					colorCodes[i][j] = ImageLoader.colorCodes.getRGB(i, j);
				}
			}
		} else {
			System.err.println("Error: Colour Codes Unavilable");
			return;
		}
	}

	public static void loadBattleBackground() {
		loadMap(0);
	}
}
