package com.Rhinodude.AdventureGame.Tiles;

import java.awt.image.BufferedImage;

import com.Rhinodude.AdventureGame.Display;
import com.Rhinodude.AdventureGame.ImageLoader;
import com.Rhinodude.AdventureGame.LevelLoader;
import com.Rhinodude.AdventureGame.Screen.BattleScreen;

public class WallTile extends Tile {
	public boolean hasOrientation = false;
	public boolean hasShadow;
	public BufferedImage shadow;

	public WallTile(BufferedImage[][] img, int x, int y, boolean isSolid) {
		super(img[0][0], x, y, isSolid);
	}

	public void render() {
		if (!hasOrientation) {
			img = getOrientation();
		}
		if(hasShadow){
			Display.drawImage(shadow, drawX+width/2, drawY+height-10, width, width);
		}
		Display.drawImage(img, drawX, drawY, width, height);
		// Display.drawRectangle(collisionRect, Color.magenta);
	}

	public void renderBattle() {
		if (!hasOrientation) {
			img = getOrientation();
		}
		Display.drawOverlay(img, drawX - BattleScreen.xScaleBattle, drawY, width, height);
		// Display.drawRectangle(collisionRect, Color.magenta);
	}

	public BufferedImage getOrientation() {
		int orientation = 0;
		if (tiledX + 1 < LevelLoader.mapWidth && LevelLoader.tileMap[tiledX + 1][tiledY] != null && (LevelLoader.tileMapFloor[tiledX + 1][tiledY].getTileType() == this.tileType || LevelLoader.tileMap[tiledX + 1][tiledY].getTileType() == this.tileType)) {
			orientation += 1;
		}
		if (tiledX - 1 > 0 && LevelLoader.tileMap[tiledX - 1][tiledY] != null && (LevelLoader.tileMapFloor[tiledX - 1][tiledY].getTileType() == this.tileType || LevelLoader.tileMap[tiledX - 1][tiledY].getTileType() == this.tileType)) {
			orientation += 2;
		}
		if (tiledY + 1 < LevelLoader.mapHeight && LevelLoader.tileMap[tiledX][tiledY + 1] != null && (LevelLoader.tileMapFloor[tiledX][tiledY + 1].getTileType() == this.tileType || LevelLoader.tileMap[tiledX][tiledY + 1].getTileType() == this.tileType)) {
			orientation += 4;
		}
		if (tiledY - 1 > 0 && LevelLoader.tileMap[tiledX][tiledY - 1] != null && (LevelLoader.tileMapFloor[tiledX][tiledY - 1].getTileType() == this.tileType || LevelLoader.tileMap[tiledX][tiledY - 1].getTileType() == this.tileType)) {
			orientation += 8;
		}
		hasOrientation = true;

		return ImageLoader.wallTiles_Outside[0][0];
	}
}
