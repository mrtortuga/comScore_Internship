// Thomas Le
// 06.29.15

package com.comScore.TokenizerTests.Methods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StrTokenizer {

	private static int mapIter = 0;

	private static String tokens = "";

	public static void main(List<String> rows, ArrayList<Integer> cols)
			throws IOException {

		int urlInput = 0;

		for (mapIter = 0; mapIter < 44; mapIter++) {

			if (!cols.contains(mapIter))
				continue;

			for (String readLine : rows) {
				processLine(readLine);
				urlInput++;
			}

		} // for

		TokenizerUtilities.setUrlInput(urlInput);

	} // main

	public static String processLine(String line) {
		StringTokenizer urlTokenizer = new StringTokenizer(line, "\t");

		for (int tokenIter = 0; tokenIter <= mapIter
				&& urlTokenizer.hasMoreTokens(); tokenIter++) {

			tokens = urlTokenizer.nextToken();
		}

		return tokens;
	}

} // class
