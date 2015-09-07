package com.comScore.TokenizerTests.Methods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IndexOf {
	
	private static int mapIter = 0;
	private static int urlInput = 0;
	private static int indexStart = 0;
	private static int indexEnd = 0;
	
	private static String parsedUrl = "";

	public static void main(List<String> rows, ArrayList<Integer> cols)
			throws IOException {

		for (mapIter = 0; mapIter < 44; mapIter++) {

			indexStart = 0;
			indexEnd = 0;

			if (!cols.contains(mapIter))
				continue;

			for (String readLine : rows) {
				processLine(readLine);
				urlInput++;
			} // while

		} // for

		TokenizerUtilities.setUrlInput(urlInput);

	} // main
	
	public static String processLine(String line) {
		
		
		if (mapIter <= 42) {

			// Splits the line into tokens with "\t"
			indexStart = TokenizerUtilities.nIndexOf(line, "\t",
					mapIter);
			indexEnd = TokenizerUtilities.nIndexOf(line, "\t",
					mapIter + 1);

			parsedUrl = line.substring(indexStart + 1, indexEnd);

		}

		else if (mapIter <= 43) {
			// Splits the line into tokens with "\t"
			indexStart = TokenizerUtilities.nIndexOf(line, "\t",
					mapIter);

			parsedUrl = line.substring(indexStart + 1);

		}
		
		return parsedUrl;
	}
} // class
