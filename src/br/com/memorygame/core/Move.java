package br.com.memorygame.core;

public class Move {

	private int rowMove;
	private int columnMove;
	
	public Move(int rowMove, int columnMove) {
		this.rowMove = rowMove;
		this.columnMove = columnMove;
	}
	
	public int getRowMove() {
		return rowMove;
	}
	public void setRowMove(int rowMove1) {
		this.rowMove = rowMove1;
	}
	public int getColumnMove() {
		return columnMove;
	}
	public void setColumnMove(int columnMove1) {
		this.columnMove = columnMove1;
	}
}
