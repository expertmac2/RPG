package me.expertmac2.rpg;

import java.net.URL;
import java.net.URLClassLoader;

import me.expertmac2.rpg.util.RPGClassLoader;
import me.expertmac2.rpg.util.RPGException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameHandler extends StateBasedGame {
	
	public int LoadingSID = 0;
	public int MainMenuSID = 1;
	public int GameplaySID = 2;
	public final String[] args;

	public GameHandler(String[] s) {
		super("-=- XpertRPG Engine -=- BUILD: 0 -=-");
		args = s;
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new LoadingState(this, LoadingSID));
		this.addState(new MainMenuState(MainMenuSID));
		this.addState(new Game(GameplaySID, null, parseArgs("game")));
		
		this.enterState(1);
	}
	
	private String parseArgs(String getThis) {
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
