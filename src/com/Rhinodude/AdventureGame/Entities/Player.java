package com.Rhinodude.AdventureGame.Entities;

import java.awt.Color;

import com.Rhinodude.AdventureGame.Camera;
import com.Rhinodude.AdventureGame.Display;
import com.Rhinodude.AdventureGame.ImageLoader;
import com.Rhinodude.AdventureGame.Attacks.Fireball;
import com.Rhinodude.AdventureGame.Attacks.Frostbolt;
import com.Rhinodude.AdventureGame.Attacks.HealingWave;
import com.Rhinodude.AdventureGame.Attacks.Waterbolt;

public class Player extends Entity {

	public Player(int x, int y) {
		super(ImageLoader.playerEntity, x, y);
		this.party.add(new Human(0, 0, 1));
		this.party.add(new Human(0, 0, 2));
		isPlayerControlled = true;
		abilities[0] = new Fireball();
		abilities[1] = new Waterbolt();
		abilities[2] = new HealingWave();
		abilities[3] = new Frostbolt();
	}

	public void render() {
		if (Display.gamemode == 0) {
			Display.drawOverlay(imgArray[animationIndex][direction], Camera.getCenterX(), Camera.getCenterY(), width, height);
		}
	}
}
