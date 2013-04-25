package me.expertmac2.rpg.logic;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.events.Event;
import me.expertmac2.rpg.maps.MapManager;

public class LogicThread extends Thread {

	private int delta;
	private Game game;

	public LogicThread(Game g, int i) {
		game = g;
		delta = i;
	}

	@Override
	public void run() {
		while (game.shouldLogicBeCalculated) {
			try {
				sleep(delta);
			} catch (InterruptedException e) {
				System.out.println("!! SEVERE: The LogicThread has been interrupted! Collision, events, and tasks won't work!");
				return;
			}
			MapManager mapManager = game.getMapManager();
			for (int i=0; i <= (mapManager.getActorMapSize() - 1); i++) { // spawn and update actors
				if (i != -1 && mapManager.getActor(i).actorSpawn && !mapManager.getActor(i).actorAlreadySpawned) {
					mapManager.getActor(i).spawnActor(mapManager, true); // spawn any actors
				}
				mapManager.getActor(i).update(mapManager);
			}
			for (int i=0; i <= (mapManager.getCurrentMap().mapEvents.size() - 1); i++) { // fire events
				if (i != -1 && mapManager.getCurrentMap().mapEvents.get(i).shouldFireNow()) {
					mapManager.getCurrentMap().mapEvents.get(i).fireEvent(game);
				}
			}
			game.getScheduler().updateTasks();
			delta = game.delta;
		}
		System.out.println("!! SEVERE: The LogicThread has been stopped! Collision, events, and tasks won't work!");
	}

}
