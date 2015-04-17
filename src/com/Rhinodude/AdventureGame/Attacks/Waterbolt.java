package com.Rhinodude.AdventureGame.Attacks;

import com.Rhinodude.AdventureGame.Display;
import com.Rhinodude.AdventureGame.ImageLoader;

public class Waterbolt extends Attack {

	public Waterbolt() {
		super(ImageLoader.waterboltAttack, "Water Bolt", Display.scale, Display.scale, 35, 5, 0, 0);
	}

}
