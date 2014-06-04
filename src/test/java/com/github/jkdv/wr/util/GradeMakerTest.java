package com.github.jkdv.wr.util;

import org.junit.Assert;
import org.junit.Test;

import com.github.jkdv.wr.util.GradeMaker;

public class GradeMakerTest {

	@Test
	public void test() {
		GradeMaker gm = new GradeMaker();
		Assert.assertEquals(gm.getNext(0), 1);
		Assert.assertEquals(gm.getNext(1), 7);
		Assert.assertEquals(gm.getNext(6), 7);
		Assert.assertEquals(gm.getNext(7), 30);
		Assert.assertEquals(gm.getNext(8), 30);
		Assert.assertEquals(gm.getNext(30), 90);
		Assert.assertEquals(gm.getNext(90), 90);
		Assert.assertEquals(gm.getNext(100), 90);
	}

}
