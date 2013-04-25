package me.expertmac2.rpg.actors;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Animation;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.maps.MapManager;
import me.expertmac2.rpg.util.Resources.ActorState;

public class NPC extends Actor {

	public NPC(float x, float y) {
		super(x, y);
	}

	@Override
	public void spawnActor(MapManager mapManager, boolean isVisible) {
		this.actorIsVisible = isVisible;
		this.actorSpawn = false;
		this.actorAlreadySpawned = true;
		//
	}

	@Override
	public void update(MapManager mapManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initializeActor(MapManager mapManager) {
		currentAnimId = 0;
		collRectangle = new Rectangle(this.x, this.y, 32, 34);
	}

}
