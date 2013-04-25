package me.expertmac2.rpg;

import java.io.File;

import me.expertmac2.rpg.util.RPGClassLoader;
import me.expertmac2.rpg.util.RPGException;
import me.expertmac2.rpg.util.Resources;
import me.expertmac2.rpg.util.Resources.Direction;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import de.schlichtherle.truezip.file.TArchiveDetector;
import de.schlichtherle.truezip.file.TConfig;
import de.schlichtherle.truezip.fs.archive.zip.ZipDriver;
import de.schlichtherle.truezip.socket.sl.IOPoolLocator;

public class Initializer {

	public static void main(String[] args) throws SlickException, RPGException {
		
		String game = parseArgs(args, "game");
		
		if (game.equals("")) {
			throw new RPGException("Required parameter \"-game (name of game)\" was not defined.");
		}
		
		System.out.println("XpertRPG Engine");
		System.out.println("Do not redistribute!");
		System.out.println("-------------------");
		System.out.println("Using game: " + game);
		System.out.println("");
		System.out.println("Running from directory " + System.getProperty("user.dir"));
		System.out.println("");

		switch (Resources.getOSVersion()) {
			case Linux:
				System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/linux/");
				break;
			case MacOSX:
				System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/macosx/");
				break;
			case Solaris:
				System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/solaris/");
				break;
			case Windows:
				System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/native/windows/");
				break;
			case Unknown:
				System.out.println("!! Couldn't identify your Operating System.");
				System.exit(1);
		}
		
		TConfig.get().setArchiveDetector(
		        new TArchiveDetector(
		            TArchiveDetector.NULL,
		            new Object[][] {
		                { "zip", new ZipDriver(IOPoolLocator.SINGLETON)},
		                { "jar", new ZipDriver(IOPoolLocator.SINGLETON)},
		            }));
		
		AppGameContainer gameContainer = new AppGameContainer(new GameHandler(args));
		gameContainer.setDisplayMode(800, 600, false);
		gameContainer.setShowFPS(false);
		gameContainer.start();
	}
	
	private static String parseArgs(String[] args, String getThis) {
		getThis = "-" + getThis;
		for (int i=0; i < args.length; i++) {
			if (args[i].equals(getThis)) {
				if (args[i + 1].startsWith("-")) { return ""; }
				return args[i + 1];
			}
		}
		return "";
	}

}
