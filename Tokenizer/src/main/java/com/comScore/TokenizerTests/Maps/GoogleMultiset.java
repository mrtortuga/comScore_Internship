package com.comScore.TokenizerTests.Maps;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.comScore.TokenizerTests.Methods.TokenizerUtilities;
import com.google.common.collect.*;
import com.google.common.collect.Multiset.Entry;

public class GoogleMultiset {
	
	public static Multiset<String> lines = HashMultiset.create();
	
	public static void MultisetMethod(List<String> fileInArrayList)
			throws IOException {

		Multiset<String> lines = readToMultiset(fileInArrayList);
		writeOutMultiset(lines);
	}

	public static Multiset<String> readToMultiset(
			List<String> fileInArrayList) {

		int lineCount = 0;

		for (int i = 0; i < fileInArrayList.size(); i++) {
			lines.add(fileInArrayList.get(i));
			lineCount++;
		}

		TokenizerUtilities.setUrlInput(lineCount);
		return lines;
	}

	public static void writeOutMultiset(Multiset<String> lines)
			throws IOException {
		PrintWriter writer = new PrintWriter(
				"C:/Users/tle/Documents/MULTISET_TEST.txt");
		for (Entry<String> entries : lines.entrySet()) {
			writer.println(entries);
		}

		Multisets.copyHighestCountFirst(lines);
		writer.close();
	}
	
//	public static void loadRandomMapEntries() {
//		Random rnd = new Random(lines.size());
//
//		int loadCount = 10000;
//		
//		Object[] keyArray = lines.toArray();
//		
//		String randomKey = "";
//
//		for (int i = 0; i < loadCount; i++) {
//			randomKey = (String) keyArray[rnd.nextInt(lines.size())];
//			if (i % 1000 == 0)
//				System.out.println(randomKey);
//		}
//
//		TokenizerUtilities.setLoadCount(loadCount);
//	}
	
	public static void loadRandomMapEntries() throws IOException {

		int loadCount = 100000;
		String shortFile = "C:/Users/tle/Documents/NEWDUMMYDATA_100K.txt";
		List<String> keyList = TokenizerUtilities.fileToList(shortFile);
		
		Integer randomKey = 0;
		
		for (int i = 0; i < loadCount; i++) {
			randomKey = lines.count(keyList.get(i));
			if (i % 1000 == 0)
				System.out.println(randomKey);
		}

		TokenizerUtilities.setLoadCount(loadCount);
	}
}
