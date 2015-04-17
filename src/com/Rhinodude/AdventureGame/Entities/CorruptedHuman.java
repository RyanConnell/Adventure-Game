package com.Rhinodude.AdventureGame.Entities;

import com.Rhinodude.AdventureGame.ImageLoader;
import com.Rhinodude.AdventureGame.Attacks.*;

public class CorruptedHuman extends Entity {

	public CorruptedHuman(int x, int y) {
		super(ImageLoader.darkHumanEntity, x, y);
		abilities[0] = new ArcaneBolt();
		abilities[1] = new Waterbolt();
		abilities[2] = new Fireball();
	}

}
