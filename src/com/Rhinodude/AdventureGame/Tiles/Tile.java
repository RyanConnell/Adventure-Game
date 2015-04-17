package com.Rhinodude.AdventureGame.Tiles;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.Rhinodude.AdventureGame.Display;
import com.Rhinodude.AdventureGame.ImageLoader;
import com.Rhinodude.AdventureGame.Entities.Entity;
import com.Rhinodude.AdventureGame.Screen.BattleScreen;

public class Tile {

	public BufferedImage img;
	int x, y, drawX, drawY;
	int tiledX, tiledY;
	int width, height;
	String tileType;
	public int imageIndex;
	public boolean isSolid = false;
	Rectangle collisionRect;

	public Tile(BufferedImage img, int x, int y, boolean isSolid) {
		this.img = img;
		this.width = Display.scale;
		this.height = Display.scale * 2;
		this.x = x * Display.scale;
		this.y = y * Display.scale;
		this.drawX = x * Display.scale;
		this.drawY = (y * Display.scale) - Display.scale;
		this.tiledX = x;
		this.tiledY = y;
		this.isSolid = isSolid;
		this.tileType = "Null";
		collisionRect = new Rectangle(this.x, this.y, Display.scale, Display.scale);
	}

	public void render() {
		Display.drawImage(img, drawX, drawY, width, height);
		// Display.drawRectangle(collisionRect, Color.blue);
	}

	public void renderBattle() {
		Display.drawOverlay(img, drawX - BattleScreen.xScaleBattle, drawY, width, height);
	}

	public String getTileType() {
		return tileType;
	}

	public void checkCollision(Entity entity) {
		if (entity.collisionRect.intersects(collisionRect)) {
			if (isSolid) {
				if (entity.rectLeft.intersects(collisionRect)) {
					entity.canMoveLeft = false;
				}
				if (entity.rectRight.intersects(collisionRect)) {
					entity.canMoveRight = false;
				}
				if (entity.rectUp.intersects(collisionRect)) {
					entity.canMoveUp = false;
				}
				if (entity.rectDown.intersects(collisionRect)) {
					entity.canMoveDown = false;
				}
			}
		}
	}

	public static void drawVoidTile(int x, int y) {
		// Add Void Tile for when another tile can't be shown. (out of bounds)
		Display.drawImage(ImageLoader.waterTile[0][0], x * Display.scale, (y * Display.scale) - Display.scale, Display.scale, Display.scale * 2);
	}
}
