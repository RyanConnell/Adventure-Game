package com.Rhinodude.AdventureGame.Attacks;

import com.Rhinodude.AdventureGame.Display;
import com.Rhinodude.AdventureGame.ImageLoader;

public class Fireball extends Attack {

	public Fireball() {
		super(ImageLoader.fireballAttack, "Fireball", Display.scale, Display.scale, 25, 5, 7, 3);
	}

}
