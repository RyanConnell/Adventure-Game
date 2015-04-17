package com.Rhinodude.AdventureGame.Tiles;

import com.Rhinodude.AdventureGame.ImageLoader;

public class SandTile extends FloorTile {

	public SandTile(int x, int y) {
		super(ImageLoader.floorTiles_Outside[0][0], x, y, false);
		this.tileType = "Sand";
	}

}
