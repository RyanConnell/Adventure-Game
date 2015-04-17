package com.Rhinodude.AdventureGame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageLoader {
	public static BufferedImage[][] wallTiles_Outside, floorTiles_Outside, waterTile;
	public static BufferedImage[][] playerEntity, darkHumanEntity, humanEntity_01, humanEntity_02;
	public static BufferedImage[][] fireballAttack, waterboltAttack, arcaneboltAttack;
	public static BufferedImage[][] frostboltAttack, healingWave;
	public static BufferedImage treeShadow;
	public static ArrayList<BufferedImage> maps = new ArrayList<BufferedImage>();
	public static ArrayList<BufferedImage> battleMaps = new ArrayList<BufferedImage>();
	public static BufferedImage colorCodes, battleStarter;
	public static BufferedImage battleScreenOverlay;
	public static BufferedImage targetedImage, spellSelected;

	public static void loadImages() {
		wallTiles_Outside = new BufferedImage[1][1];
		floorTiles_Outside = new BufferedImage[4][1];
		waterTile = new BufferedImage[3][1];
		playerEntity = new BufferedImage[5][4];
		darkHumanEntity = new BufferedImage[5][4];
		humanEntity_01 = new BufferedImage[2][4];
		humanEntity_02 = new BufferedImage[2][4];
		fireballAttack = new BufferedImage[8][2];
		waterboltAttack = new BufferedImage[8][2];
		arcaneboltAttack = new BufferedImage[8][2];
		frostboltAttack = new BufferedImage[8][3];
		healingWave = new BufferedImage[8][2];
		
		loadSpriteSheet(playerEntity, getImage("/Graphics/Entities/PlayerSheet.png"), 5, 2, 32, 32);
		loadSpriteSheet(darkHumanEntity, getImage("/Graphics/Entities/DarkHuman.png"), 5, 2, 32, 32);
		loadSpriteSheet(humanEntity_01, getImage("/Graphics/Entities/HumanSheet_01.png"), 2, 2, 32, 32);
		loadSpriteSheet(humanEntity_02, getImage("/Graphics/Entities/HumanSheet_02.png"), 2, 2, 32, 32);
		
		loadSpriteSheet(wallTiles_Outside, getImage("/Graphics/Tiles/wallTiles_Outside.png"), 1, 1, 128, 128*2);
		loadSpriteSheet(floorTiles_Outside, getImage("/Graphics/Tiles/floorTiles_Outside.png"), 3, 1, 128, 128*2);
		loadSpriteSheet(waterTile, getImage("/Graphics/Tiles/waterTile.png"), 3, 1, 128, 128*2);
		
		loadSpriteSheet(fireballAttack, getImage("/Graphics/Attacks/fireball.png"), 8, 2, 128, 128);
		loadSpriteSheet(waterboltAttack, getImage("/Graphics/Attacks/waterbolt.png"), 8, 2, 128, 128);
		loadSpriteSheet(arcaneboltAttack, getImage("/Graphics/Attacks/arcanebolt.png"), 8, 2, 128, 128);
		loadSpriteSheet(frostboltAttack, getImage("/Graphics/Attacks/frostbolt.png"), 8, 3, 128, 128);
		loadSpriteSheet(healingWave, getImage("/Graphics/Attacks/heal.png"), 8, 2, 128, 128);
		
		//battleStarter = getImage("/Graphics/Tiles/battleTile.png");
		
		battleScreenOverlay = getImage("/Graphics/Overlays/BattleScreenOverlay.png");
		colorCodes = getImage("/Graphics/colorCodes.png");
		targetedImage = getImage("/Graphics/Other/targetedImage.png");
		spellSelected = getImage("/Graphics/Other/SpellSelected.png");
		
		treeShadow = getImage("/Graphics/Tiles/treeShadow.png");

		battleMaps.add(getImage("/Graphics/Maps/battleMap_01.png"));
		
		maps.add(getImage("/Graphics/Maps/map_01.png"));
		maps.add(getImage("/Graphics/Maps/map_03.png"));
	}

	public static void loadSpriteSheet(BufferedImage[][] tileArray, BufferedImage spriteSheet, int tilesX, int tilesY, int tileWidth, int tileHeight) {
		for (int i = 0; i < tilesX; i++) {
			for (int j = 0; j < tilesY; j++) {
				tileArray[i][j] = spriteSheet.getSubimage(i * tileWidth, j * tileHeight, tileWidth, tileHeight);
			}
		}
	}

	public static BufferedImage getImage(String location) {
		try {
			return ImageIO.read(Frame.class.getResource(location));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
