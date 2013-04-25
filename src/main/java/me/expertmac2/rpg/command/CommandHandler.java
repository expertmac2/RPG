package me.expertmac2.rpg.command;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.actors.Actor;
import me.expertmac2.rpg.actors.NPC;
import me.expertmac2.rpg.events.CutsceneUtils;
import me.expertmac2.rpg.file.SaveHandler;
import me.expertmac2.rpg.logic.Task;
import me.expertmac2.rpg.util.RPGException;

public class CommandHandler {

	public static void handleCommand(Game game, GameContainer container, String s) {
		String[] cmd = s.split(" ");
		try {
			if (game.doesGameVarExist(cmd[0])) {
				game.setGameVariable(cmd[0], Boolean.parseBoolean(cmd[1]));
				game.addDebugPromptOutputLine(cmd[0] + " set to " + cmd[1]);
			} else if (cmd[0].equals("quit")) {
			} else if (cmd[0].equals("forcegc")) {
				System.gc();
				game.addDebugPromptOutputLine("## Sent a garbage collection request to the JVM.");
			} else if (cmd[0].equals("changelevel")) {
				if (game.getMapManager().getMapInfo(cmd[1]) == null) {
					game.addDebugPromptOutputLine("!! Map doesn't exist!");
					game.addDebugPromptOutputLine("## Available maps: desert, sewers");
					return;
				}
				game.getMapManager().setCurrentMap(game, cmd[1], 0);
			} else if (cmd[0].equals("help")) {
				game.addDebugPromptOutputLine("## Available commands: debug <boolean>, showactorcoll <boolean>, ");
				game.addDebugPromptOutputLine("showmapcoll <boolean>, changelevel <level name>, forcegc, quit");
			} else if (cmd[0].equals("testautowalk")) {
				Task task = CutsceneUtils.moveActorToX(game, 0, 240, "testautowalk");
				CutsceneUtils.moveActorToY(game, 0, 190, "testautowalk", task);
			} else if (cmd[0].equals("interruptlogic")) {
				game.getLogicThread().interrupt();
			} else if (cmd[0].equals("fireevent")) {
				if (game.getMapManager().getCurrentMap().mapEvents.containsKey(Integer.parseInt(cmd[1]))) {
					game.getMapManager().getCurrentMap().mapEvents.get(Integer.parseInt(cmd[1])).fireNow();
				} else {
					game.addDebugPromptOutputLine("!! Invalid Event ID");
				}
			} else if (cmd[0].equals("stresstest") && cmd[1].equals("FFSTRESSTESTPASSWD")) {
				for (int i=0; i != 400; i++) {
					try {
						game.getMapManager().addActorToWorld(new NPC((float) i + 500, (float) i + 100), 
								game.getMapManager().getCurrentMapName(), true, true, game);
					} catch (RPGException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else if (cmd[0].equals("save")) {
				try {
					SaveHandler.saveGame(game, cmd[1]);
				} catch (ArrayIndexOutOfBoundsException aioobe) {
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				game.addDebugPromptOutputLine("Saved game with file: " + cmd[0]);
			} else if (cmd[0].equals("load")) {
				try {
					SaveHandler.loadSave(game, cmd[1]);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RPGException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				game.addDebugPromptOutputLine("Loaded save game " + cmd[0]);
			} else if (cmd[0].equals("tp")) {
				game.getMapManager().getActor(0).setPosition(Float.parseFloat(cmd[1]), Float.parseFloat(cmd[2]));
			} else {
				game.addDebugPromptOutputLine("!! Invalid command");
			}
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			game.addDebugPromptOutputLine("!! Not enough parameters");
		} /*catch (RPGException e) {
			e.printStackTrace();
		}*/
	}

}
