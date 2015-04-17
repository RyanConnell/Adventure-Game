package com.Rhinodude.AdventureGame;

import javax.swing.JFrame;

public class Frame {
	public static JFrame frame;
	static Display display;
	public static int fWidth = 120*7, fHeight = 80*7;
	public static String fTitle = "Adventure Game - Alpha 0.4";
	
	public static void main(String[] args){
		frame = new JFrame();
		display = new Display();
		
		frame.add(display);
		frame.pack();
		frame.setSize(fWidth+6, fHeight+28);
		frame.setTitle(fTitle);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		display.init(args);
	}
}
