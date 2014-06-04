package com.github.jkdv.wr.util;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.jkdv.wr.util.ExcelUtils;
import com.github.jkdv.wr.util.GradeMaker;

public class ExcelUtilsTest {

	ExcelUtils utils;

	@Before
	public void setUp() throws Exception {
		utils = ExcelUtils.getInstance();
		utils.openExcel();
	}

	@After
	public void tearDown() throws Exception {
		utils.closeExcel();
	}
	
	@Test
	public void testSizeOfRows() {
		Assert.assertEquals(utils.getSize(), 2);
	}

	@Test
	public void testUpdateWord() throws IOException, URISyntaxException {
		GradeMaker gm = new GradeMaker();
		Row row = utils.getRow(0);
		Cell cell = row.getCell(ExcelUtils.COL_GRADE);
		int grade = gm.getNext((int) cell.getNumericCellValue());
		utils.updateWord(row, grade);
	}

}
