package me.expertmac2.rpg.events;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.actors.Actor;
import me.expertmac2.rpg.logic.Task;
import me.expertmac2.rpg.maps.GameMap;
import me.expertmac2.rpg.util.Resources.ActorState;

public class CutsceneUtils {
	
	public static Event getEvent(Game game, String mapName, final int eventId) {
		GameMap map = (GameMap) game.getMapManager().getLoadedMaps().get(mapName);
		return map.mapEvents.get(eventId);
	}
	
	public static Event getEvent(Game game, final int eventId) {
		return game.getMapManager().getCurrentMap().mapEvents.get(eventId);
	}
	
	public static void revertToASIdle(Game game, Task task, final int actorId) {
		game.getScheduler().scheduleTask(new Task("setActorState", "gameEvent", task) {
			@Override
			public void executeTaskActions(Game game) {
				game.getMapManager().getActor(actorId).updateStatus(ActorState.IDLE);
				isDone = true;
			}
		});
	}
	
	public static void revertToASIdle(Game game, final long timeout, final int actorId) {
		game.getScheduler().scheduleTask(new Task("setActorState", "gameEvent", timeout) {
			@Override
			public void executeTaskActions(Game game) {
				game.getMapManager().getActor(actorId).updateStatus(ActorState.IDLE);
				isDone = true;
			}
		});
	}
	
	// right: 1   left: 2   down: 3   up: 4
	// up-left: 5   up-right: 6   down-left: 7   down-right: 8
	
	public static Task moveActorToX(Game game, final int id, final float x, String schedBy) {
		Task task = null;
		final Actor actor = game.getMapManager().getActor(id);
		if (actor.getX() < x) {
			task = new Task("moveActorToX", schedBy) {
				private float distanceBetween = x - actor.getX();
				private int actorId = id;
				@Override
				public void executeTaskActions(Game game) {
					if (distanceBetween != 0) {
						distanceBetween = distanceBetween - 1;
						game.getMapManager().getActor(actorId).movement(1, game);
						isDone = false;
					} else {
						isDone = true;
					}
				}
			};
			game.getScheduler().scheduleTask(task);
		} else if (actor.getX() > x) {
			task = new Task("moveActorToX", schedBy) {
				private float distanceBetween = actor.getX() - x;
				private int actorId = id;
				@Override
				public void executeTaskActions(Game game) {
					if (distanceBetween != 0) {
						distanceBetween = distanceBetween - 1;
						game.getMapManager().getActor(actorId).movement(2, game);
						isDone = false;
					} else {
						isDone = true;
					}
				}
			};
			game.getScheduler().scheduleTask(task);
		}
		return task;
	}
	
	public static Task moveActorToX(Game game, final int id, final float x, String schedBy, final Task executeAfter) {
		Task task = null;
		final Actor actor = game.getMapManager().getActor(id);
		if (actor.getX() < x) {
			task = new Task("moveActorToX", schedBy, executeAfter) {
				private float distanceBetween = x - actor.getX();
				private int actorId = id;
				@Override
				public void executeTaskActions(Game game) {
					if (distanceBetween != 0) {
						distanceBetween = distanceBetween - 1;
						game.getMapManager().getActor(actorId).movement(1, game);
						isDone = false;
					} else {
						isDone = true;
					}
				}
			};
			game.getScheduler().scheduleTask(task);
		} else if (actor.getX() > x) {
			task = new Task("moveActorToX", schedBy, executeAfter) {
				private float distanceBetween = actor.getX() - x;
				private int actorId = id;
				@Override
				public void executeTaskActions(Game game) {
					if (distanceBetween != 0) {
						distanceBetween = distanceBetween - 1;
						game.getMapManager().getActor(actorId).movement(2, game);
						isDone = false;
					} else {
						isDone = true;
					}
				}
			};
			game.getScheduler().scheduleTask(task);
		}
		return task;
	}
	
	public static Task moveActorToY(Game game, final int id, final float y, String schedBy) {
		Task task = null;
		final Actor actor = game.getMapManager().getActor(id);
		if (actor.getY() < y) {
			task = new Task("moveActorToY", schedBy) {
				private float distanceBetween = y - actor.getY();
				private int actorId = id;
				@Override
				public void executeTaskActions(Game game) {
					if (distanceBetween != 0) {
						distanceBetween = distanceBetween - 1;
						game.getMapManager().getActor(actorId).movement(3, game);
						isDone = false;
					} else {
						isDone = true;
					}
				}
			};
			game.getScheduler().scheduleTask(task);
		} else if (actor.getY() > y) {
			task = new Task("moveActorToY", schedBy) {
				private float distanceBetween = actor.getY() - y;
				private int actorId = id;
				@Override
				public void executeTaskActions(Game game) {
					if (distanceBetween != 0) {
						distanceBetween = distanceBetween - 1;
						game.getMapManager().getActor(actorId).movement(4, game);
						isDone = false;
					} else {
						isDone = true;
					}
				}
			};
			game.getScheduler().scheduleTask(task);
		}
		return task;
	}
	
	public static Task moveActorToY(Game game, final int id, final float y, String schedBy, Task executeAfter) {
		Task task = null;
		final Actor actor = game.getMapManager().getActor(id);
		if (actor.getY() < y) {
			task = new Task("moveActorToY", schedBy, executeAfter) {
				private float distanceBetween = y - actor.getY();
				private int actorId = id;
				@Override
				public void executeTaskActions(Game game) {
					if (distanceBetween != 0) {
						distanceBetween = distanceBetween - 1;
						game.getMapManager().getActor(actorId).movement(3, game);
						isDone = false;
					} else {
						isDone = true;
					}
				}
			};
			game.getScheduler().scheduleTask(task);
		} else if (actor.getY() > y) {
			task = new Task("moveActorToY", schedBy, executeAfter) {
				private float distanceBetween = actor.getY() - y;
				private int actorId = id;
				@Override
				public void executeTaskActions(Game game) {
					if (distanceBetween != 0) {
						distanceBetween = distanceBetween - 1;
						game.getMapManager().getActor(actorId).movement(4, game);
						isDone = false;
					} else {
						isDone = true;
					}
				}
			};
			game.getScheduler().scheduleTask(task);
		}
		return task;
	}

}
