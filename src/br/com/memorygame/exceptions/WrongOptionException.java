package br.com.memorygame.exceptions;

@SuppressWarnings("serial")
public class WrongOptionException extends Exception{

	public WrongOptionException(String msg) {
		super(msg);
	}
}
