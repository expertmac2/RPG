package me.expertmac2.rpg.worldobj;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.actors.Actor;
import me.expertmac2.rpg.util.AnimationSet;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

public abstract class WorldObj implements Serializable {
	
	private final int id;
	private int currentAnim = 0, layerId = 0;
	private float x, y;
	private final boolean canCollide;
	private boolean isVisible;
	private Rectangle collisionRectangle;
	private AnimationSet animSet;
	
	public WorldObj(int i, AnimationSet anim, boolean collide, Rectangle collRect) {
		id = i;
		animSet = anim;
		canCollide = collide;
		collisionRectangle = collRect;
		layerId = 0;
	}
	
	public WorldObj(int i, int layer, AnimationSet anim, boolean collide, Rectangle collRect) {
		id = i;
		animSet = anim;
		canCollide = collide;
		collisionRectangle = collRect;
		layerId = layer;
	}
	
	public int getId() {
		return id;
	}
	
	public int getLayer() {
		return layerId;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float[] getPosition() {
		return (new float[] { x, y });
	}
	
	public boolean isVisible() {
		return isVisible();
	}
	
	public boolean canCollide() {
		return canCollide;
	}
	
	public Rectangle getCollisionRectangle() {
		return collisionRectangle;
	}
	
	public Animation getCurrentAnimation() {
		return animSet.getAnimation(currentAnim);
	}
	
	public void setLayer(int layer) {
		layerId = layer;
	}
	
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
	
	public void setCurrentAnimation(int i) {
		currentAnim = i;
	}
	
	public void setPosition(float newx, float newy) {
		x = newx;
		y = newy;
	}
	
	/**
	 * The game will call this method on the WorldObj to update it every tick.
	 * 
	 * @param game The current Game instance.
	 * @author expertmac2
	 */
	public abstract void update(Game game);
	
	/**
	 * If an Actor or another WorldObj touches this WorldObj, the game will call this method.
	 * You do not need to \@Override this method, if it isn't needed.
	 * 
	 * @param object The Actor or WorldObj that has touched this WorldObj.
	 * @author expertmac2
	 */
	public void onCollide(Object object) {}
	
}
