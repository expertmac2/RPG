package me.expertmac2.rpg.logic;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.maps.MapManager;
import me.expertmac2.rpg.util.Resources.Direction;

public class Collision {

	public boolean isColliding(MapManager mapManager, int actorId) {
		Rectangle rect = mapManager.getActorCollRectangle(actorId);
		for (int i=0; i <= (mapManager.getActorMapSize() - 1); i++) {
			Rectangle actorRect = mapManager.getActorCollRectangle(i);
			if (actorRect.intersects(rect)) {
				return true;
			}	
		}
		for (Rectangle mapRect : mapManager.getMapCollRectangles()) {
			if (mapRect.intersects(rect)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Direction> collidingInWhatDirection(MapManager mapManager, int actorId) {
		ArrayList<Direction> collDirections = new ArrayList<Direction>();
		Rectangle temp1, temp2, temp3, temp4, actorCheckRect;
		float[] actorCoords = mapManager.getActor(actorId).getPosition(); // x, y
		temp1 = new Rectangle(actorCoords[0], actorCoords[1] - 4, 32, 3); // north - blue
		temp2 = new Rectangle(actorCoords[0], actorCoords[1] + 34, 32, 3); // south - green
		temp3 = new Rectangle(actorCoords[0] + 32, actorCoords[1], 3, 32); // east - orange
		temp4 = new Rectangle(actorCoords[0] - 3, actorCoords[1], 3, 32); // west - red
		for (int i=0; i <= (mapManager.getActorMapSize() - 1); i++) {
			if (i != -1 && i != actorId) {
				actorCheckRect = mapManager.getActorCollRectangle(i);
				if (temp1.intersects(actorCheckRect)) {
					collDirections.add(Direction.NORTH);
				} if (temp2.intersects(actorCheckRect)) {
					collDirections.add(Direction.SOUTH);
				} if (temp3.intersects(actorCheckRect)) {
					collDirections.add(Direction.EAST);
				} if (temp4.intersects(actorCheckRect)) {
					collDirections.add(Direction.WEST);
				}
			}
		}
		for (Rectangle mapRect : mapManager.getMapCollRectangles()) {
			if (temp1.intersects(mapRect)) {
				collDirections.add(Direction.NORTH);
			} if (temp2.intersects(mapRect)) {
				collDirections.add(Direction.SOUTH);
			} if (temp3.intersects(mapRect)) {
				collDirections.add(Direction.EAST);
			} if (temp4.intersects(mapRect)) {
				collDirections.add(Direction.WEST);
			}
		}
		return collDirections;
	}
	
	public boolean isCollidingInSpecificDirection(MapManager mapManager, int actorId, Direction direction) {
		Rectangle temp = null;
		float[] actorCoords = mapManager.getActor(actorId).getPosition(); // x, y
		if (direction == Direction.NORTH) {
			temp = new Rectangle(actorCoords[0], actorCoords[1] - 4, 32, 3);
		} else if (direction == Direction.SOUTH) {
			temp = new Rectangle(actorCoords[0], actorCoords[1] + 34, 32, 3);
		} else if (direction == Direction.EAST) {
			temp = new Rectangle(actorCoords[0] + 32, actorCoords[1], 3, 32);
		} else if (direction == Direction.WEST) {
			temp = new Rectangle(actorCoords[0] - 3, actorCoords[1], 3, 32);
		} else {
			return false;
		}
		return temp.intersects(mapManager.getActorCollRectangle(actorId));
	}
	
}
