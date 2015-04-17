package com.Rhinodude.AdventureGame.Tiles;

import java.awt.image.BufferedImage;

import com.Rhinodude.AdventureGame.ImageLoader;
import com.Rhinodude.AdventureGame.LevelLoader;

public class WaterTile extends WallTile {

	public WaterTile(int x, int y) {
		super(ImageLoader.waterTile, x, y, true);
		this.tileType = "Water";
	}

	public BufferedImage getOrientation() {
		// return ImageLoader.floorTiles_Outside[2][0];
		int orientation = 0;
		if (!hasOrientation && tiledY - 1 >= 0 && LevelLoader.tileMap[tiledX][tiledY - 1] != null) {
			if (LevelLoader.tileMap[tiledX][tiledY - 1].getTileType().equals("Water")) {
				orientation = 0;
				hasOrientation = true;
			}
		}
		if (!hasOrientation && tiledY - 1 >= 0 && LevelLoader.tileMapFloor[tiledX][tiledY - 1] != null) {
			if (LevelLoader.tileMapFloor[tiledX][tiledY - 1].getTileType().equals("Sand")) {
				orientation = 1;
				hasOrientation = true;
			}
			if (LevelLoader.tileMapFloor[tiledX][tiledY - 1].getTileType().equals("Grass")) {
				orientation = 2;
				hasOrientation = true;
			}
		}
		hasOrientation = true;
		return ImageLoader.waterTile[orientation][0];
	}
}
