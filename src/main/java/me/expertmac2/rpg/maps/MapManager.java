package me.expertmac2.rpg.maps;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.geom.Rectangle;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.actors.*;
import me.expertmac2.rpg.events.Event;
import me.expertmac2.rpg.file.AnimationHandler;
import me.expertmac2.rpg.logic.Collision;
import me.expertmac2.rpg.util.AnimationSet;
import me.expertmac2.rpg.util.RPGException;

public class MapManager {

	private BidiMap maps = new DualHashBidiMap();
	private GameMap currentMap = null;
	private Collision coll = null;
	public String defaultMap = "##engine-NOTHING";
	
	public MapManager() {
		coll = new Collision();
	}

	public void addMap(String name, TiledMap tm, HashMap<Integer, float[]> hm, ArrayList<Rectangle> al,
			HashMap<Integer, Event> events, HashMap<Integer, Actor> actors) {
		maps.put(name, new GameMap(tm, name, hm, al, events, actors));
		if (defaultMap.equals("##engine-NOTHING")) defaultMap = name;
	}

	public void setCurrentMap(Game game, String newMap, int entranceId) { 
		game.shouldRenderGame = false;
		currentMap = (GameMap) maps.get(newMap);
		float[] entranceCoords = currentMap.getMapEntrance(entranceId);
		game.getCamera().changeMap(this.getCurrentMap().getTiledMap());
		getActor(0).setMap(newMap);
		getActor(0).setPosition(entranceCoords[0], entranceCoords[1]);
		game.shouldRenderGame = true;
	}
	
	public void setCurrentMap(Game game, String newMap) {
		game.shouldRenderGame = false;
		currentMap = (GameMap) maps.get(newMap);
		game.getCamera().changeMap(this.getCurrentMap().getTiledMap());
		game.shouldRenderGame = true;
	}

	public TiledMap getTiledMap(String mapName) {
		GameMap gm = (GameMap) maps.get(mapName);
		return gm.getTiledMap();
	}

	public void addActorToWorld(Actor a, String mapName, 
			boolean spawnNow, boolean shouldBeVisible, Game game) throws RPGException {
		a.initializeActor(this);
		a.setMap(mapName);
		a.setId((a instanceof Player) ? 0 : getMapInfo(mapName).mapActors.size());
		a.setAnimations(AnimationHandler.buildActorAnimationSet(game, a.getClass().getCanonicalName()));
		a.actorSpawn = spawnNow;
		GameMap map = (GameMap) maps.get(mapName);
		map.mapActors.put((a instanceof Player) ? 0 : currentMap.mapActors.size(), a);

		System.out.println("addActorToWorld: " + a.toString() + " on map " + mapName + " with id " + ((a instanceof Player) ? 0 : 
			currentMap.mapActors.size()));
	}

	public void spawnActor(Game game, int actorId, boolean shouldBeVisible) {
		if (!currentMap.mapActors.containsKey(actorId) || 
				currentMap.mapActors.get(actorId) instanceof Player || 
				currentMap.mapActors.get(actorId).actorSpawn) {
			return;
		}
		currentMap.mapActors.get(actorId).spawnActor(this, shouldBeVisible);
	}

	public GameMap getMapInfo(String mapName) {
		return (GameMap) maps.get(mapName);
	}

	public GameMap getCurrentMap() {
		return currentMap;
	}

	public String getCurrentMapName() {
		return (String) maps.getKey(currentMap);
	}

	public Actor getActor(int id) {
		return currentMap.mapActors.get(id);
	}
	
	public Actor getActor(String mapName, int id) {
		return ((GameMap) maps.get(mapName)).mapActors.get(id);
	}

	@Deprecated
	public Rectangle getActorCollRectangle(int id) {
		return getActor(id).collRectangle;
	}

	public ArrayList<Rectangle> getMapCollRectangles() {
		return currentMap.mapCollRectangles;
	}

	public int getActorMapSize() {
		return currentMap.mapActors.size();
	}

	public Collision getCollision() {
		return this.coll;
	}
	
	public DualHashBidiMap getLoadedMaps() {
		return (DualHashBidiMap) maps;
	}
	
	public void setLoadedMaps(DualHashBidiMap newMaps) {
		maps = newMaps;
	}

}
