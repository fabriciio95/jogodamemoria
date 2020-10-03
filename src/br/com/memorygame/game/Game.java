package br.com.memorygame.game;

import br.com.memorygame.cards.DeckAlphanumeric;
import br.com.memorygame.constants.Constants;
import br.com.memorygame.core.Board;
import br.com.memorygame.core.Configuration;
import br.com.memorygame.core.Move;
import br.com.memorygame.core.Player;
import br.com.memorygame.core.WinningScore;
import br.com.memorygame.exceptions.InvalidMoveException;
import br.com.memorygame.exceptions.RepeatedMoveException;
import br.com.memorygame.interfaces.ScorePlayer;
import br.com.memorygame.ui.UI;

public class Game {
	private Board board;
	private Configuration configuration;
	private ScorePlayer scorePlayer;

	public Game() {
		board = new Board(new DeckAlphanumeric());
		this.configuration = new Configuration(board);
		this.scorePlayer = new WinningScore();
	}

	public void play() {
		try {
			UI.message("Bem-vindo ao jogo da mem�ria");
			UI.newLine();
			int option = 0;
			while (true) {
				try {
					option = UI.showMenu("0-Configura��es\n1-Jogar\n2-Ranking");
					if (option == 0) {
						configuration.makeSettings();
						continue;
					} else if(option == 2) {
						scorePlayer.showRanking();
						continue;
					}
				} catch (Exception e) {
					UI.message(e.getMessage());
					continue;
				}
				if (option == 1) {
					String namePlayer = UI.getString("Qual o nome do jogador: ");
					Player player = new Player(namePlayer.toUpperCase());
					UI.message("Ol� " + player.getName() + " voc� vai jogar com o baralho " + board.getDeck().getName());
					UI.newLine();
					int attempts = configuration.getAttempts();
					boolean isWithoutAttempt = true;
					if (attempts != Constants.MAX_ATTEMPTS && attempts > 0 || attempts == Constants.MAX_ATTEMPTS) {
						UI.message("Voc� pode errar " + attempts + " vez(es)");
						UI.newLine();
					} else {
						UI.message("Voc� pode errar quantas vezes precisar");
						UI.newLine();
						isWithoutAttempt = false;
					}
					UI.message("LEGAL!! VAMOS COME�AR A JOGAR!! Veja os pares abaixo e memorize-os se puder!!!");
					UI.newLine();
					board.printBoardUncovered();
					Thread.sleep(5 * 1000);
					Move move1 = null;
					Move move2 = null;
					int errors = 0;
					while (true) {
						try {
							board.printBoardCovered();
							move1 = UI.makeMove("Diga a posi��o de uma carta: ");
							board.makeMove(move1);
							board.printBoardCovered();
							move2 = UI.makeMove("Diga a posi��o da outra carta do par: ");
							board.makeMove(move2);
						} catch (RepeatedMoveException | InvalidMoveException e) {
							UI.message(e.getMessage());
							if (!board.searchPairOnBoardCovered(move1)) {
								board.undoMove(move1);
							}
							if (!board.searchPairOnBoardCovered(move2)) {
								board.undoMove(move2);
							}
							continue;
						}
						board.printBoardCovered();
						if (board.isRightMove(move1, move2)) {
							UI.message("Na mosca! Voc� acertou este par!");
						} else {
							UI.message("Que pena! N�o foi dessa vez");
							errors += 1;
							if (isWithoutAttempt && errors == attempts) {
								UI.message(
										"AAAAAH :( ACABOU SUAS CHANCES E VOC� PERDEU O JOGO!!!! Veja abaixo seus pares acertados e depois todas as posi��es dos pares");
								UI.newLine();
								board.printBoardCovered();
								UI.newLine();
								board.printBoardUncovered();
								UI.newLine();
								break;
							} else {
								UI.message("Voc� ainda pode errar " + attempts + " vez(es)");
								UI.newLine();
								board.printBoardCovered();
							}
						}

						if (board.isAllPairsFound()) {
							scorePlayer.saveScore(player);
							UI.message("PARAB�NS!!! Voc� acertou todos os pares, est� com a mem�ria �tima e j� possui " + scorePlayer.getScorePlayer(player) + " vit�ria(s) no ranking!");
							board.printBoardCovered();
							break;
						}
					}
				}

				int optionGame = UI.showMenu("Deseja jogar mais uma vez? 0-SIM 1-N�O");
				if (optionGame != 0) {
					break;
				} else {
					board.hideCardsOnTheBoardCovered();
					board.resetBoardUncovored();
					board.putCardsOnTheBoardUncovered();
				}
			}
		} catch (Exception e) {
			UI.message("ERRO! O jogo foi encerrado, porque: " + e.getMessage());
		}
	}
}
