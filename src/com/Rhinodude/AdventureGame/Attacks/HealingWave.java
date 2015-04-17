package com.Rhinodude.AdventureGame.Attacks;

import com.Rhinodude.AdventureGame.Display;
import com.Rhinodude.AdventureGame.ImageLoader;

public class HealingWave extends Attack {

	public HealingWave() {
		super(ImageLoader.healingWave, "Healing Wave", Display.scale, Display.scale, 50, 5, 0, 0);
		this.isFriendly = true;
	}

	public void damage() {
		if (target != null) {
			caster.mana -= manaCost;
			if (target.health + damage >= target.maxHealth) {
				target.health = target.maxHealth;
			} else {
				target.heal(damage);
			}
		}
	}

}
