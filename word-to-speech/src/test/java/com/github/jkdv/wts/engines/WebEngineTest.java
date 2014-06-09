package com.github.jkdv.wts.engines;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class WebEngineTest {

	public static void main(String[] args) throws UnknownHostException, IOException {
		String ip = WebEngine.generateRandomIp();
		
		Socket socket = new Socket("dic.naver.com", 80);
		System.out.println(socket.isConnected());

		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		
		System.out.println("SOCKET OPEN.");
		
		String word = "flood";
		StringBuilder packet = new StringBuilder();
		packet.append("GET /search.nhn?&query=" + word + " HTTP/1.1\n");
		packet.append("Host: dic.naver.com\n");
		packet.append("Ip: " + ip + "\n");
		packet.append("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:29.0) Gecko/20100101 Firefox/29.0\n");
		packet.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n");
		packet.append("Connection: keep-alive\n");
		packet.append("\n");
		
		System.out.println("WRITE.");
		
		out.writeBytes(packet.toString());
		
		System.out.println("READ.");
		
		String line;
		StringBuffer html = new StringBuffer();
		while ((line = reader.readLine()) != null) {
		    html.append(line);
		}
		
		System.out.println(html.toString());
		
		System.out.println("END.");
		
		socket.close();
	}

}
