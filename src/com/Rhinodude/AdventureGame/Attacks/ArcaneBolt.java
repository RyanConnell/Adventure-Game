package com.Rhinodude.AdventureGame.Attacks;

import com.Rhinodude.AdventureGame.Display;
import com.Rhinodude.AdventureGame.ImageLoader;


public class ArcaneBolt extends Attack {

	public ArcaneBolt() {
		super(ImageLoader.arcaneboltAttack, "Arcane Bolt", Display.scale, Display.scale, 20, 10, 5, 2);
	}

}
