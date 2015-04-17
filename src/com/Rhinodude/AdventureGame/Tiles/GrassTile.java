package com.Rhinodude.AdventureGame.Tiles;

import java.util.Random;

import com.Rhinodude.AdventureGame.ImageLoader;

public class GrassTile extends FloorTile {

	public GrassTile(int x, int y) {
		super(ImageLoader.floorTiles_Outside[1][0], x, y, false);
		this.tileType = "Grass";
	}
	
}
