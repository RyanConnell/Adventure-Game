package com.Rhinodude.AdventureGame.Tiles;

import java.util.ArrayList;

import com.Rhinodude.AdventureGame.Display;
import com.Rhinodude.AdventureGame.ImageLoader;
import com.Rhinodude.AdventureGame.LevelLoader;
import com.Rhinodude.AdventureGame.Entities.CorruptedHuman;
import com.Rhinodude.AdventureGame.Entities.Entity;

public class BattleTile extends Tile {

	ArrayList<Entity> party = new ArrayList<Entity>();

	public BattleTile(int x, int y) {
		super(ImageLoader.battleStarter, x, y, true);
		createParty();
		this.tileType = "BattleTile";
	}

	public void createParty() {
		party.add(new CorruptedHuman(0, 0));
		party.add(new CorruptedHuman(0, 0));
	}

	public void checkCollision(Entity entity) {
		if (entity.collisionRect.intersects(collisionRect)) {
			if (isSolid) {
				destroy();
				Display.screen.enterBattleScreen(party);
			}
		}
	}
	
	public void destroy(){
		LevelLoader.tileMap[x/Display.scale][y/Display.scale] = null;
	}

}
