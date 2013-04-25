package me.expertmac2.rpg.maps;

import net.java.games.input.Mouse;
import me.expertmac2.rpg.Game;

public class MapEditor {
	
	private final Game game;
	private boolean mouseDown = false, isInSelection = false;
	private float worldX, worldY, screenX, screenY;
	
	
	public MapEditor(Game g) {
		game = g;
	}
	
	public void inputUpdate(boolean up, boolean down, float x, float y) {
		mouseDown = down;
		if (mouseDown && !isInSelection) {
			float[] world = screenToWorld(x, y);
			worldX = world[0];
			worldY = world[1];
			
			
			
			isInSelection = true;
		}
	}
	
	private float[] worldToScreen(float x, float y) {
		return new float[] { game.getCamera().getX() - x, game.gameContainer.getScreenHeight() + y };
	}
	
	private float[] screenToWorld(float x, float y) {
		return new float[] { x + game.getCamera().getX(), game.gameContainer.getScreenHeight() - y };
	}

}
