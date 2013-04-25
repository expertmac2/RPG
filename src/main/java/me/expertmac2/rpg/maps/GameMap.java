package me.expertmac2.rpg.maps;

import java.io.File;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import me.expertmac2.rpg.actors.Actor;
import me.expertmac2.rpg.events.Event;
import me.expertmac2.rpg.worldobj.*;

import org.apache.commons.lang.ArrayUtils;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class GameMap implements Serializable {
	
	private static final long serialVersionUID = 5181174981348551545L;
	private TiledMap tiledMap;
	private final String mapName;
	private HashMap<Integer, float[]> mapEntrances;
	public HashMap<Integer, Event> mapEvents;
	public HashMap<Integer, Actor> mapActors;
	public HashMap<Long, WorldObj> worldObjs;
	public ArrayList<Rectangle> mapCollRectangles;
	
	public GameMap(TiledMap tm, String name, HashMap<Integer, float[]> ent, ArrayList<Rectangle> al,
			HashMap<Integer, Event> events, HashMap<Integer, Actor> actors) {
		tiledMap = tm;
		mapName = name;
		mapEntrances = ent;
		mapCollRectangles = al;
		mapEvents = events;
		mapActors = actors;
	}
	
	public TiledMap getTiledMap() {
		return tiledMap;
	}
	
	public String getMapName() {
		return mapName;
	}
	
	public float[] getMapEntrance(int id) {
		return mapEntrances.get(id);
	}
	
	public boolean doesMapEntranceExist(int id) {
		return (mapEntrances.get(id) == null);
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		TiledMap tiledMapCopy = tiledMap;
		tiledMap = null;
		oos.defaultWriteObject();
		tiledMap = tiledMapCopy;
	}
	
	private void readObject(ObjectInputStream ois) throws Exception {
		ois.defaultReadObject();
		tiledMap = new TiledMap("res/maps/" + mapName + ".tmx");
	}

}
