package me.expertmac2.rpg.actors;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Animation;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.logic.Collision;
import me.expertmac2.rpg.maps.MapManager;
import me.expertmac2.rpg.util.Resources.ActorState;
import me.expertmac2.rpg.util.Resources.Direction;

public class Player extends Actor {

	public Player(float x, float y) {
		super(x, y);
	}

	@Override
	public void movement(int mode, Game game) {
		// right: 1   left: 2   down: 3   up: 4
		// up-left: 5   up-right: 6   down-left: 7   down-right: 8
		if (game.getMapManager().getCollision().isColliding(game.getMapManager(), this.getId()) 
				&& !game.getVariableValue("noclip")) {
			ArrayList<Direction> collDirections = 
					game.getMapManager().getCollision().collidingInWhatDirection(
							game.getMapManager(), this.getId());
			if (mode == 2) {
				if (collDirections.contains(Direction.WEST)) { return; }
			} else if (mode == 1) {
				if (collDirections.contains(Direction.EAST)) { return; }
			} else if (mode == 3) {
				if (collDirections.contains(Direction.SOUTH)) { return; }
			} else if (mode == 4) {
				if (collDirections.contains(Direction.NORTH)) { return; }
			}
		}
		if (mode == 2) {
			x = (float) (x - 1);
		} else if (mode == 1) {
			x = (float) (x + 1);
		} else if (mode == 3) {
			y = (float) (y + 1);
		} else if (mode == 4) {
			y = (float) (y - 1);
		}
	}

	@Override
	public void spawnActor(MapManager mapManager, boolean isVisible) {
		this.actorIsVisible = isVisible;
		this.actorSpawn = false;
		this.actorAlreadySpawned = true;
	}

	@Override
	public void update(MapManager mapManager) {
		mapManager.getActorCollRectangle(this.getId()).setLocation(this.x, this.y);
	}

	@Override
	public void initializeActor(MapManager mapManager) {
		currentAnimId = 0;
		collRectangle = new Rectangle(this.x, this.y, 32, 34);
	}
	
}
