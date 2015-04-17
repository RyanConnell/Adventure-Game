package com.Rhinodude.AdventureGame.Tiles;

import java.awt.image.BufferedImage;

import com.Rhinodude.AdventureGame.ImageLoader;

public class TreeTile extends WallTile {
	
	public TreeTile(int x, int y) {
		super(ImageLoader.wallTiles_Outside, x, y, true);
		this.tileType = "Tree";
		this.hasShadow = false;
	}

	public BufferedImage getOrientation() {
		return img;
	}

}
