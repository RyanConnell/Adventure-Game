package com.Rhinodude.AdventureGame.Screen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import com.Rhinodude.AdventureGame.Camera;
import com.Rhinodude.AdventureGame.Display;
import com.Rhinodude.AdventureGame.ImageLoader;
import com.Rhinodude.AdventureGame.LevelLoader;
import com.Rhinodude.AdventureGame.Entities.Entity;
import com.Rhinodude.AdventureGame.Entities.Player;
import com.Rhinodude.AdventureGame.Tiles.Tile;

public class GameScreen extends Screen {
	Tile[][] tileMap;
	public static ArrayList<Entity> entityList, oldEntityList;
	public static Player player;
	public static Screen gameScreen;
	public static Camera camera;
	public static Random r;
	public static int currentMap;

	public int spawnTick, spawnMax, currentSpawned, spawnLimit;

	public GameScreen() {
		super(1);
		r = new Random();
		spawnMax = 500;
		spawnLimit = 10;
	}

	public void init() {
		entityList = new ArrayList<Entity>();
		currentMap = 0;
		LevelLoader.loadMap(currentMap);
		entityList.add(player);
		camera = new Camera(player);
	}

	public void update() {
		for (int i = 0; i < entityList.size(); i++) {
			entityList.get(i).update();
		}
	}

	public void render() {
		int screenDrawOffsetX = ((Camera.getXOffset()) / Display.scale)-1;
		int screenDrawOffsetY = ((Camera.getYOffset()) / Display.scale)-1;
		for (int j = 0; j < 14; j++) { //Default was 14 
			for (int i = 0; i < 13; i++) { //Default was 13
				if (i + screenDrawOffsetX >= 0 && i + screenDrawOffsetX < LevelLoader.mapWidth && j + screenDrawOffsetY >= 0 && j + screenDrawOffsetY < LevelLoader.mapHeight) {
					if (LevelLoader.tileMapFloor[i + screenDrawOffsetX][j + screenDrawOffsetY] != null) {
						LevelLoader.tileMapFloor[i + screenDrawOffsetX][j + screenDrawOffsetY].render();
					} else {
					}
				}
			}
		}

		for (int j = 0; j < 14; j++) { //Default was 14
			for (int i = 0; i < 13; i++) { //Default was 13
				if (i + screenDrawOffsetX >= 0 && i + screenDrawOffsetX < LevelLoader.mapWidth && j + screenDrawOffsetY >= 0 && j + screenDrawOffsetY < LevelLoader.mapHeight) {
					if (player.tiledX == i + screenDrawOffsetX && player.tiledY == j - 1 + screenDrawOffsetY) {
						player.render();
					}
					if (LevelLoader.tileMap[i + screenDrawOffsetX][j + screenDrawOffsetY] != null) {

						LevelLoader.tileMap[i + screenDrawOffsetX][j + screenDrawOffsetY].render();
					}
				} else {
					Tile.drawVoidTile(i + screenDrawOffsetX, j + screenDrawOffsetY);
				}
			}

		}
		for (int i = 1; i < entityList.size(); i++) {
			entityList.get(i).render();
		}
	}

	public void enterBattleScreen(ArrayList<Entity> entity) {
		oldEntityList = entityList;
		gameScreen = this;
		Display.screen = new BattleScreen(entity);	
	}

	public static void exitBattleScreen() {
		entityList = new ArrayList<Entity>();
		Display.screen = gameScreen;
		BattleScreen.isActive = false;
		LevelLoader.loadMap(currentMap);
		entityList.add(player);
		camera.reset(player);
	}
}
