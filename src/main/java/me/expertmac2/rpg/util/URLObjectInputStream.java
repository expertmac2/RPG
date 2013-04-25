package me.expertmac2.rpg.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.net.URLClassLoader;

import me.expertmac2.rpg.Game;

public class URLObjectInputStream extends ObjectInputStream {
	
	private final ClassLoader classLoader;

	protected URLObjectInputStream() throws IOException, SecurityException {
		super();
		classLoader = Class.class.getClassLoader();
	}
	
	protected URLObjectInputStream(InputStream in) throws IOException, SecurityException {
		super(in);
		classLoader = Class.class.getClassLoader();
	}
	
	public URLObjectInputStream(Game game) throws IOException, SecurityException {
		classLoader = game.getURLClassLoader();
	}
	
	public URLObjectInputStream(InputStream in, Game game) throws IOException, SecurityException {
		super(in);
		classLoader = game.getURLClassLoader();
	}
	
    protected Class<?> resolveClass(ObjectStreamClass desc) throws ClassNotFoundException {
        return Class.forName(desc.getName(), false, classLoader);
    }

}
