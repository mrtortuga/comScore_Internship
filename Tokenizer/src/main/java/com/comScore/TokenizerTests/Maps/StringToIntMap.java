package com.comScore.TokenizerTests.Maps;

import java.io.*;
import java.util.List;
import java.util.Map.Entry;

import com.comScore.TokenizerTests.Methods.TokenizerUtilities;
import com.comscore.collections.cds.map.String2IntMap;

public class StringToIntMap {

	private static String2IntMap hashToWebIdMap;

	public StringToIntMap() throws Exception {

	}

	public void LoadHashMap(List<String> fileInArrayList) {
		try {
			loadHashToPatternIdMap(fileInArrayList);
		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("Loaded the map");
	}

	private static void loadHashToPatternIdMap(List<String> fileInArrayList)
			throws Exception {
		hashToWebIdMap = new String2IntMap();
		int lineCount = 0;
		try {
			for (String line : fileInArrayList) {
				
				String[] fields = line.split("\t", -1);
				
				if (fields.length < 2) {
					continue;
				}
				
				if (hashToWebIdMap.containsKey(fields[0])) {
					hashToWebIdMap.put(fields[0],
							hashToWebIdMap.get(fields[0]) + 1);
				} else {
					hashToWebIdMap.put(fields[0], 1);
				}

				lineCount++;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Lines Loaded=" + lineCount);
		hashToWebIdMap.compact(String2IntMap.SearchMode.BINARY_SEARCH);
		TokenizerUtilities.setUrlInput(lineCount);
		System.out.println("Completed loading the map");
	}

	public static void writeOutS2IMap(String2IntMap lines) throws IOException {
		PrintWriter writer = new PrintWriter(
				"C:/Users/tle/Documents/S2I_TEST.txt");
		for (Entry<String, Integer> url : lines.entrySet()) {

			String urlString = url.getKey();
			Integer i = url.getValue();

			writer.printf("%-60s \t\t %d \n", urlString, i);
		} // for

		writer.close();
	}
	
	

	public void loadRandomMapEntries() throws IOException {

		int loadCount = 100000;
		String shortFile = "C:/Users/tle/Documents/NEWDUMMYDATA_100K.txt";
		List<String> keyList = TokenizerUtilities.fileToList(shortFile);

		Integer randomKey = 0;

		for (int i = 0; i < loadCount; i++) {
			randomKey = hashToWebIdMap.get(keyList.get(i));
			if (i % 900 == 0)
				System.out.println(randomKey);
		}

		TokenizerUtilities.setLoadCount(loadCount);

	}
}
