package me.expertmac2.rpg.actors;

import java.io.Serializable;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.maps.MapManager;
import me.expertmac2.rpg.util.AnimationSet;
import me.expertmac2.rpg.util.Resources.ActorState;

public abstract class Actor implements Serializable {
	
	protected float x = 0;
	protected float y = 0;
	protected int actorId = 0;
	protected int layerId = 0;
	private ActorState actorState = ActorState.NULL;
	private AnimationSet anim = null;
	public boolean actorSpawn = false;
	public boolean actorAlreadySpawned = false;
	public boolean actorIsVisible = false;
	private String map;
	public Rectangle collRectangle = null;
	protected int currentAnimId = -9999;
	
	// rpg elements
	protected int health = -1000;
	protected int maxHealth = 100;
	protected int level = 0;
	protected long experience = 0;
	
	
	public Actor(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float[] getPosition() {
		return new float[] {x, y};
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public int getId() {
		return actorId;
	}
	
	public int getLayer() {
		return layerId;
	}
	
	// You should override this. Just saying.
	public void movement(int mode, Game game) {
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
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void updateStatus(ActorState as) {
		this.setActorState(as);
	}
	
	public ActorState getState() {
		return getActorState();
	}
	
	public void setAnimations(AnimationSet anim) {
		this.anim = anim;
	}
	
	public AnimationSet getAnimations() {
		return anim;
	}
	
	public Animation getCurrentAnimation() {
		return anim.getAnimation(currentAnimId);
	}
	
	public void setCurrentAnimation(int animId) {
		currentAnimId = animId;
	}
	
	public void setId(int id) {
		this.actorId = id;
	}
	
	public void setLayer(int layer) {
		layerId = layer;
	}
	
	public boolean isActorVisible() {
		return actorIsVisible;
	}
	
	public void setMap(String mapName) {
		map = mapName;
	}
	
	public String getMap() {
		return map;
	}
	
	public ActorState getActorState() {
		return actorState;
	}

	public void setActorState(ActorState actorState) {
		this.actorState = actorState;
	}
	
	public synchronized void setHealth(int health) {
		this.health = health;
	}
	
	public synchronized void setMaxHealth(int health) {
		this.maxHealth = health;
	}
	
	public synchronized void setExperience(long exp) {
		this.experience = exp;
	}
	
	public synchronized void setLevel(int level) {
		this.level = level;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getLevel() {
		return level;
	}
	
	public long getExperience() {
		return experience;
	}
	
	public abstract void initializeActor(MapManager mapManager);
	public abstract void spawnActor(MapManager mapManager, boolean isVisible);
	public abstract void update(MapManager mapManager);
	
}
