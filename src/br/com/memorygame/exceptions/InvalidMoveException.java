package br.com.memorygame.exceptions;

@SuppressWarnings("serial")
public class InvalidMoveException extends Exception{

	public InvalidMoveException(String msg) {
		super(msg);
	}
}
