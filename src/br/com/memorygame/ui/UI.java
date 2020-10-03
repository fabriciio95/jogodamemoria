package br.com.memorygame.ui;

import java.util.Scanner;

import br.com.memorygame.constants.Constants;
import br.com.memorygame.core.Move;
import br.com.memorygame.exceptions.InvalidMoveException;
import br.com.memorygame.exceptions.WrongOptionException;

public class UI {

	private static Scanner sc = new Scanner(System.in);
	
	public static void message(String msg) {
		System.out.println(msg);
	}
	
	public static void newLine() {
		System.out.println();
	}
	
	public static Move makeMove(String msg) throws InvalidMoveException {
		System.out.print(msg);
		String make = sc.nextLine();
		if(make.trim().isEmpty()) {
			throw new InvalidMoveException("ERRO! Informe uma posição válida em vez de espaços em branco!");
		} else if(!make.contains(",")) {
			throw new InvalidMoveException("ERRO! Informe uma posição de linha e coluna separados por vírgula!");
		} else if(make.length() > 3) {
			throw new InvalidMoveException("ERRO! Informe apenas uma posição de linha e coluna separados por vírgula!");
		}
		Character line = make.charAt(0);
		Character posColumn = make.charAt(2);
		if(!Character.isDigit(line) || !Character.isDigit(posColumn)) {
			throw new InvalidMoveException("ERRO! Informe apenas NÚMEROS, referente a linha e coluna da carta desejada separados por vírgula!");
		}
		int row = Integer.parseInt(make.split(",")[0]);
		int column = Integer.parseInt(make.split(",")[1]);
		if(row >= Constants.BORDER_SIZE || column >= Constants.BORDER_SIZE || row < 0 || column < 0) {
			throw new InvalidMoveException("ERRO! Essa posição não existe no baralho, informe uma posição válida");
		}
		return new Move(row, column);
	}
	
	public static int showMenu(String menu) throws NumberFormatException, WrongOptionException{
		System.out.println(menu);
		String option = sc.nextLine();
		if(option.trim().isEmpty()) {
			throw new NumberFormatException("ERRO! Informe uma opção válida em vez de espaços em branco!");
		} else if(option.length() > 1) {
			throw new NumberFormatException("Informe apenas uma opção!");
		}
		char optionChosen = option.charAt(0);
		if(!Character.isDigit(optionChosen)) {
			throw new NumberFormatException("ERRO! Informe apenas o NÚMERO referente a opção desejada!");
		}
		
		int optionChosenInt = Integer.parseInt(option);
		if(optionChosenInt < 0) {
			throw new WrongOptionException("Opção inválida!");
		}
		return optionChosenInt;
	}
	
	public static int getValue(String msg) throws NumberFormatException, WrongOptionException{
		System.out.print(msg);
		String option = sc.nextLine();
		if(option.trim().isEmpty()) {
			throw new NumberFormatException("ERRO! Informe uma valor válido em vez de espaços em branco!");
		} 
		char optionChosen = option.charAt(0);
		if(!Character.isDigit(optionChosen)) {
			throw new NumberFormatException("ERRO! Informe apenas o NÚMERO!");
		}
		
		int optionChosenInt = Integer.parseInt(option);
		if(optionChosenInt <= 0) {
			throw new WrongOptionException("Valor não pode ser negativo ou zero!");
		}
		return optionChosenInt;
	}
	
	public static String getString(String msg) {
		System.out.print(msg);
		return sc.nextLine();
	}
}
