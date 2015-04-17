package com.Rhinodude.AdventureGame.Screen;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

import com.Rhinodude.AdventureGame.Camera;
import com.Rhinodude.AdventureGame.Display;
import com.Rhinodude.AdventureGame.LevelLoader;
import com.Rhinodude.AdventureGame.Tiles.GrassTile;
import com.Rhinodude.AdventureGame.Tiles.SandTile;
import com.Rhinodude.AdventureGame.Tiles.Tile;
import com.Rhinodude.AdventureGame.Tiles.TreeTile;
import com.Rhinodude.AdventureGame.Tiles.WaterTile;

public class LevelCreatorScreen extends GameScreen {
	int screenType;
	public static int currentTile;
	public static int maxTiles = 4;

	public LevelCreatorScreen() {
		r = new Random();
		spawnMax = 500;
		spawnLimit = 10;
		player.isSolid = false;
		createMap();
	}

	public void update() {
		if (currentTile <= 0) {
			currentTile = maxTiles;
		}
		if (currentTile >= maxTiles) {
			currentTile = 0;
		}
		for (int i = 0; i < entityList.size(); i++) {
			entityList.get(i).update();
		}
	}

	public void render() {
		screenType = 4;
		int screenDrawOffsetX = ((Camera.getXOffset()) / Display.scale) - 1;
		int screenDrawOffsetY = ((Camera.getYOffset()) / Display.scale) - 1;
		for (int j = 0; j < 14; j++) {
			for (int i = 0; i < 13; i++) {
				if (i + screenDrawOffsetX >= 0 && i + screenDrawOffsetX < LevelLoader.mapWidth && j + screenDrawOffsetY >= 0 && j + screenDrawOffsetY < LevelLoader.mapHeight) {
					if (LevelLoader.tileMapFloor[i + screenDrawOffsetX][j + screenDrawOffsetY] != null) {
						LevelLoader.tileMapFloor[i + screenDrawOffsetX][j + screenDrawOffsetY].render();
					} else {
					}
				}
			}
		}

		for (int j = 0; j < 14; j++) {
			for (int i = 0; i < 13; i++) {
				if (i + screenDrawOffsetX >= 0 && i + screenDrawOffsetX < LevelLoader.mapWidth && j + screenDrawOffsetY >= 0 && j + screenDrawOffsetY < LevelLoader.mapHeight) {
					if (player.tiledX == i + screenDrawOffsetX && player.tiledY == j - 1 + screenDrawOffsetY) {
						player.render();
					}
					if (LevelLoader.tileMap[i + screenDrawOffsetX][j + screenDrawOffsetY] != null) {

						LevelLoader.tileMap[i + screenDrawOffsetX][j + screenDrawOffsetY].render();
					}
					Display.drawRectangle(new Rectangle((i + screenDrawOffsetX) * Display.scale, (j + screenDrawOffsetY) * Display.scale), Color.red, false);
				} else {
					Tile.drawVoidTile(i + screenDrawOffsetX, j + screenDrawOffsetY);
				}
			}
		}
		Display.fillRect(Display.scale / 2, Display.scale / 2, Display.scale / 4, Display.scale / 2, Color.cyan);
		Display.drawText("Current Block ID: " + currentTile, 32, 32, Color.black, true);
	}

	public static void createMap() {
		int maxSize = 100;
		System.out.println("Creating New Map");
		Tile[][] tileMap = new Tile[maxSize][maxSize];
		for (int i = 0; i < maxSize; i++) {
			for (int j = 0; j < maxSize; j++) {
				tileMap[i][j] = new WaterTile(i, j);
			}
		}
		LevelLoader.tileMapFloor = new Tile[maxSize][maxSize];
		LevelLoader.tileMap = tileMap;
	}

	public static void place(int tile, int x, int y) {
		System.out.println("Placing Tile ID:" + tile);
		switch (tile) {
		case 0:
			LevelLoader.tileMap[x][y] = new WaterTile(x, y);
		case 1:
			LevelLoader.tileMap[x][y] = new TreeTile(x, y);
		case 2:
			LevelLoader.tileMapFloor[x][y] = new GrassTile(x, y);
		case 3:
			LevelLoader.tileMapFloor[x][y] = new SandTile(x, y);
		case 4:
		}
	}
}