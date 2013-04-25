package me.expertmac2.rpg.util;

public class Resources {
	
	private final static String OS = System.getProperty("os.name").toLowerCase();
	
	public enum OSVersion {
		Windows, Linux, MacOSX, Solaris, Unknown;
	}

	public enum Direction {
		NORTH, SOUTH, EAST, WEST;
	}
	
	public enum ActorState {
		IDLE, MOVING, ININVENTORY, INBATTLE, INCUTSCENE, NULL;
	}
	
	public enum GameState {
		NULL, RUNNING, PAUSED;
	}
	
	public enum Priority { // not used
		NULL, VERYLOW, LOW, MEDIUM, HIGH, VERYHIGH;
	}
	
	public static OSVersion getOSVersion() {
		if (OS.indexOf("win") >= 0)
			return OSVersion.Windows;
		else if (OS.indexOf("mac") >= 0)
			return OSVersion.MacOSX;
		else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0)
			return OSVersion.Linux;
		else if (OS.indexOf("sunos") >= 0)
			return OSVersion.Solaris;
		else
			return OSVersion.Unknown;
		
	}
	
	public static String removeLastStringChar(String s) {
		if (s.length() == 0) {
			return s;
		}
		return s.substring(0, s.length() - 1);
	}
	
	public static int priorityToInt(Priority pri) { // not used
		switch(pri) {
			case NULL:
				return 0;
			case VERYLOW:
				return 1;
			case LOW:
				return 2;
			case MEDIUM:
				return 3;
			case HIGH:
				return 4;
			case VERYHIGH:
				return 5;
			default:
				return 0;
		}
	}
	
	public static Priority intToPriority(int i) {
		switch(i) {
			case 0:
				return Priority.NULL;
			case 1:
				return Priority.VERYLOW;
			case 2:
				return Priority.LOW;
			case 3:
				return Priority.MEDIUM;
			case 4:
				return Priority.HIGH;
			case 5:
				return Priority.VERYHIGH;
			default:
				return Priority.NULL;
		}
	}
	
}
