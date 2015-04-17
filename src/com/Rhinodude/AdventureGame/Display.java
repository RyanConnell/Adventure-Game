package com.Rhinodude.AdventureGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import com.Rhinodude.AdventureGame.Screen.GameScreen;
import com.Rhinodude.AdventureGame.Screen.LevelCreatorScreen;
import com.Rhinodude.AdventureGame.Screen.Screen;

public class Display extends Canvas implements Runnable {
	Thread gameThread;
	public static Image dbImage;
	public static Graphics dbGraphics;
	public static Screen screen;
	public static int scale = (int) (32 * 2.5); //Default was (32 * 2.5);
	public static Controller controller;
	public static int gamemode = 0;
	
	public int xOffset = 0, yOffset = 0;

	public void init(String[] args) {
		if (args != null && args.length >= 1 && args[0].equals("LevelEditorMode")) {
			gamemode = 1;
		}
		controller = new Controller();
		addKeyListener(controller);
		addMouseListener(controller);
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void paint(Graphics g) {
		dbImage = createImage(Frame.fWidth, Frame.fHeight);
		dbGraphics = dbImage.getGraphics();

		if (screen != null) {
			if (GameScreen.camera != null) {
				GameScreen.camera.update();
			}
			screen.render();
		}
		
		g.drawImage(dbImage, 0, 0, Frame.fWidth-xOffset, Frame.fHeight-yOffset, null);
		g.dispose();
	}

	public void update(Graphics g) {
		paint(g);
	}

	public static void drawImage(BufferedImage img, int x, int y, int w, int h) {
		dbGraphics.drawImage(img, x - GameScreen.camera.getXOffset(), y - GameScreen.camera.getYOffset(), w, h, null);
	}

	public static void drawOverlay(BufferedImage img, int x, int y, int w, int h) {
		dbGraphics.drawImage(img, x, y, w, h, null);
	}

	public static void drawRect(int x, int y, int w, int h, Color c) {
		dbGraphics.setColor(c);
		dbGraphics.drawRect(x, y, w, h);
	}

	public static void drawRectangle(Rectangle rect, Color c, boolean isOverlay) {
		dbGraphics.setColor(c);
		if (isOverlay) {
			dbGraphics.drawRect(rect.x, rect.y, rect.width, rect.height);
		} else {
			dbGraphics.drawRect(rect.x - GameScreen.camera.getXOffset(), rect.y - GameScreen.camera.getYOffset(), rect.width, rect.height);
		}
	}

	public static void fillRect(int x, int y, int w, int h, Color c) {
		dbGraphics.setColor(c);
		dbGraphics.fillRect(x, y, w, h);
	}

	public static void fillRectangle(Rectangle rect, Color c) {
		dbGraphics.setColor(c);
		dbGraphics.fillRect(rect.x, rect.y, rect.width, rect.height);
	}

	public static void drawText(String string, int x, int y, Color c, boolean isOverlay) {
		Font f = new Font("Dialog", Font.PLAIN, 20);
		dbGraphics.setFont(f);
		dbGraphics.setColor(c);
		FontMetrics fm = dbGraphics.getFontMetrics(f);
		Rectangle2D stringRect = fm.getFont().getStringBounds(string, fm.getFontRenderContext());
		dbGraphics.drawString(string, (int) (x - stringRect.getWidth() / 2), y);
	}

	public void update() {
		if (screen != null) {
			screen.update();
		}
	}

	public void run() {
		ImageLoader.loadImages();
		if (gamemode == 1) {
			screen = new LevelCreatorScreen();
		} else {
			screen = new GameScreen();
		}
		double startTime = System.currentTimeMillis();
		double timePassed = 0;
		double currentTime;
		int fpsTick = 0;
		while (true) {
			try {
				currentTime = System.currentTimeMillis();
				if (currentTime - startTime >= 1000 / 60) {
					update();
					repaint();
					fpsTick++;
					timePassed += currentTime - startTime;
					startTime = currentTime;
				}
				if (timePassed >= 1000) {
					Frame.frame.setTitle(Frame.fTitle + " | " + fpsTick + " FPS");
					fpsTick = 0;
					timePassed = 0;
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	public static void drawGrid(int width, int height) {
		for (int i = 0; i < Frame.fWidth / width; i++) {
			for (int j = 0; j < Frame.fHeight / height; j++) {
				Display.drawRect(i * width, j * height, width, height, Color.black);
			}
		}

	}

}
