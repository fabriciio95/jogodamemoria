package br.com.memorygame.core;

import java.util.ArrayList;
import java.util.List;

import br.com.memorygame.constants.Constants;
import br.com.memorygame.exceptions.RepeatedMoveException;
import br.com.memorygame.interfaces.Deck;
import br.com.memorygame.utils.Utils;

public class Board {

	private Character[][] boardCovered = new Character[Constants.BORDER_SIZE][Constants.BORDER_SIZE];
	private Character[][] boardUncovered = new Character[Constants.BORDER_SIZE][Constants.BORDER_SIZE];
	private Deck deck;
	private static final char DEFAULT_SYMBOL = '*';

	public Board(Deck deck) {
		if (deck == null) {
			throw new NullPointerException("Nenhum baralho foi passado!");
		}

		this.deck = deck;
		hideCardsOnTheBoardCovered();
		putCardsOnTheBoardUncovered();
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public void hideCardsOnTheBoardCovered() {
		for (int row = 0; row < boardCovered.length; row++) {
			for (int column = 0; column < boardCovered[row].length; column++) {
				boardCovered[row][column] = DEFAULT_SYMBOL;
			}
		}
	}

	public void printBoardCovered() {
		for (int row = 0; row < boardCovered.length; row++) {
			for (int column = 0; column < boardCovered[row].length; column++) {
				System.out.print(boardCovered[row][column] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void putCardsOnTheBoardUncovered() {
		int cont = 0;
		List<Character> cardsUsed = new ArrayList<Character>();
		while (cont < deck.getCards().size()) {
			Character card = null;;
			do {
				card = nextCardPair(cardsUsed);
			} while(card == null);
			cardsUsed.add(card);
			for (int i = 0; i < 2; i++) {
				boolean isPutCard = shuffleCardOntheBoardUncovered(Utils.newRandomValue(10), card);
				if (isPutCard == false) {
					i--;
				}
			}
			cont += 1;
		}
	}

	private boolean shuffleCardOntheBoardUncovered(int i, char card) {
		if (i == 0) {
			return scrollVerticalTopLeft(card);
		} else if (i == 1) {
			return scrollHorizontalBack(card);
		} else if (i == 2) {
			return scrollVerticalLowLeft(card);
		} else if (i == 3) {
			return scrollDiagonalLowLeft(card);
		} else if (i == 4) {
			return scrollDiagonalTopLeft(card);
		} else if (i == 5) {
			return scrollDiagonalLowRight(card);
		} else if (i == 6) {
			return scrollDiagonalTopRight(card);
		} else if (i == 7) {
			return scrollVerticalTopRight(card);
		} else if (i == 8) {
			return scrollVerticalLowRight(card);
		} else {
			return scrollHorizontalFront(card);
		}
	}

	private Character nextCardPair(List<Character> cardsToRemove) {
		List<Character> cards = deck.getCards();
		cards.removeAll(cardsToRemove);
		return searchItemFree(cards, 0);
	}

	private boolean scrollHorizontalFront(char card) {
		for (int row = 0; row < boardUncovered.length; row++) {
			for (int column = 0; column < boardUncovered[row].length; column++) {
				Character item = boardUncovered[row][column];
				if (item == null || item == ' ') {
					boardUncovered[row][column] = card;
					return true;
				}
			}
		}
		return false;
	}

	private boolean scrollHorizontalBack(char card) {
		for (int row = boardUncovered.length - 1; row >= 0; row--) {
			for (int column = boardUncovered[row].length - 1; column >= 0; column--) {
				Character item = boardUncovered[row][column];
				if (item == null || item == ' ') {
					boardUncovered[row][column] = card;
					return true;
				}
			}
		}
		return false;
	}

	private boolean scrollVerticalTopLeft(char card) {
		int column = 0;
		while (column < Constants.BORDER_SIZE) {
			for (int row = 0; row < boardUncovered.length; row++) {
				Character item = boardUncovered[row][column];
				if (item == null || item == ' ') {
					boardUncovered[row][column] = card;
					return true;
				}
			}
			column += 1;
		}
		return false;
	}

	private boolean scrollVerticalLowLeft(char card) {
		int column = 0;
		while (column < Constants.BORDER_SIZE) {
			for (int row = boardUncovered.length - 1; row >= 0; row--) {
				Character item = boardUncovered[row][column];
				if (item == null || item == ' ') {
					boardUncovered[row][column] = card;
					return true;
				}
			}
			column += 1;
		}
		return false;
	}

	private boolean scrollVerticalTopRight(char card) {
		int column = Constants.BORDER_SIZE - 1;
		while (column >= 0) {
			for (int row = 0; row < boardUncovered.length; row++) {
				Character item = boardUncovered[row][column];
				if (item == null || item == ' ') {
					boardUncovered[row][column] = card;
					return true;
				}
			}
			column -= 1;
		}
		return false;
	}

	private boolean scrollVerticalLowRight(char card) {
		int column = Constants.BORDER_SIZE - 1;
		while (column >= 0) {
			for (int row = boardUncovered.length - 1; row >= 0; row--) {
				Character item = boardUncovered[row][column];
				if (item == null || item == ' ') {
					boardUncovered[row][column] = card;
					return true;
				}
			}
			column -= 1;
		}
		return false;
	}

	private boolean scrollDiagonalTopLeft(char card) {
		for (int row = 0, column = 0; row < boardUncovered.length; row++, column++) {
			Character item = boardUncovered[row][column];
			if (item == null || item == ' ') {
				boardUncovered[row][column] = card;
				return true;
			}
		}
		return false;
	}

	private boolean scrollDiagonalLowLeft(char card) {
		for (int row = boardUncovered.length - 1, column = boardUncovered.length - 1; row >= 0; row--, column--) {
			Character item = boardUncovered[row][column];
			if (item == null || item == ' ') {
				boardUncovered[row][column] = card;
				return true;
			}
		}
		return false;
	}

	private boolean scrollDiagonalTopRight(char card) {
		for (int row = 0, column = Constants.BORDER_SIZE - 1; row < boardUncovered.length; row++, column--) {
			Character item = boardUncovered[row][column];
			if (item == null || item == ' ') {
				boardUncovered[row][column] = card;
				return true;
			}
		}
		return false;
	}

	private boolean scrollDiagonalLowRight(char card) {
		for (int row = boardUncovered.length - 1, column = 0; row >= 0; row--, column++) {
			Character item = boardUncovered[row][column];
			if (item == null || item == ' ') {
				boardUncovered[row][column] = card;
				return true;
			}
		}
		return false;
	}

	public boolean searchPairOnBoardCovered(Move move) {
		int pair = 0;
		if (move != null) {
			char card = boardCovered[move.getRowMove()][move.getColumnMove()];
			for (int row = 0; row < boardCovered.length; row++) {
				for (int column = 0; column < boardCovered[row].length; column++) {
					Character item = boardCovered[row][column];
					if (item != null && item == card) {
						pair += 1;
					}
				}
			}
		}
		return pair == 2 ? true : false;
	}

	private Character searchItemFree(List<Character> cards, int indice) {
		if (boardUncovered.length == indice) {
			return null;
		}
		char item = cards.get(indice);
		for (int row = 0; row < boardUncovered.length; row++) {
			for (int column = 0; column < boardUncovered[row].length; column++) {
				if (boardUncovered[row][column] != null && boardUncovered[row][column] == item) {
					return searchItemFree(cards, indice + 1);
				}
			}
		}
		return item;
	}

	public void printBoardUncovered() {
		for (int row = 0; row < boardUncovered.length; row++) {
			for (int column = 0; column < boardUncovered[row].length; column++) {
				System.out.print(boardUncovered[row][column] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public boolean makeMove(Move move) throws RepeatedMoveException {
		char card = boardCovered[move.getRowMove()][move.getColumnMove()];
		if (card == DEFAULT_SYMBOL) {
			boardCovered[move.getRowMove()][move.getColumnMove()] = boardUncovered[move.getRowMove()][move
					.getColumnMove()];
			return true;
		} else {
			throw new RepeatedMoveException("ERRO, JOGADA INVÁLIDA! Você já acertou a carta dessa posição!");
		}
	}

	public void undoMove(Move move) {
		if (move != null) {
			boardCovered[move.getRowMove()][move.getColumnMove()] = DEFAULT_SYMBOL;
		}
	}

	public boolean isRightMove(Move card1, Move card2) {
		if (boardCovered[card1.getRowMove()][card1.getColumnMove()] == boardCovered[card2.getRowMove()][card2
				.getColumnMove()]) {
			return true;
		} else {
			undoMove(card1);
			undoMove(card2);
			return false;
		}
	}

	public boolean isAllPairsFound() {
		for (int row = 0; row < boardCovered.length; row++) {
			for (int column = 0; column < boardCovered[row].length; column++) {
				if (boardCovered[row][column] == DEFAULT_SYMBOL) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void resetBoardUncovored() {
		this.boardUncovered = new Character[Constants.BORDER_SIZE][Constants.BORDER_SIZE];
	}
}
