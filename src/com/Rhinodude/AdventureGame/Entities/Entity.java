package com.Rhinodude.AdventureGame.Entities;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.Rhinodude.AdventureGame.Display;
import com.Rhinodude.AdventureGame.LevelLoader;
import com.Rhinodude.AdventureGame.Attacks.Attack;
import com.Rhinodude.AdventureGame.Attacks.Buff;
import com.Rhinodude.AdventureGame.Screen.BattleScreen;

public class Entity {

	BufferedImage[][] imgArray;
	public int x, y;
	public int tiledX, tiledY;
	public int width, height;
	int startX, startY;
	public boolean moveUp, moveDown, moveLeft, moveRight;
	public boolean canMoveUp, canMoveDown, canMoveLeft, canMoveRight;
	public int maxHealth, health, maxMana, mana;
	public Attack[] abilities;
	public int battleX, battleY;
	public Rectangle collisionRect, entityRect;
	public Rectangle rectUp, rectDown, rectLeft, rectRight;
	public Rectangle battleRect;
	public int moveSpeed;
	public ArrayList<Entity> party;
	public int animationIndex, animationType;
	public int animationLength = 4;
	public int idleAnimationIndex = 0;
	public int animationTick;
	public int direction = 0;
	public int animationTickMax = 5;
	public boolean isAnimated;
	public boolean isDead;
	public boolean canAttack, hasAttacked;
	public boolean isPlayerControlled = false;
	public int battleDirection;
	public int currentSpell = -1;
	public int dotAmmount, dotTick, dotTurns;
	public boolean dotHasHit = false;
	public Buff currentBuff;
	public int buffTick, buffTurns;
	public boolean buffHasHit;
	public boolean isSolid;

	// Ai Movement Stuff
	public Random r;
	public int moveTick = 100;

	public Entity(BufferedImage[][] img, int x, int y) {
		this.imgArray = img;
		this.width = Display.scale;
		this.height = Display.scale;
		this.x = x * Display.scale;
		this.y = y * Display.scale;
		this.startX = x;
		this.startY = y;
		isDead = false;
		isSolid = true;

		moveSpeed = Display.scale / 16;

		abilities = new Attack[6];

		this.maxHealth = 100;
		this.health = maxHealth;
		this.maxMana = 10;
		this.mana = maxMana;

		r = new Random();

		party = new ArrayList<Entity>(3);
		party.add(this);
	}

	public void update() {
		tiledX = (x / Display.scale);
		tiledY = (y / Display.scale);
		updateRectangles();
		move();
		animate();
		tiledX = x / Display.scale;
		tiledY = y / Display.scale;
	}

	public void animate() {
		animationTickMax = 10;
		int animationStart = getAnimationStart(animationType);
		int animationLength = getAnimationLength(animationType);
		animationTick++;
		if (animationTick >= animationTickMax) {
			if (animationIndex < animationLength) {
				if (isAnimated) {
					animationIndex++;
				}
			} else {
				animationIndex = animationStart;
			}
			animationTick = 0;
		}
	}

	public void changeAnimation(int type) {
		if (animationType != type) {
			animationType = type;
			animationIndex = getAnimationStart(type);
		}
	}

	public int getAnimationLength(int type) {
		switch (type) {
		case 0:
			isAnimated = false;
			return 1; // Idle
		case 1:
			isAnimated = true;
			return 2; // Running
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
		case 0:
			return 0; // Idle Stance
		case 1:
			return 1; // Running Stance
		case 2:
			return 3; // Battle Stance
		case 3:
			return 4; // Death Stance
		}
		return 0;
	}

	public void updateRectangles() {
		this.width = (int) (32 * 2.2);
		this.height = (int) (32 * 2.2);
		entityRect = new Rectangle(this.x, this.y + (height / 4) * 3, width, ((height / 4) / 2) * 3);
		collisionRect = new Rectangle(entityRect.x - (moveSpeed) * 4, entityRect.y - (moveSpeed) * 4, entityRect.width + (moveSpeed) * 8, entityRect.height + (moveSpeed) * 8);
		rectDown = new Rectangle(entityRect.x, entityRect.y + entityRect.height, entityRect.width, moveSpeed);
		rectUp = new Rectangle(entityRect.x, entityRect.y - moveSpeed, entityRect.width, moveSpeed);
		rectLeft = new Rectangle(entityRect.x - moveSpeed, entityRect.y, moveSpeed, entityRect.height);
		rectRight = new Rectangle(entityRect.x + entityRect.width, entityRect.y, moveSpeed, entityRect.height);

	}

	public void render() {
		if (Display.gamemode != 1) {
			Display.drawImage(imgArray[animationIndex][direction], x, y - Display.scale * 2, width, height);
			// Display.drawRectangle(collisionRect, Color.blue, false);
			// Display.drawRectangle(entityRect, Color.green, false);
		}
	}

	public void turnReset() {
		if (checkDeath()) {

		} else {
			canAttack = true;
			hasAttacked = false;
			if (currentBuff != null) {
				if (!currentBuff.canAttack(this)) {
					BattleScreen.nextTurn();
					System.out.println("Next Round! Buff Lasted for " + buffTick);
				}
			}
			if (BattleScreen.currentAttack != null) {
			}
		}
	}

	public boolean canAttack() {
		if (currentBuff != null) {
			currentBuff.update(this);
			if (!currentBuff.canAttack(this)) {
				return false;
			}
		}
		return true;
	}

	public boolean checkDeath() {
		if (this.isDead) {
			return true;
		}
		return false;
	}

	public void updateBattle() {
		animate();
		if (BattleScreen.currentAttacker == this && this.isDead) {
			BattleScreen.nextTurn();
		}
		System.out.println("1");
		if (canAttack && !hasAttacked && !isPlayerControlled) {
			BattleScreen.entityAttack(this);
		}
		System.out.println("2");
		if (hasAttacked && canAttack && BattleScreen.currentAttack != null && !BattleScreen.currentAttack.isActive) {
			canAttack = false;
			BattleScreen.nextTurn();
		}

		System.out.println("3");
		if (this.health >= 1) {
			changeAnimation(2);
			isDead = false;
		} else {
			changeAnimation(3);
			isDead = true;
		}
		System.out.println("4");
	}

	public void perTurnUpdates() {
		dotHasHit = false;
		dotUpdate();
		buffUpdate();
	}

	public void kill() {
		isDead = true;
		canAttack = false;
		hasAttacked = false;
	}

	public void renderBattle(int x, int y, int direction) {
		battleX = x;
		battleY = y;
		int xScaleBattle = 64;
		int yScaleBattle = 32;
		battleDirection = direction;
		if (battleRect == null) {
			battleRect = new Rectangle(battleX * xScaleBattle, battleY * yScaleBattle, width, height);
		}
		Display.drawOverlay(imgArray[animationIndex][direction], (battleX * xScaleBattle), (battleY * yScaleBattle), 64, 64);
		int healthBarHeight = (height / 8) * 7;
		if (!isDead) {
			switch (direction) {
			case 0:
				// Display.drawRectangle(battleRect, Color.blue, true);
				Display.fillRect((battleX + 1) * xScaleBattle, ((battleY * yScaleBattle) + height / 8), xScaleBattle / 8, (int) ((double) (healthBarHeight * health) / maxHealth), Color.red);
				Display.drawRect((battleX + 1) * xScaleBattle, (battleY * yScaleBattle) + height / 8, xScaleBattle / 8, (height / 8) * 7, Color.black);
				break;
			case 1:
				// Display.drawRectangle(battleRect, Color.blue, true);
				Display.fillRect((battleX) * xScaleBattle - yScaleBattle / 8, (battleY * yScaleBattle) + height / 8, xScaleBattle / 8, (int) ((double) (healthBarHeight * health) / maxHealth), Color.red);
				Display.drawRect((battleX) * xScaleBattle - yScaleBattle / 8, (battleY * yScaleBattle) + height / 8, xScaleBattle / 8, (height / 8) * 7, Color.black);
				break;
			}
		}
		if (currentBuff != null) {
			currentBuff.render();
			currentBuff.renderBuff(this);
		}
	}

	public void dotUpdate() {
		if (!dotHasHit && dotAmmount >= 0 && dotTick < dotTurns) {
			damage(dotAmmount);
			dotTick++;
			dotHasHit = true;
		}
	}

	public void buffUpdate() {
		if (currentBuff != null) {
			currentBuff.perTurnToggle(this);
		}
	}

	public void damage(int damage) {
		if (!isDead) {
			health -= damage;
		}
		if (health <= 0) {
			kill();
		}
	}

	public void heal(int healAmmount) {
		health += healAmmount;
	}

	public void cast(int index, Entity target) {
		if (canAttack && !hasAttacked && !isDead) {
			if (currentSpell != -1 && abilities[currentSpell] != null) {
				abilities[currentSpell].cast(this, target, battleDirection);
				hasAttacked = true;
			}
			currentSpell = -1;
		}
	}

	public void chooseDirection() {
		int dir = r.nextInt(8);

		switch (dir) {
		case 0:
			moveUp = true;
			moveDown = false;
			moveLeft = false;
			moveRight = false;
		case 1:
			moveUp = false;
			moveDown = true;
			moveLeft = false;
			moveRight = false;
		case 2:
			moveUp = false;
			moveDown = false;
			moveLeft = true;
			moveRight = false;
		case 3:
			moveUp = false;
			moveDown = false;
			moveLeft = false;
			moveRight = true;
		}
	}

	public void resetDirection() {
		canMoveUp = true;
		canMoveDown = true;
		canMoveLeft = true;
		canMoveRight = true;
	}

	public void checkCollisions() {
		if (isSolid) {
			int tiledX = (x / Display.scale) - 1;
			int tiledY = (y / Display.scale) - 1;

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (i + tiledX >= 0 && i + tiledX < LevelLoader.mapWidth && j + tiledY >= 0 && j + tiledY < LevelLoader.mapHeight) {
						if (LevelLoader.tileMap[i + tiledX][j + tiledY] != null) {
							LevelLoader.tileMap[i + tiledX][j + tiledY].checkCollision(this);
						}
					}
				}
			}
		}
	}

	public void move() {
		checkCollisions();
		if (moveLeft || moveRight || moveUp || moveDown) {
			changeAnimation(1);
		} else {
			changeAnimation(0);
		}
		if (moveLeft && canMoveLeft) {
			direction = 0;
			x -= moveSpeed;
		}
		if (moveRight && canMoveRight) {
			direction = 1;
			x += moveSpeed;
		}
		if (moveUp && canMoveUp) {
			y -= moveSpeed;
		}
		if (moveDown && canMoveDown) {
			y += moveSpeed;
		}
		resetDirection();
	}

}
