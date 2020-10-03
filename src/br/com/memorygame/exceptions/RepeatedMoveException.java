package br.com.memorygame.exceptions;

@SuppressWarnings("serial")
public class RepeatedMoveException extends Exception {

	public RepeatedMoveException(String msg) {
		super(msg);
	}
}
