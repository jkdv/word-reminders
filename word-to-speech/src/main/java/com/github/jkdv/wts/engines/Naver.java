package com.github.jkdv.wts.engines;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Naver extends WebEngine {

	private static String HOST_NAME = "dic.naver.com";
	private static int PORT = 80;
	private static final String regex = "(http://)[a-zA-Z0-9./?=]*(/us/)[a-zA-Z0-9./?=]*";
	private final Pattern pattern = Pattern.compile(regex);

	public String getHttpResponse(String word) throws UnknownHostException, IOException {
		String ip = generateRandomIp();
		Socket socket = new Socket(HOST_NAME, PORT);
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		StringBuilder packet = new StringBuilder();
		packet.append("GET /search.nhn?&query=" + word + " HTTP/1.1\n");
		packet.append("Host: " + HOST_NAME + "\n");
		packet.append("Ip: " + ip + "\n");
		packet.append("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:29.0) Gecko/20100101 Firefox/29.0\n");
		packet.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n");
		packet.append("Connection: keep-alive\n");
		packet.append("\n");
		out.writeBytes(packet.toString());

		String line;
		StringBuffer html = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			html.append(line);
			html.append("\n");
		}
		socket.close();
		return html.toString();
	}
	
	public String getVoiceFilePath(String word) throws IOException {
		String response = getHttpResponse(word);
		Matcher m = pattern.matcher(response);
		if (m.find()) {
			return m.group();
		}
		return null;
	}
	
	public void play(String word) {
		try {
			URL url = new URL(getVoiceFilePath(word));
			InputStream stream = url.openStream();
			Player player = new Player(stream);
			player.play();
		} catch (IOException | JavaLayerException e) {
			e.printStackTrace();
		}
	}

}
