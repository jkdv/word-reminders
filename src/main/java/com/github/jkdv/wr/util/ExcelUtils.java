package com.github.jkdv.wr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	private static ExcelUtils instance;
	private FileInputStream input;
	private FileOutputStream output;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	public static String FILE_NAME = "Words.xlsx";
	public static int COL_WORD = 0;
	public static int COL_MEANING = 1;
	public static int COL_URL = 2;
	public static int COL_GRADE = 3;
	public static int COL_REG_DATE = 4;
	public static int COL_TEST_DATE = 5;
	public static int COL_NEXT_DATE = 6;
	
	private ArrayList<Row> todayRows = new ArrayList<Row>();
	
	private ExcelUtils() {
	}
	
	public static ExcelUtils getInstance() {
		if (instance == null)
			instance = new ExcelUtils();
		return instance;
	}

	public void openExcel() throws IOException, URISyntaxException {
		input = new FileInputStream(new File(FILE_NAME));
		workbook = new XSSFWorkbook(input);
		sheet = workbook.getSheetAt(0);

		//Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			
			//Get iterator to all cells of current row
			Cell cell = row.getCell(COL_NEXT_DATE);
			Date date = new Date();
			if (cell.getDateCellValue().compareTo(date) <= 0) {
				todayRows.add(row);
			}
		}
		input.close();
	}
	
	public int getSize() {
		return todayRows.size();
	}
	
	public Iterator<Row> iterator() {
		return todayRows.iterator();
	}
	
	public Row getRow(int rownum) {
		return todayRows.get(rownum);
	}
	
	public void updateWord(Row row, int grade) throws IOException, URISyntaxException {
		output = new FileOutputStream(new File(FILE_NAME));
		row.getCell(COL_GRADE).setCellValue(grade);
		row.getCell(COL_TEST_DATE).setCellValue(new Date());
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DATE, grade);
		row.getCell(COL_NEXT_DATE).setCellValue(calendar.getTime());
		workbook.write(output);
		output.close();
	}
	
	public void closeExcel() throws IOException {
		todayRows.clear();
	}
}
