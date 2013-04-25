package me.expertmac2.rpg;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoadingState extends BasicGameState {

	private boolean loadingDone = false;
	private GameHandler gameHandler = null;
	private int timer = 50;
	private int stateId;

	public LoadingState(GameHandler gh, int sid) {
		stateId = sid;
		gameHandler = gh;
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawString("Loading engine files...", 4, 10);
		g.drawString("timeout: " + timer, 4, 30);

	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		timer = timer - 1;
		if (timer <= 0) {
			gameHandler.enterState(gameHandler.MainMenuSID);
			timer = 50;
		}
	}

	@Override
	public int getID() {
		return stateId;
	}

}
