package com.Rhinodude.AdventureGame.Attacks;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.Rhinodude.AdventureGame.Display;
import com.Rhinodude.AdventureGame.Entities.Entity;
import com.Rhinodude.AdventureGame.Screen.BattleScreen;

public class Attack {

	public BufferedImage[][] img;
	public String name;
	public Entity target;
	public Entity caster;
	public int tick;
	public int width, height;
	public int animationIndex;
	public int damage;
	public int manaCost;
	public boolean isActive;
	public int animationLimit = 8;
	public int animationTickMax = 4;
	public int maxTick, animationTick;
	public int direction;
	public Rectangle buttonRect;
	public boolean isFriendly;
	public int dotAmmount, dotTurns;

	public Attack(BufferedImage[][] img, String name, int width, int height, int damage, int manaCost, int dotAmmount, int dotTurns) {
		this.img = img;
		this.name = name;
		this.width = width;
		this.height = height;
		this.damage = damage;
		this.manaCost = manaCost;
		this.isActive = true;
		this.dotAmmount = dotAmmount;
		this.dotTurns = dotTurns;
		this.isFriendly = false;
		this.maxTick = 100;
	}

	public void update() {
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
	}

	public void cast(Entity caster, Entity target, int direction) {
		this.isActive = true;
		tick = 0;
		animationIndex = 0;
		this.caster = caster;
		this.target = target;
		this.direction = direction;
		BattleScreen.currentAttack = this;
	}

	public void selfCast() {
		Display.drawOverlay(img[animationIndex][1], ((caster.battleX) * BattleScreen.xScaleBattle), (caster.battleY) * BattleScreen.yScaleBattle, width, height);
	}

	public void movingCast() {
		switch (direction) {
		case 0:
			Display.drawOverlay(img[animationIndex][0], ((caster.battleX) * BattleScreen.xScaleBattle) + (int) moveX(), ((caster.battleY + 1) * BattleScreen.yScaleBattle) + (int) moveY(), width / 2, height / 2);
			break;
		case 1:
			Display.drawOverlay(img[animationIndex][0], ((caster.battleX) * BattleScreen.xScaleBattle) + (int) moveX(), (caster.battleY + 1) * (BattleScreen.yScaleBattle) + (int) moveY(), width / 2, height / 2);
			break;
		}
	}

	public double moveX() {
		double x1 = caster.battleX * BattleScreen.xScaleBattle;
		double x2 = target.battleX * BattleScreen.xScaleBattle;

		return ((x2 - x1) / (double) (maxTick)) * (double) tick;
	}

	public double moveY() {
		double y1 = caster.battleY * BattleScreen.yScaleBattle;
		double y2 = target.battleY * BattleScreen.yScaleBattle;
		return ((y2 - y1) / (double) (maxTick)) * (double) tick;
	}

	public void render() {
		if (!caster.isDead && target != null) {
			if (caster.equals(target)) {
				maxTick = (maxTick / 8) * 7;
				if (tick < ((maxTick) / 4) * 3) {
					selfCast();
				} else {
					renderDamage();
				}
			}
			if (!caster.equals(target)) {
				if (tick < (maxTick / 4) * 3) {
					movingCast();
				} else {
					renderDamage();
				}
			}
		} else {
			destroy();
		}
	}

	public void renderDamage() {
		if (!target.isDead) {
			Display.drawOverlay(img[animationIndex][1], ((target.battleX) * BattleScreen.xScaleBattle), (target.battleY) * BattleScreen.yScaleBattle, width, height);
		}
	}

	public void damage() {
		if (target != null) {
			caster.mana -= manaCost;
			target.damage(damage);
			addDamageOverTime(dotAmmount, dotTurns);
		}
	}

	public void addDamageOverTime(int ammount, int turns) {
		target.dotAmmount = ammount;
		target.dotTurns = turns;
		target.dotTick = 0;
	}

	public void destroy() {
		isActive = false;
		this.caster = null;
		this.target = null;
	}

}
