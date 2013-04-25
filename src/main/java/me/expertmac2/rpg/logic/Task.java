package me.expertmac2.rpg.logic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.util.RPGException;

public abstract class Task {
	
	public boolean isDone = false;
	private Task executeBeforeThis = null;
	private long timeout = 0; // in milliseconds (1000 ms = 1 s)
	private final boolean noExecuteConditions;
	public final String taskDescription;
	public final String scheduledBy;
	
	public Task(String desc, String schedBy) {
		taskDescription = desc;
		scheduledBy = schedBy;
		noExecuteConditions = true;
	}
	
	public Task(String desc, String schedBy, long time) {
		taskDescription = desc;
		scheduledBy = schedBy;
		timeout = time;
		noExecuteConditions = false;
	}
	
	public Task(String desc, String schedBy, Task beforeTask) {
		taskDescription = desc;
		scheduledBy = schedBy;
		executeBeforeThis = beforeTask;
		noExecuteConditions = false;
	}
	
	public Task(String desc, String schedBy, Task beforeTask, long time) {
		taskDescription = desc;
		scheduledBy = schedBy;
		executeBeforeThis = beforeTask;
		timeout = time;
		noExecuteConditions = false;
	}
	
	public final void executeTask(Game game) {
		if (isDone) return;
		if (!noExecuteConditions) {
			if (executeBeforeThis != null && !executeBeforeThis.isDone) return;
			if (timeout > 0) {
				timeout = timeout - game.delta;
				return;
			}
		}
		executeTaskActions(game);
	}
	
	protected abstract void executeTaskActions(Game game);

}
