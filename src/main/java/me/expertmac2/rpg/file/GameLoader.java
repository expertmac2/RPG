package me.expertmac2.rpg.file;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.newdawn.slick.AngelCodeFont;

import de.schlichtherle.truezip.file.TFile;
import de.schlichtherle.truezip.file.TFileInputStream;
import de.schlichtherle.truezip.file.TFileReader;
import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.GameHandler;
import me.expertmac2.rpg.base.BaseGame;
import me.expertmac2.rpg.events.Event;
import me.expertmac2.rpg.util.RPGClassLoader;
import me.expertmac2.rpg.util.RPGException;

public class GameLoader {
	
	private static SAXReader saxReader = new SAXReader();

	public static BaseGame loadGame(String gameName, GameHandler gameHandler) throws RPGException {
		TFile jar = new TFile("res/games/" + gameName + ".jar");
		if (!jar.exists()) { throw new RPGException("The specified game's .jar doesn't exist!"); }
		TFile gameInfo = new TFile("res/games/" + gameName + ".jar/cfg/GameConfig.xml");
		TFileReader giInput = null;
		Element root = null;
		String fullName, author, gameFont, urlPath = "jar:file:" + jar.getPath() + "!/";;
		String[] maps, gameVarsTemp;
		HashMap<String, Boolean> gameVars = new HashMap<String, Boolean>();
		try {
			  
			Game game = (Game) gameHandler.getState(gameHandler.GameplaySID);
			game.setURLClassLoader(new URLClassLoader(new URL[] { new URL(urlPath) }));
			
			System.out.println(System.getProperty("java.class.path"));
			
			if (!gameInfo.exists()) { gameInfo.createNewFile(); }
			giInput = new TFileReader(gameInfo);
			root = saxReader.read(giInput).getRootElement();
			
			fullName = root.elementText("fullname");
			author = root.elementText("author");
			gameFont = root.element("misc").elementText("gamefont");
			maps = root.elementText("maps").split(",");
			gameVarsTemp = root.elementText("gamevars").split(",");
			
			for (int i=0; i < gameVarsTemp.length; i++) {
				gameVars.put(gameVarsTemp[i], false);
			}
			
			Constructor<?> constructor = game.getURLClassLoader().loadClass(root.elementText("mainclass"))
					.getDeclaredConstructor(String.class, String.class, HashMap.class, String[].class, AngelCodeFont.class);
			constructor.setAccessible(true);
			return (BaseGame) constructor.newInstance(fullName, author, gameVars, maps,
					new AngelCodeFont("res/gfx/fonts/" + gameFont + ".fnt", 
							"res/gfx/fonts/" + gameFont + "_00.png"));
			
		} catch (Exception e) {
			throw new RPGException(e);
		} finally {
			if (giInput != null) {
				try {
					giInput.close();
				} catch (IOException ioe) {
					// TODO Auto-generated catch block
					throw new RPGException(ioe);
				}
			}
		}
		
	}

}
