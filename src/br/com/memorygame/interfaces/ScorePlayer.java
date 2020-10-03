package br.com.memorygame.interfaces;

import java.io.IOException;
import java.util.Map;

import br.com.memorygame.core.Player;

public interface ScorePlayer {

	Map<String, Integer> getPlayersScore();
	void saveScore(Player player) throws IOException;
	Integer getScorePlayer(Player player);
	void showRanking();
}
