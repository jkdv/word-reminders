package com.github.jkdv.wr.util;

import java.util.ArrayList;
import java.util.Iterator;

public class GradeMaker {
	
	private ArrayList<Integer> gradeList = new ArrayList<Integer>();
	
	public GradeMaker() {
		gradeList.add(1);
		gradeList.add(7);
		gradeList.add(30);
		gradeList.add(90);
	}
	
	public int getNext(int grade) {
		Iterator<Integer> it = gradeList.iterator();
		int next = 1;
		while (it.hasNext()) {
			next = it.next();
			if (grade < next)
				break;
		}
		return next;
	}
}
