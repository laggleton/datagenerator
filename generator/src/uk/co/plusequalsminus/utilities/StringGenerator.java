package uk.co.plusequalsminus.utilities;

import java.util.Random;

public class StringGenerator {
	
	private final static int alpha = 3;
	private final static int numeric = 6;
	
	private final static String alphabet = "ABCDEFGHJKLMNPQRSTUVWXYZ";
	
	public static String generateAlphaNumericString(int alpha, int numeric) {
		Random r = new Random();
		String s = "";
		for (int i = 0; i < alpha; i++) {
			s += alphabet.charAt(r.nextInt(alphabet.length()));
		}
		
		for (int i = 0; i < numeric; i++) {
			s+= r.nextInt(10);
		}
		return s;
	}
	
	public static String generateAlphaNumericString() {
		return generateAlphaNumericString(alpha, numeric);
	}
}
