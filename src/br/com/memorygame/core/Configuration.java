package br.com.memorygame.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.memorygame.cards.DeckAlphanumeric;
import br.com.memorygame.cards.DeckNumeric;
import br.com.memorygame.constants.Constants;
import br.com.memorygame.exceptions.WrongOptionException;
import br.com.memorygame.interfaces.Deck;
import br.com.memorygame.ui.UI;

public class Configuration {

	private Map<Integer, Deck> decks = new HashMap<Integer, Deck>();
	private int attempts = Constants.MAX_ATTEMPTS;
	private Board board;
	
	public Configuration(Board board) {
		this.board = board;
		getDecks();
	}

	public Map<Integer, Deck> getDecks() {
		decks.put(1, new DeckAlphanumeric());
		decks.put(2, new DeckNumeric());
		return decks;
	}
	
	public void makeSettings() throws NumberFormatException, WrongOptionException {
		int optionConfig = UI
				.showMenu("0-Alterar número máximo de tentativas (atual é " + attempts
						+ ")" + "\n1-Alterar baralho (atual é " + board.getDeck().getName() + " )");
		if(optionConfig == 0) {
			int optionAttemp = UI.showMenu("0-Retirar número de tentativas máximas 1-Alterar valor de tentativas máximas");
			if(optionAttemp == 0) {
				attempts = 0;
			} else if (optionAttemp == 1) {
				attempts = UI.getValue("Qual o novo valor máximo de tentativas? ");
				UI.newLine();
				UI.message("OK! Novo valor máximo de tentativas é " + attempts);
				UI.newLine();
			}
		} else if(optionConfig == 1) {
			showDecks();
			this.board.setDeck(decks.get(UI.getValue("Com qual baralho deseja jogar? ")));
			board.hideCardsOnTheBoardCovered();
			board.resetBoardUncovored();
			board.putCardsOnTheBoardUncovered();
			UI.message("Baralho alterado com sucesso!");
			UI.newLine();
		}
	}

	public int getAttempts() {
		return attempts;
	}
	
	private void showDecks() {
		Set<Entry<Integer, Deck>> index = decks.entrySet();
		for (Entry<Integer, Deck> entry : index) {
			System.out.println(entry.getKey() + " - " + entry.getValue().getName());
		}
	}
	
}
