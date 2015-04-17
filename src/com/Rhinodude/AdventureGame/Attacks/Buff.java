package com.Rhinodude.AdventureGame.Attacks;

import java.awt.image.BufferedImage;

import com.Rhinodude.AdventureGame.Display;
import com.Rhinodude.AdventureGame.Entities.Entity;
import com.Rhinodude.AdventureGame.Screen.BattleScreen;

public class Buff extends Attack {

	public int buffTurns;

	public Buff(BufferedImage[][] img, String name, int manaCost, int buffTurns) {
		super(img, name, 64, 64, 0, manaCost, 0, 0);
		this.buffTurns = buffTurns;
	}

	public void cast(Entity caster, Entity target, int direction) {
		this.isActive = true;
		tick = 0;
		animationIndex = 0;
		this.caster = caster;
		this.target = target;
		this.direction = direction;
		BattleScreen.currentAttack = this;
		target.currentBuff = this;
	}

	public void render() {
		if (isActive) {
			if (!caster.isDead && target != null) {
				if (caster == target) {
					selfCast();
				}
				if (tick < (maxTick / 4) * 3) {
					movingCast();
				} else {
					renderDamage();
				}
			} else {
				destroy();
			}
		}
	}

	public void renderBuff(Entity target) {
		if (target.currentBuff != null) {
			Display.drawOverlay(img[animationIndex][2], target.battleX * BattleScreen.xScaleBattle, target.battleY * BattleScreen.yScaleBattle, width, height);
		}
	}
	
	public boolean canAttack(Entity entity){
		return true;
	}

	public void update(Entity entity) {
		if (target == null || caster == null) {
			destroy();
		}
		tick++;
		animationTick++;
		if (animationTick >= animationTickMax) {
			animationTick = 0;
			if (animationIndex >= animationLimit - 1) {
				animationIndex = 0;
			} else {
				animationIndex++;
			}
		}
		if (tick >= maxTick) {
			this.damage();
			this.destroy();
		}
		updateBuff(target);
	}

	public void updateBuff(Entity target) {
		if (target.buffTick >= buffTurns) {
			endBuff(target);
			System.out.println("Terminated");
		}
	}

	public void perTurnToggle(Entity target) {
		// Ability activated each turn.
	}

	public void onDamageToggle(Entity target) {
		// Ability activated by Damage.
	}

	public void onTurnToggle(Entity target) {
		// Ability activated on Targets Turn.
	}

	public void onDestroyToggle(Entity target) {
		// Ability activated when Buff is Destroyed.
	}

	public void onDeathToggle(Entity target) {
		// Ability activated on targets death.
	}

	public void onCastToggle(Entity target) {
		// Ability activated when target casts.
	}

	public void damage() {
		isActive = false;
		target.buffTick = 0;
		target.currentBuff = this;
	}

	public void destroy() {
		isActive = false;
	}

	public void endBuff(Entity target) {
		System.out.println("Ending " + name + " buff.");
		target.buffTick = 0;
		target.currentBuff = null;
	}
}
