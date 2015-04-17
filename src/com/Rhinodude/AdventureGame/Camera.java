package com.Rhinodude.AdventureGame;

import com.Rhinodude.AdventureGame.Entities.Entity;
import com.Rhinodude.AdventureGame.Screen.GameScreen;

public class Camera {
	static int xOffset, yOffset;

	public static Entity entity;

	public Camera(Entity entity) {
		Camera.entity = entity;
	}
	
	public void reset(Entity entity){
		Camera.entity = entity;
	}

	public void update() {
		xOffset = (entity.x + (entity.width / 2)) - Frame.fWidth / 2;
		yOffset = (entity.y + (entity.height / 2)) - (Frame.fHeight / 2);
	}

	public static int getXOffset() {
		return xOffset;
	}

	public static int getYOffset() {
		return yOffset;
	}

	public static int getCenterX() {
		return (int) ((Frame.fWidth / 2) - (entity.width / 2));
	}

	public static int getCenterY() {
		return (int) ((Frame.fHeight / 2) - (entity.height / 2));
	}

	public static int getRealX(int x) {
		return (int) (x + xOffset)/Display.scale;
	//	x - GameScreen.camera.getXOffset(), y - GameScreen.camera.getYOffset()
	}

	public static int getRealY(int y) {
		return (int) (y + yOffset)/Display.scale;
	}
}