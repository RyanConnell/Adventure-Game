package com.Rhinodude.AdventureGame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.Rhinodude.AdventureGame.Screen.GameScreen;

public class GameLoader {

	static BufferedReader reader;
	static BufferedWriter writer;
	static File saveFile;
	static String[] loadedFile;
	static String[][] statFile;
	static int loadFileLength = 2;

	public GameLoader() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void init() throws Exception {
		loadedFile = new String[loadFileLength];
		statFile = new String[loadFileLength][2];

		chooseSaveFile(0);

		reader = new BufferedReader(new FileReader(saveFile));
		writer = new BufferedWriter(new FileWriter(saveFile));
	}

	public static void chooseSaveFile(int i) {
		saveFile = new File("C:\\Users\\Ryan Connell\\Desktop\\saveFile_01.txt");
		if (saveFile == null) {
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		saveFile = new File("C:\\Users\\Ryan Connell\\Desktop\\saveFile_01.txt");
	}

	public static void loadGame() {
		for (int i = 0; i < loadFileLength; i++) {
			try {
				loadedFile[i] = reader.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < loadFileLength; i++) {
			statFile[i] = loadedFile[i].split("=");
			System.out.println(statFile[i][0] + " = " + statFile[i][1]);
		}
	}

	public static void saveGame() {
		try {
			writer.write("playerX=" + GameScreen.player.x);
			writer.write("playerY=" + GameScreen.player.y);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
