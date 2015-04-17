package com.Rhinodude.AdventureGame.Attacks;

import com.Rhinodude.AdventureGame.ImageLoader;
import com.Rhinodude.AdventureGame.Entities.Entity;
import com.Rhinodude.AdventureGame.Screen.BattleScreen;

public class Frostbolt extends Buff {

	public Frostbolt() {
		super(ImageLoader.frostboltAttack, "Frost Bolt", 10, 3);
	}

	public boolean canAttack(Entity target) {
		target.buffTick++;
		if (target.buffTick <= buffTurns) {
			return false;
		} else {
			endBuff(target);
			return true;
		}
	}

}
