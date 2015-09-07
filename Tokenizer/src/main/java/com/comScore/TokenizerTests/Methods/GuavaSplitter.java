package com.comScore.TokenizerTests.Methods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Splitter;

public class GuavaSplitter {

	private static String dummyUrl = "";
	private static Splitter split = null;
	private static int mapIter = 0;
	
	public static void main(List<String> rows, ArrayList<Integer> cols)
			throws IOException {
		
		@SuppressWarnings("unused")
		String parsedUrl = "";

		int urlInput = 0;

		

		// Make "mapIter < columnNum" in future implementation
		for (mapIter = 0; mapIter < 44; mapIter++) {

			if (!cols.contains(mapIter))
				continue;
			
			for (String readLine : rows) {
				processLine(readLine);
				urlInput++; // keeps track of total number of rows

			} // while
		} // for
		TokenizerUtilities.setUrlInput(urlInput);
	} // main
	
	public static String processLine(String line) {
		
		split = Splitter.on("\t");
		
		Iterator<String> it = split.split(line).iterator();
		// for loop with indexof code to run next mapiter amount

		for (int i = -1; i < mapIter; i++) {
			dummyUrl = it.next();
		}
		
		return dummyUrl;
	}
} // class