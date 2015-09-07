package com.comScore.TokenizerTests.Methods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.comScore.ByteParserUtil.*;

public class ByteParser {

	private static String parsedUrl = "";
	private static int mapIter = 0;

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
		RowParser rowParser = RowParserBuilder.newBuilder()
				.forParsing(ParsingData.BYTES).withDelim('\t').withColumns(44)
				.build();
		rowParser.parse(line.getBytes(), line.getBytes().length);
		rowParser.parse(line.getBytes(), line.getBytes().length);

		parsedUrl = rowParser.getStringToken(mapIter);

		return parsedUrl;
	}

} // class
