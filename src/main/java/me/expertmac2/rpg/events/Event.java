package me.expertmac2.rpg.events;

import java.io.Serializable;

import me.expertmac2.rpg.Game;

public abstract class Event implements Serializable {
	
	private static final long serialVersionUID = -1351140563982498439L;
	protected boolean hasFired = false;
	protected boolean fireNow = false;
	private int eventId = -1;
	
	public Event(int id) {
		eventId = id;
	}
	
	public void resetEvent(Game game) {
		fireNow = false;
		hasFired = false;
		resetEventActions(game);
	}
	
	public final int getEventId() {
		return eventId;
	}
	
	public final void fireNow() {
		fireNow = true;
	}
	
	public final boolean shouldFireNow() {
		return fireNow;
	}
	
	public final void fireEvent(Game game) {
		if (hasFired) return;
		fireEventActions(game);
	}
	
	protected abstract void fireEventActions(Game game);
	protected abstract void resetEventActions(Game game);
	
}
