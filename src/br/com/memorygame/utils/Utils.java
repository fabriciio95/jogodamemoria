package br.com.memorygame.utils;

import java.util.Random;

public class Utils {

	private static Random random = new Random();
	
	public static int newRandomValue(int max) {
		return random.nextInt(max);
	}
}
