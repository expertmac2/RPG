package me.expertmac2.rpg.logic;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import me.expertmac2.rpg.Game;
import me.expertmac2.rpg.util.RPGException;

public class TaskScheduler {
	
	private Game game;
	private HashMap<Long, Task> tasks = new HashMap<Long, Task>();
	
	public TaskScheduler(Game game) {
		this.game = game;
	}
	
	public void scheduleTask(Task task) {
		if (task == null) {
			throw new IllegalArgumentException("Cannot schedule a null task!"); 
		}
		tasks.put((long) tasks.size(), task);
	}
	
	public void updateTasks() {
		for (long l=0; (long) (tasks.size() - 1) >= l; l++) {
			if (l != -1) {
				try {
					tasks.get(l).executeTask(game);
				} catch (NullPointerException npe) { }
				//if (tasks.get(l).isDone) tasks.remove(l);
			}
		}
	}
	
	public ArrayList<Task> getAllTasksScheduledBy(String str) {
		ArrayList<Task> returnThis = new ArrayList<Task>();
		for (long l=0; (long) (tasks.size() - 1) >= l; l++) {
			if (l != -1 && tasks.get(l).scheduledBy.equals(str)) {
				returnThis.add(tasks.get(l));
			}
		}
		return returnThis;
	}
	
	public Task getTask(long id) {
		return tasks.get(id);
	}
	
	public HashMap<Long, Task> getAllTasks() {
		return tasks;
	}
	
	public void setHashMap(HashMap<Long, Task> newHM) {
		tasks = newHM;
	}

}
