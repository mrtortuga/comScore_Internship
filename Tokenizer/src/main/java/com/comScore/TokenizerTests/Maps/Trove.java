package com.comScore.TokenizerTests.Maps;

import java.io.IOException;
import java.util.List;
import com.comScore.TokenizerTests.Methods.TokenizerUtilities;

import gnu.trove.TObjectIntHashMap;

public class Trove {
	
	public static TObjectIntHashMap<String> lines = new TObjectIntHashMap<String>();
	
	public static void TroveMethod(List<String> fileInArrayList)
			throws IOException {
		TObjectIntHashMap<String> lines = readToTrove(fileInArrayList);
		TokenizerUtilities.writeOutTrove(lines);
		
	}

	public static TObjectIntHashMap<String> readToTrove(
			List<String> fileInArrayList) {

		int lineCount = 0;

		for (int i = 0; i < fileInArrayList.size(); i++) {

			lines.adjustOrPutValue(fileInArrayList.get(i), 1, 1);
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
			if (i % 1000 == 0)
				System.out.println(randomKey);
		}

		TokenizerUtilities.setLoadCount(loadCount);
	}
}
