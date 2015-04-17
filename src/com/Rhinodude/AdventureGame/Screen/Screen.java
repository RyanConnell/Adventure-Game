package com.Rhinodude.AdventureGame.Screen;

import java.util.ArrayList;

import com.Rhinodude.AdventureGame.Entities.Entity;

public class Screen {
	public int screenType;
	//1 = GameScreen
	
	public Screen (int screenType){
		this.screenType = screenType;
		init();
	}
	
	public void init(){
		
	}
	
	public void render(){
		
	}
	
	public void update(){
		
	}
	
	public int getScreenType(){
		return screenType;
	}

	public void enterBattleScreen(ArrayList<Entity> battlePartyTest) {
		
	}
	
}
