package com.github.jkdv.wts.engines;

import java.io.IOException;
import java.util.Random;

public abstract class WebEngine {
	
	public static String generateRandomIp() {
		Random random = new Random();
		String ip = "";
		for (int i = 0; i < 4; i++) {
			ip += String.valueOf(random.nextInt(255));
			if (i < 3)
				ip += ".";
		}
		return ip;
	}
	
	public abstract String getVoiceFilePath(String word) throws IOException;
	
	public abstract void play(String word);
}
