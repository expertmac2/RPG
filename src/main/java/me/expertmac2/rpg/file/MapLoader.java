package me.expertmac2.rpg.file;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.maps.GameMap;
import me.expertmac2.rpg.maps.MapManager;
import me.expertmac2.rpg.util.AnimationSet;
import me.expertmac2.rpg.util.RPGException;
import me.expertmac2.rpg.actors.Actor;
import me.expertmac2.rpg.actors.Player;
import me.expertmac2.rpg.events.Event;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import de.schlichtherle.truezip.file.TFileInputStream;

public class MapLoader {

	private static SAXReader saxReader = new SAXReader();

	public static void loadMap(Game game, MapManager mapManager, String mapName) throws RPGException {

		try {
			Element cfg = saxReader.read(new TFileInputStream(game.getGameJarPath() + "/cfg/maps/" +
					mapName + ".xml")).getRootElement();
			HashMap<Integer, float[]> ent = new HashMap<Integer, float[]>();
			HashMap<Integer, Event> events = new HashMap<Integer, Event>();
			HashMap<Integer, Actor> actors = new HashMap<Integer, Actor>();
			ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
			boolean isDefaultMap = Boolean.parseBoolean(cfg.attributeValue("default"));
			GameMap defaultMap = null;
			if (!isDefaultMap) { defaultMap = (GameMap) mapManager.getMapInfo(mapManager.defaultMap); }

			for (Iterator i1 = cfg.elementIterator("entrances"); i1.hasNext();) {
				Element allEntrances = (Element) i1.next();

				for (Iterator i2 = allEntrances.elementIterator("entrance"); i2.hasNext();) {
					Element entrance = (Element) i2.next();
					String x = entrance.elementText("coordx"), y = entrance.elementText("coordy");
					float[] temp1 = { Float.parseFloat(x), Float.parseFloat(y) };
					ent.put(Integer.parseInt(entrance.attributeValue("id")), temp1);
				}

			}

			float[] playerSpawn = ent.get(0);

			if (isDefaultMap) {
				addActorToMap(actors, new Player(playerSpawn[0], playerSpawn[1]), mapManager.defaultMap, 
						true, true, game);
			} else {
				actors.put(0, defaultMap.mapActors.get(0));
			}

			for (Iterator i1 = cfg.elementIterator("actors"); i1.hasNext();) {
				Element allActors = (Element) i1.next();

				for (Iterator i2 = allActors.elementIterator("actor"); i2.hasNext();) {
					Element actor = (Element) i2.next();
					String x = actor.elementText("coordx"), y = actor.elementText("coordy");
					Constructor<?> constructor = game.getURLClassLoader().loadClass(actor.attributeValue("class"))
							.getDeclaredConstructor(float.class, float.class);
					constructor.setAccessible(true);
					Actor a = (Actor) constructor.newInstance(Float.parseFloat(x), Float.parseFloat(y));
					if (a instanceof Player) {
						throw new RPGException("Map configuration file " + mapName + " attempted to make a Player actor!");
					}
					addActorToMap(actors, a, mapName, Boolean.parseBoolean(actor.attributeValue("spawnNow")),
							Boolean.parseBoolean(actor.attributeValue("isVisible")), game);
				}

			}

			for (Iterator i1 = cfg.elementIterator("collision"); i1.hasNext();) {
				Element allEntrances = (Element) i1.next();

				for (Iterator i2 = allEntrances.elementIterator("rectangle"); i2.hasNext();) {
					Element rect = (Element) i2.next();
					String x = rect.attributeValue("coordx"), y = rect.attributeValue("coordy"),
							len = rect.attributeValue("length"), wid = rect.attributeValue("width");
					rectangles.add(new Rectangle(Float.parseFloat(x), Float.parseFloat(y),
							Float.parseFloat(len), Float.parseFloat(wid)));
				}

			}

			for (Iterator i1 = cfg.elementIterator("events"); i1.hasNext();) {
				Element allEvents = (Element) i1.next();

				for (Iterator i2 = allEvents.elementIterator("event"); i2.hasNext();) {
					Element event = (Element) i2.next();
					int eventId = Integer.parseInt(event.attributeValue("eventId"));
					Constructor<?> constructor = game.getURLClassLoader().loadClass(event.attributeValue("class"))
							.getDeclaredConstructor(int.class);
					constructor.setAccessible(true);
					events.put(eventId, (Event) constructor.newInstance(eventId));
				}

			}

			mapManager.addMap(mapName, new TiledMap("res/maps/" + mapName + ".tmx"), ent, rectangles, events,
					actors);

		} catch (Exception e) {
			throw new RPGException(e);
		}


	}

	public static void addActorToMap(HashMap<Integer, Actor> actors, Actor a, String mapName, 
			boolean spawnNow, boolean shouldBeVisible, Game game) throws RPGException {
		int id = (a instanceof Player) ? 0 : actors.size();
		a.initializeActor(game.getMapManager());
		a.setMap(mapName);
		a.setId(id);
		a.setAnimations(AnimationHandler.buildActorAnimationSet(game, a.getClass().getCanonicalName()));
		a.actorSpawn = spawnNow;
		actors.put(id, a);
	}

}
