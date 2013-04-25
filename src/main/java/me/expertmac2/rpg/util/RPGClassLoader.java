package me.expertmac2.rpg.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class RPGClassLoader extends URLClassLoader {

	public RPGClassLoader(URL[] urls) {
		super(urls);
		// TODO Auto-generated constructor stub
	}

	/**
	 * add the Jar file to the classpath
	 * 
	 * @param path
	 * @throws MalformedURLException
	 */
	public void addFile(String path) throws MalformedURLException {
		// construct the jar url path
		String urlPath = "jar:file:" + path + "!/";

		// invoke the base method
		addURL(new URL(urlPath));
	}

	/**
	 * add the Jar file to the classpath
	 * 
	 * @param path
	 * @throws MalformedURLException
	 */
	public void addFile(String paths[]) throws MalformedURLException {
		if (paths != null)
			for (int i = 0; i < paths.length; i++)
				addFile(paths[i]);
	}


}