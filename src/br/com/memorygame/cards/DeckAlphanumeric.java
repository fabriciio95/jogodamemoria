package br.com.memorygame.cards;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import br.com.memorygame.constants.Constants;
import br.com.memorygame.interfaces.Deck;
import br.com.memorygame.utils.Utils;

public class DeckAlphanumeric implements Deck{
	
	private static final Path DECK_FILE = Path.of("deckalphanumeric.txt");
	private List<Character> deck = new ArrayList<Character>();
	private String name;

	
	public DeckAlphanumeric() {
		try {
			setup();
			this.name = "Alfanumérico";
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<Character> getCards() {
		List<Character> cards = new ArrayList<Character>();
		for(int i = 0; i < Constants.TOTAL_CARDS_PAIRS; i++) {
			int indexNewCard = Utils.newRandomValue(deck.size());
			Character card = deck.get(indexNewCard);
			if(!cards.contains(card)) {
				cards.add(card);
			} else {
				i--;
			}
		}
		return cards;
	}
	
	private void setup() throws IOException {
		if(!Files.exists(DECK_FILE)) {
			Files.createFile(DECK_FILE);
		}
		
		try(BufferedReader reader = Files.newBufferedReader(DECK_FILE)){
			String line;
			while((line = reader.readLine()) != null) {
				deck.add(line.toCharArray()[0]);
			}
		}
	}

	@Override
	public String getName() {
		return name;
	}
}
