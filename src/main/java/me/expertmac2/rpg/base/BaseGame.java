package me.expertmac2.rpg.base;

import java.util.HashMap;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.file.MapLoader;
import me.expertmac2.rpg.util.RPGException;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class BaseGame {
	
	public final String fullName;
	public final String author;
	private final String[] mapsToLoad;
	private HashMap<String, Boolean> gameVariables;
	private final AngelCodeFont gameFont;
	
	public BaseGame(String name, String auth, HashMap<String, Boolean> vars, String[] maps, 
			AngelCodeFont font) {
		fullName = name;
		author = auth;
		mapsToLoad = maps;
		gameVariables = vars;
		gameFont = font;
	}
	
	
	public void loadMaps(Game game) throws RPGException {
		for (int i=0; i < mapsToLoad.length; i++) {
			MapLoader.loadMap(game, game.getMapManager(), mapsToLoad[i]);
		}
	}
	
	public AngelCodeFont getFont() {
		return gameFont;
	}
	
	public abstract void init(Game game, GameContainer container);
	public abstract void update(Game game, GameContainer container, int delta);
	public abstract void renderWorld(Game game, GameContainer container, Graphics g);// int layerId);
	public abstract void renderHUD(Game game, GameContainer container, Graphics g);

}
