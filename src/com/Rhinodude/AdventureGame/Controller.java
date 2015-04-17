package com.Rhinodude.AdventureGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import com.Rhinodude.AdventureGame.Entities.CorruptedHuman;
import com.Rhinodude.AdventureGame.Entities.Entity;
import com.Rhinodude.AdventureGame.Screen.BattleScreen;
import com.Rhinodude.AdventureGame.Screen.GameScreen;
import com.Rhinodude.AdventureGame.Screen.LevelCreatorScreen;

public class Controller implements KeyListener, MouseListener {
	ArrayList<Entity> battlePartyTest;

	public Controller() {
		battlePartyTest = new ArrayList<Entity>();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			GameScreen.player.moveUp = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			GameScreen.player.moveDown = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			GameScreen.player.moveLeft = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			GameScreen.player.moveRight = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_PLUS) {
			LevelCreatorScreen.currentTile++;
		}
		if (e.getKeyCode() == KeyEvent.VK_MINUS) {
			LevelCreatorScreen.currentTile--;
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			GameScreen.player.moveUp = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			GameScreen.player.moveDown = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			GameScreen.player.moveLeft = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			GameScreen.player.moveRight = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			battlePartyTest = new ArrayList<Entity>();
			battlePartyTest.add(new CorruptedHuman(0, 0));
			battlePartyTest.add(new CorruptedHuman(0, 0));
			battlePartyTest.add(new CorruptedHuman(0, 0));
			Display.screen.enterBattleScreen(battlePartyTest);
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		System.out.println(Camera.getRealX(e.getX()) + " | " + Camera.getRealY(e.getY()));
		if (Display.gamemode == 1) {
			LevelCreatorScreen.place(LevelCreatorScreen.currentTile, Camera.getRealX(e.getX()), Camera.getRealY(e.getY()));
		}
		if (BattleScreen.isActive) {
			int mouseX = e.getX();
			int mouseY = e.getY();
			BattleScreen.mouseClicked(mouseX, mouseY);
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

}
