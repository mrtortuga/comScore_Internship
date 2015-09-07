// Thomas Le
// 06.29.15
// Main Tokenizer method

package com.comScore.TokenizerTests.Tokenizer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.openjdk.jmh.runner.RunnerException;

import com.comScore.TokenizerTests.Methods.*;

public class TokenizerTest {

	public static void main(String[] args) throws IOException, RunnerException {
		
		String filePath = "C:/Users/tle/Documents/ad_classifications.txt";

		List<String> dataRows = TokenizerUtilities.fileToList(filePath);
		
		String methodSelect = args[0];
		String columnSelect = args[1];

		String methodName = "";

		int divisor = 1;

		switch (columnSelect) {

		case "even":
			divisor = 2;
			break;

		case "quarter":
			divisor = 4;
			break;

		case "all":
			divisor = 1;
			break;
			
		default:
			divisor = 4;
			break;
		}

		ArrayList<Integer> cols = new ArrayList<Integer>();

		for (int index = 0; index < 44; index++) {

			if (index % divisor == 0)
				cols.add(index);
		}

		TokenizerUtilities.startTimeAndMemory();

		switch (methodSelect) {

		case "split":
			StringSplit.main(dataRows, cols);
			methodName = "StringSplit";
			break;

		case "indexOf":
			IndexOf.main(dataRows, cols);
			methodName = "IndexOf";
			break;

		case "guavaSplitter":
			GuavaSplitter.main(dataRows, cols);
			methodName = "GuavaSplitter";
			break;

		case "StringTokenizer":
			StrTokenizer.main(dataRows, cols);
			methodName = "StringTokenizer";
			break;

		case "ByteParser":
			ByteParser.main(dataRows, cols);
			methodName = "ByteParser";
			break;

		default:
			System.out.println("Please enter valid argument(s).");

		} // switch
		
		TokenizerUtilities.outputResults(TokenizerUtilities
				.endTimeAndMemory(TokenizerUtilities.getUrlInput(), TokenizerUtilities.getLoadCount()),
				methodName, columnSelect);
	} // main

}
