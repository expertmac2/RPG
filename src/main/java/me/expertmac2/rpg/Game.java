package me.expertmac2.rpg;

import java.io.File;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

import me.expertmac2.rpg.actors.Actor;
import me.expertmac2.rpg.base.BaseGame;
import me.expertmac2.rpg.command.ConsoleWindow;
import me.expertmac2.rpg.file.GameLoader;
import me.expertmac2.rpg.maps.Camera;
import me.expertmac2.rpg.maps.MapManager;
import me.expertmac2.rpg.util.RPGException;
import me.expertmac2.rpg.logic.LogicThread;
import me.expertmac2.rpg.logic.TaskScheduler;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.AngelCodeFont;

public class Game extends BasicGameState {

	private int stateId;
	private Camera cam;
	private Input inputHandler;
	public GameContainer gameContainer;
	private MapManager mapManager;
	private TaskScheduler taskScheduler;
	private LogicThread logicThread;
	private final ConsoleWindow console;
	private HashMap<String, Boolean> gameVariables = new HashMap<String, Boolean>();
	private int timeoutUntilGCRequest = 100000;
	public int delta = 0;
	public boolean shouldLogicBeCalculated = false;
	public boolean shouldRenderGame = true;
	public final String gameName;
	private URLClassLoader urlClassLoader = null;
	private double msPerFrame = 0.0;

	private BaseGame game = null;
	private String gameJarPath = "##ENGINE-notset";

	public Game(int stateId, File gameJar, String gn) {
		this.stateId = stateId;
		gameName = gn;
		console = new ConsoleWindow(this);
	}

	public void init(GameContainer container, StateBasedGame gameHandler)
			throws SlickException {
		container.setVSync(true);
		gameJarPath = "res/games/" + gameName + ".jar";
		mapManager = new MapManager();
		gameContainer = container;

		try {
			game = GameLoader.loadGame(gameName, (GameHandler) gameHandler);
			game.loadMaps(this);
		} catch (RPGException rpge) {
			throw new SlickException("Game loading error", rpge);
		}

		cam = new Camera(this, container, mapManager.getTiledMap("desert"));
		mapManager.setCurrentMap(this, "desert", 0);
		inputHandler = new Input(container);
		logicThread = new LogicThread(this, delta);
		taskScheduler = new TaskScheduler(this);

		shouldLogicBeCalculated = true;
		getLogicThread().start();

		game.init(this, container);

		// init game vars
		gameVariables.put("debug", false);
		gameVariables.put("showactorcoll", true);
		gameVariables.put("showmapcoll", true);
		gameVariables.put("noclip", false);
		gameVariables.put("mapedit", false);
	}

	public void update(GameContainer container, StateBasedGame gameHandler, int delta)
			throws SlickException {
		cam.centerOn(mapManager.getActor(0).getX(), mapManager.getActor(0).getY());
		inputHandler.handleInput(this);
		game.update(this, container, delta);
		this.gameContainer = container;
		timeoutUntilGCRequest = timeoutUntilGCRequest - 1;
		if (timeoutUntilGCRequest <= 0) {
			System.gc();
			timeoutUntilGCRequest = 100000;
		}
		this.delta = delta;
	}

	public void render(GameContainer container, StateBasedGame gameHandler, Graphics g)
			throws SlickException {
		if (!shouldRenderGame) return;
		msPerFrame = 1000 / container.getFPS();
		g.setFont(game.getFont());
		ArrayList<Actor> mainLayer = new ArrayList<Actor>();
		ArrayList<Actor> differentLayerActors = new ArrayList<Actor>();
		for (int i=0; i <= (mapManager.getActorMapSize() - 1); i++) { // render all actors
			if (i != -1 && mapManager.getActor(i).actorAlreadySpawned && mapManager.getActor(i).actorIsVisible) {
				if (mapManager.getActor(i).getLayer() != 0) {
					differentLayerActors.add(mapManager.getActor(i));
				} else {
					mainLayer.add(mapManager.getActor(i));
				}
			}
		}
		cam.drawMap(0);
		cam.translateGraphics();
		for (Actor actor : mainLayer) {
			g.drawAnimation(actor.getCurrentAnimation(), actor.getX(), actor.getY());
			if (gameVariables.get("showactorcoll")) {
				g.setColor(Color.green);
				g.draw(actor.collRectangle);
			}
		}
		if (gameVariables.get("showmapcoll")) {
			for (Rectangle r : mapManager.getMapCollRectangles()) {
				g.setColor(Color.red);
				g.draw(r);
			}
		}
		game.renderWorld(this, container, g);
		if (mapManager.getCurrentMap().getTiledMap().getLayerCount() > 0) {
			cam.untranslateGraphics();
			for (int i=1; i < mapManager.getCurrentMap().getTiledMap().getLayerCount(); i++) {
				cam.drawMap(i);
				cam.translateGraphics();
				for (Actor actor : mainLayer) {
					if (actor.getLayer() == i) {
						g.drawAnimation(actor.getCurrentAnimation(), actor.getX(), actor.getY());
					}
					if (gameVariables.get("showactorcoll")) {
						g.setColor(Color.green);
						g.draw(actor.collRectangle);
					}
				}
				//game.renderWorld(this, container, g, i);
			}
			// HUD won't show unless you translate the graphics again before untranslating
			cam.translateGraphics();
		}
		// hud stuff
		cam.untranslateGraphics();
		game.renderHUD(this, container, g);
		g.setColor(Color.white);
		g.drawString(container.getFPS() + " FPS", 0, 0);
		if (gameVariables.get("debug")) {
			g.drawString("player x: " + mapManager.getActor(0).getX()
					+ " player y: " + mapManager.getActor(0).getY(), 0, 20);
			g.drawString("Number of Actors: " + (mapManager.getActorMapSize() - 1), 0, 40);
			g.drawString("PS: " + mapManager.getActor(0).getState(), 0, 60);
			g.drawString("ticks per frame: " + delta, 0, 100);
			g.drawString("current map: " + mapManager.getCurrentMapName(), 0, 120);
			g.drawString("    N  ", 0, 160);
			g.drawString("  W-+-E", 0, 180);
			g.drawString("    S  ", 0, 200);
		} else {
			g.drawString("ticks per frame: " + delta, 0, 40);
			g.drawString("time to render a frame: " + msPerFrame + "ms", 0, 60);
			g.drawString("using " + 
					(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) 
					+ "MB of " + Runtime.getRuntime().maxMemory() / (1024 * 1024) + "MB", 0, 80);
		}
	}

	/**
	 * 
	 * Draws a string onto the screen using a specific AngelCodeFont.
	 * 
	 * @param g The Graphics instance to use.
	 * @param font The AngelCodeFont that should be used to draw the text.
	 * @param s The text to be drawn.
	 * @param x The X coordinate of where the text should be drawn.
	 * @param y The Y coordinate of where the text should be drawn.
	 * @deprecated As of build 24, due to @link org.newdawn.slick.AngelCodeFont#drawString(float, float, String).
	 */
	@Deprecated
	public void drawStringUsingFont(Graphics g, AngelCodeFont font, String s, float x, float y) {
		Font originalFont = g.getFont();
		g.setFont(font);
		g.drawString(s, x, y);
		g.setFont(originalFont);
	}

	/**
	 * Returns the Camera instance for the game.
	 * 
	 * @return {@link me.expertmac2.rpg.maps.Camera}
	 * @see me.expertmac2.rpg.maps.Camera
	 */
	public Camera getCamera() {
		return this.cam;
	}

	public MapManager getMapManager() {
		return this.mapManager;
	}

	public TaskScheduler getScheduler() {
		return this.taskScheduler;
	}

	public LogicThread getLogicThread() {
		return logicThread;
	}

	public URLClassLoader getURLClassLoader() {
		return urlClassLoader;
	}

	public void setURLClassLoader(URLClassLoader ucl) {
		urlClassLoader = ucl;
	}

	public void addDebugPromptOutputLine(String s) {
		console.addLineToConsole(s);
	}

	public boolean getVariableValue(String varName) {
		return gameVariables.get(varName);
	}

	public void setGameVariable(String varName, boolean value) {
		gameVariables.put(varName, value);
	}

	public boolean doesGameVarExist(String varName) {
		return gameVariables.containsKey(varName);
	}

	public String getGameJarPath() {
		return gameJarPath;
	}

	public ConsoleWindow getConsole() {
		return console;
	}

	@Override
	public int getID() {
		return stateId;
	}

}
