package com.Rhinodude.AdventureGame.Entities;

import com.Rhinodude.AdventureGame.ImageLoader;
import com.Rhinodude.AdventureGame.Attacks.Fireball;
import com.Rhinodude.AdventureGame.Attacks.Frostbolt;
import com.Rhinodude.AdventureGame.Attacks.HealingWave;
import com.Rhinodude.AdventureGame.Attacks.Waterbolt;

public class Human extends Entity {

	public Human(int x, int y, int humanType) {
		super(ImageLoader.humanEntity_02, x, y);
		initType(humanType);
	}

	public void initType(int type) {
		switch (type) {			
		case 1: // Yellow Haired Guy
			isPlayerControlled = true;
			imgArray = ImageLoader.humanEntity_01;
			abilities[0] = new Waterbolt();
			abilities[1] = new HealingWave();
			abilities[2] = new Frostbolt();
			break;

		case 2: // Brown Hair, Green Shirt.
			isPlayerControlled = true;
			imgArray = ImageLoader.humanEntity_02;
			abilities[0] = new Waterbolt();
			abilities[1] = new Fireball();
			abilities[2] = new Frostbolt();
			break;
		}
	}

	public int getAnimationLength(int type) {
		switch (type) {
		case 2:
			isAnimated = false;
			return 1; // Battle Stance
		case 3:
			isAnimated = false;
			return 1; // Death Stance
		}
		return 0;
	}

	public int getAnimationStart(int type) {
		switch (type) {
		case 2:
			return 0; // Battle Stance
		case 3:
			return 1; // Death Stance
		}
		return 0;
	}

}
