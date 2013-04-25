package me.expertmac2.rpg.util;

public class RPGException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -67154071772829640L;
	
	public RPGException() {
		super();
	}
	
	public RPGException(String str) {
		super(str);
	}
	
	public RPGException(Exception exception) {
		super(exception);
	}

}
