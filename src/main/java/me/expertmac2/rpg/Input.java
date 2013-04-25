package me.expertmac2.rpg;

import me.expertmac2.rpg.actors.NPC;
import me.expertmac2.rpg.util.RPGException;
import me.expertmac2.rpg.util.Resources.ActorState;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Input {
	
	private final GameContainer container;
	
	public Input(GameContainer gc) {
		container = gc;
	}
	
	public void handleInput(Game game) {
		boolean keyLeft = container.getInput().isKeyDown(org.newdawn.slick.Input.KEY_LEFT);
		boolean keyRight = container.getInput().isKeyDown(org.newdawn.slick.Input.KEY_RIGHT);
		boolean keyUp = container.getInput().isKeyDown(org.newdawn.slick.Input.KEY_UP);
		boolean keyDown = container.getInput().isKeyDown(org.newdawn.slick.Input.KEY_DOWN);
		boolean mouseDown = container.getInput().isMouseButtonDown(0);
		boolean areTwoKeysDown = ((keyUp && keyLeft) || (keyUp && keyRight) || 
				(keyDown && keyLeft) || (keyDown && keyRight));
		
		if (container.getInput().isKeyDown(org.newdawn.slick.Input.KEY_GRAVE)) {
			game.getConsole().setVisible(true);
		}
		
		if (!keyLeft && !keyRight && !keyUp && !keyDown && 
				game.getMapManager().getActor(0).getActorState() != ActorState.INCUTSCENE) {
			game.getMapManager().getActor(0).setCurrentAnimation(0);
			game.getMapManager().getActor(0).updateStatus(ActorState.IDLE);
		}
		
		// right: 1   left: 2   down: 3   up: 4
		// up-left: 5   up-right: 6   down-left: 7   down-right: 8
		
		if (game.getMapManager().getActor(0).getActorState() == ActorState.INCUTSCENE) return;
		if (keyLeft || keyRight || keyUp || keyDown) {
			game.getMapManager().getActor(0).setCurrentAnimation(1);
			game.getMapManager().getActor(0).setActorState(ActorState.MOVING);
		}
		
		if (mouseDown) {
			try {
				game.getMapManager().addActorToWorld(new NPC(Mouse.getX() + game.getCamera().getX(), 
						(game.gameContainer.getHeight() - Mouse.getY()) + game.getCamera().getY()), 
						game.getMapManager().getCurrentMapName(), true, true, game);
			} catch (RPGException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (keyUp && keyLeft) { game.getMapManager().getActor(0).movement(5, game); }
		if (keyUp && keyRight) { game.getMapManager().getActor(0).movement(6, game); }
		if (keyDown && keyLeft) { game.getMapManager().getActor(0).movement(7, game); }
		if (keyDown && keyRight) { game.getMapManager().getActor(0).movement(8, game); }
		
		if (areTwoKeysDown) { return; }
		 
		if (keyLeft) { game.getMapManager().getActor(0).movement(2, game); }
		if (keyRight) { game.getMapManager().getActor(0).movement(1, game); }
		if (keyUp) { game.getMapManager().getActor(0).movement(4, game); }
		if (keyDown) { game.getMapManager().getActor(0).movement(3, game); }
		
	}

}
