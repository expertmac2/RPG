package me.expertmac2.rpg;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.BasicGameState;

public class MainMenuState extends BasicGameState implements KeyListener {
	
	private int stateId = 0;
	private Image background = null;
	private boolean switchToGame = false;
	private String mainMenuTitle = "XpertRPG - A L P H A";
	
	public MainMenuState(int stateId) {
		this.stateId = stateId;
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		background = new Image("res/gfx/main-menu.jpg");
		
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawImage(background, 0, 0);
		g.drawString(mainMenuTitle, 300, 20);
		g.drawString("Hit the S P A C E button to start the demo game.", 180, 50);
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (switchToGame) {
			GameHandler gameHandler = (GameHandler) game;
			gameHandler.enterState(gameHandler.GameplaySID);
			switchToGame = false;
		}
	}
	
	@Override
	public void keyPressed(int key, char charOfKey) {
		if (key == Input.KEY_SPACE) {
			switchToGame = true;
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateId;
	}
	
	

}
