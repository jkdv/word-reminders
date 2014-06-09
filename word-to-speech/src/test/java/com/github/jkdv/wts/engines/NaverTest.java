package com.github.jkdv.wts.engines;

import java.io.IOException;
import java.net.UnknownHostException;

import javazoom.jl.decoder.JavaLayerException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NaverTest {

	private static Logger logger = LoggerFactory.getLogger(NaverTest.class);
	private Naver naver;
	
	@Before
	public void setup() {
		naver = new Naver();
	}

	@Test
	public void testGetHttpResponse() throws UnknownHostException, IOException {
		String filepath = naver.getHttpResponse("flood");
		Assert.assertNotNull(filepath);
	}
	
	@Test
	public void testGetVoiceFilePath() throws IOException {
		String filepath = naver.getVoiceFilePath("flood");
		logger.debug(filepath);
		Assert.assertNotNull(filepath);
	}
	
	@Test
	public void testPlay() throws JavaLayerException {
		naver.play("extraordinary");
	}

}
