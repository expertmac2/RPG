package me.expertmac2.rpg.worldobj;

import org.newdawn.slick.geom.Rectangle;

import me.expertmac2.rpg.actors.Actor;
import me.expertmac2.rpg.util.AnimationSet;

public abstract class DynamicWorldObj extends WorldObj {
	
	private boolean isInteractable;

	public DynamicWorldObj(int i, AnimationSet anim, boolean canCollide, Rectangle collRect) {
		super(i, anim, canCollide, collRect);
		isInteractable = false;
	}
	
	public DynamicWorldObj(int i, AnimationSet anim, boolean canCollide, Rectangle collRect,
			boolean interactable) {
		super(i, anim, canCollide, collRect);
		isInteractable = interactable;
	}
	
	public boolean isInteractable() {
		return isInteractable;
	}
	
	public void setInteractable(boolean interactable) {
		isInteractable = interactable;
	}
	
	/**
	 * This method is called by the engine when an actor interacts with this object.
	 * 
	 * @param actor The actor that has interacted with this DynamicWorldObj.
	 */
	public abstract void onInteract(Actor actor);

}
