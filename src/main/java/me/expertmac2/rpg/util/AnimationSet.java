package me.expertmac2.rpg.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.newdawn.slick.Animation;

/**
 *
 * AnimationSet is a utility class that makes a 
 * set of any number of Animation instances.
 * 
 * @author expertmac2
 *
 */
public class AnimationSet implements Serializable {

	private DualKeyMap<Integer, String, Animation> animSet;

	/**
	 * This constructor is to be used if you have an existing
	 * set of Animation instances, their names, and their IDs.
	 * 
	 * @param existingSet An existing set of Animation instances.
	 * @see org.newdawn.slick.Animation
	 */
	public AnimationSet(DualKeyMap<Integer, String, Animation> existingSet) {
		animSet = existingSet;
	}

	/**
	 * This constructor is to be used if you just want to create
	 * a blank set of Animation instances.
	 * 
	 * @see org.newdawn.slick.Animation
	 */
	public AnimationSet() {
		animSet = new DualKeyMap<Integer, String, Animation>();
	}

	/**
	 * Adds an animation to the set.
	 * 
	 * @param i The animation ID that should be given.
	 * @param animName The name of the animation.
	 * @param anim The Animation instance to add.
	 * @see org.newdawn.slick.Animation
	 */
	public void addAnimation(int i, String animName, Animation anim) {
		animSet.put(i, animName, anim);
	}

	/**
	 * Gets an Animation from the set.
	 * 
	 * @param i The animation ID.
	 * @return The Animation instance, or null if the id is invalid.
	 * @see org.newdawn.slick.Animation
	 */
	public Animation getAnimation(int i) {
		return animSet.get(i);
	}

	/**
	 * Gets an Animation from the set.
	 * 
	 * @param name The animation name.
	 * @return The Animation instance, or null if the name is invalid.
	 * @see org.newdawn.slick.Animation
	 */
	public Animation getAnimation(String name) {
		return animSet.get(name);
	}
	
	private void readObject(ObjectInputStream ois) throws Exception {
		ois.defaultReadObject();
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		DualKeyMap<Integer, String, Animation> mapCopy = animSet;
		animSet = null;
		oos.defaultWriteObject();
		animSet = mapCopy;
	}


}
