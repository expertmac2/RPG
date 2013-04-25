package me.expertmac2.rpg.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.actors.Actor;
import me.expertmac2.rpg.util.AnimationSet;
import me.expertmac2.rpg.util.RPGException;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import de.schlichtherle.truezip.file.TFile;
import de.schlichtherle.truezip.file.TFileInputStream;
import de.schlichtherle.truezip.file.TFileReader;

public class AnimationHandler {

	public static SAXReader saxReader = new SAXReader();

	public static AnimationSet buildActorAnimationSet(Game game, String actorPackage) 
			throws RPGException {
		try {
			AnimationSet animSet = new AnimationSet();
			TFile animFile = new TFile(game.getGameJarPath() + "/cfg/AnimationInfo.xml");
			Element animInfo = saxReader.read(new TFileReader(animFile)).getRootElement();

			for (Iterator i1 = animInfo.elementIterator("actors"); i1.hasNext();) {
				Element actors = (Element) i1.next();

				for (Iterator i2 = actors.elementIterator("actor"); i2.hasNext();) {
					Element actorInfo = (Element) i2.next();
					String definedClass = actorInfo.attributeValue("class");
					Class loadedClass = game.getURLClassLoader().loadClass(definedClass);
					String definedPackage = loadedClass.getCanonicalName();

					if (actorPackage.equals(definedPackage)) {
						int tw = Integer.parseInt(actorInfo.attributeValue("texturewidth")),
								th = Integer.parseInt(actorInfo.attributeValue("textureheight"));
						SpriteSheet sheet = new SpriteSheet("sheet_" + definedPackage,
								new TFileInputStream(game.getGameJarPath() + "/" + 
										actorInfo.attributeValue("sheet")), tw, th);
						for (Iterator i3 = actorInfo.elementIterator("animation"); i3.hasNext();) {
							Element animation = (Element) i3.next();
							Animation anim = new Animation();
							int animationId = Integer.parseInt(animation.attributeValue("id"));
							String animationName = animation.attributeValue("name");
							for (Iterator i4 = animation.elementIterator("frame"); i4.hasNext();) {
								Element frame = (Element) i4.next();
								int x = Integer.parseInt(frame.attributeValue("x")),
										y = Integer.parseInt(frame.attributeValue("y")),
										duration = Integer.parseInt(frame.attributeValue("duration"));
								anim.addFrame(sheet.getSprite(x, y), duration);
							}
							animSet.addAnimation(animationId, animationName, anim);
						}

						return animSet;
					}
				}
			}

			return null;
		} catch (Exception e) {
			throw new RPGException(e);
		}
	}
	
	public static AnimationSet buildWorldObjAnimationSet(Game game, String actorPackage) 
			throws RPGException {
		try {
			AnimationSet animSet = new AnimationSet();
			TFile animFile = new TFile(game.getGameJarPath() + "/cfg/AnimationInfo.xml");
			Element animInfo = saxReader.read(new TFileReader(animFile)).getRootElement();

			for (Iterator i1 = animInfo.elementIterator("worldobjs"); i1.hasNext();) {
				Element actors = (Element) i1.next();

				for (Iterator i2 = actors.elementIterator("worldobj"); i2.hasNext();) {
					Element actorInfo = (Element) i2.next();
					String definedClass = actorInfo.attributeValue("class");
					Class loadedClass = game.getURLClassLoader().loadClass(definedClass);
					String definedPackage = loadedClass.getCanonicalName();

					if (actorPackage.equals(definedPackage)) {
						int tw = Integer.parseInt(actorInfo.attributeValue("texturewidth")),
								th = Integer.parseInt(actorInfo.attributeValue("textureheight"));
						SpriteSheet sheet = new SpriteSheet("sheet_" + definedPackage,
								new TFileInputStream(game.getGameJarPath() + "/" + 
										actorInfo.attributeValue("sheet")), tw, th);
						for (Iterator i3 = actorInfo.elementIterator("animation"); i3.hasNext();) {
							Element animation = (Element) i3.next();
							Animation anim = new Animation();
							int animationId = Integer.parseInt(animation.attributeValue("id"));
							String animationName = animation.attributeValue("name");
							for (Iterator i4 = animation.elementIterator("frame"); i4.hasNext();) {
								Element frame = (Element) i4.next();
								int x = Integer.parseInt(frame.attributeValue("x")),
										y = Integer.parseInt(frame.attributeValue("y")),
										duration = Integer.parseInt(frame.attributeValue("duration"));
								anim.addFrame(sheet.getSprite(x, y), duration);
							}
							animSet.addAnimation(animationId, animationName, anim);
						}

						return animSet;
					}
				}
			}

			return null;
		} catch (Exception e) {
			throw new RPGException(e);
		}
	}

}
