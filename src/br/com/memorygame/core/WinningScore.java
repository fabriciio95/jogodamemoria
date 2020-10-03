package br.com.memorygame.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.memorygame.interfaces.ScorePlayer;
import br.com.memorygame.ui.UI;

public class WinningScore implements ScorePlayer{
	
	private Map<String, Integer> playersScore = new HashMap<String, Integer>();
	private static final Path SCORE_FILE = Path.of("winningscore.txt");
	
	
	public WinningScore() {
		try {
			setup();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void setup() throws IOException {
		if(!Files.exists(SCORE_FILE)) {
			 Files.createFile(SCORE_FILE);
		}
		
		try(BufferedReader reader = Files.newBufferedReader(SCORE_FILE)){
			String line;
			while((line = reader.readLine()) != null) {
				String[] tokens = line.split("\\|");
				playersScore.put(tokens[0].toUpperCase(), Integer.parseInt(tokens[1]));
			}
		}
	}

	@Override
	public Map<String, Integer> getPlayersScore() {
		return playersScore;
	}

	@Override
	public void saveScore(Player player) throws IOException {
		Integer score = getScorePlayer(player);
		if(score == null) {
			score = 0;
		}
		playersScore.put(player.getName().toUpperCase(), ++score);
		
		try(BufferedWriter writer = Files.newBufferedWriter(SCORE_FILE)){
			Set<Map.Entry<String, Integer>> scores = playersScore.entrySet();
			for (Map.Entry<String, Integer> entry : scores) {
				String name = entry.getKey().toUpperCase();
				Integer scoreValue = entry.getValue();
				writer.write(name + "|" + scoreValue);
				writer.newLine();
			}
			writer.flush();
		}
	}

	@Override
	public Integer getScorePlayer(Player player) {
		return playersScore.get(player.getName());
	}

	@Override
	public void showRanking() {
		Map<String, Integer> orderedScore = sortScore();
		Set<String> keys = orderedScore.keySet();
		if(keys.size() <= 0) {
			UI.message("Nenhuma pontuação registrada ainda!");
			return;
		}
		UI.message("==== RANKING DE VITÓRIAS ====");
		int contador = 1;
		for (String key : keys) {
			Integer score = playersScore.get(key);
			UI.message(contador + "° - " + key + ": " + score);
			contador += 1;
		}
		UI.newLine();
	}

	private Map<String, Integer> sortScore() {
		return playersScore.entrySet().stream().sorted((s1, s2) -> s2.getValue().compareTo(s1.getValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) ->  e1, LinkedHashMap::new));
	}
}
