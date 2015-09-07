package com.comScore.TokenizerTests.Maps;

import java.util.*;
import java.io.*;

import com.comScore.TokenizerTests.Methods.TokenizerUtilities;

public class TraditionalHashMap {

	private static Map<String, Integer> lines = new HashMap<String, Integer>();

	public static void hashMapMethod(List<String> fileInArrayList)
			throws IOException {

		Map<String, Integer> lines = readToHashMap(fileInArrayList);
		TokenizerUtilities.writeOutMap(lines);
		TokenizerUtilities.startSecondTime();
		loadRandomMapEntries();
	}

	public static Map<String, Integer> readToHashMap(
			List<String> fileInArrayList) {

		int lineCount = 0;
		
		for (String line : fileInArrayList) {
			String [] fields = line.split("\t", -1);
			
			if (lines.containsKey(fields[0])) {
				lines.put(fields[0],
						lines.get(fields[0]) + 1);
			} else {
				lines.put(fields[0], 1);
			}

			lineCount++;
		}

		TokenizerUtilities.setUrlInput(lineCount);
		return lines;
	}

	public static void loadRandomMapEntries() throws IOException {

		int loadCount = 100000;
		String shortFile = "C:/Users/tle/Documents/NEWDUMMYDATA_100K.txt";
		List<String> keyList = TokenizerUtilities.fileToList(shortFile);
		
		Integer randomKey = 0;

		for (int i = 0; i < loadCount; i++) {
			randomKey = lines.get(keyList.get(i));
			if (i % 900 == 0)
				System.out.println(randomKey);
		}

		TokenizerUtilities.setLoadCount(loadCount);
	}

}
