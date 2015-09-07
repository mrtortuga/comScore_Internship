package com.comScore.TokenizerTests.Methods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StringSplit {

	public static void main(List<String> rows,
			ArrayList<Integer> cols) throws IOException {

		int urlInput = 0;

		@SuppressWarnings("unused")
		String[] urlSplit = null;

		for (int mapIter = 0; mapIter < 44; mapIter++) {

			if (!cols.contains(mapIter))
				continue;

			for (String readLine : rows) {
				ProcessLine(readLine);
				urlInput++; 
			} // while

		} // for

		TokenizerUtilities.setUrlInput(urlInput);

	} // main
	
	public static String[] ProcessLine(String line)
	{
		String[] tokens = null;
		tokens = line.split("\t");
		return tokens;
	}
} // class
