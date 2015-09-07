package com.comScore.TokenizerTests.Tokenizer;

import java.util.List;

import com.comScore.TokenizerTests.Maps.GoogleMultiset;
import com.comScore.TokenizerTests.Maps.StringToIntMap;
import com.comScore.TokenizerTests.Maps.TraditionalHashMap;
import com.comScore.TokenizerTests.Maps.Trove;
import com.comScore.TokenizerTests.Methods.TokenizerUtilities;

public class MapsTest {
	public static void main(String[] args) throws Exception {
		
		String fullFile = "C:/Users/tle/Documents/NEWDUMMYDATA_100K.txt";
		
		StringToIntMap hashtoWebIdMap = new StringToIntMap();
		// Reads file into ArrayList to avoid potential slow-downs
		List<String> lines = TokenizerUtilities.fileToList(fullFile);

		String mapName = "stringtoint".toUpperCase();
		TokenizerUtilities.startTimeAndMemory();

		int index = 0;
		for (int i = 0; i < 3; i++) {
			switch (mapName) {
			case "TRADITIONAL":
				TraditionalHashMap.hashMapMethod(lines);
				break;
			case "TROVE":
				Trove.TroveMethod(lines);
				break;
			case "MULTISET":
				GoogleMultiset.MultisetMethod(lines);
				break;
			case "STRINGTOINT":
				hashtoWebIdMap.LoadHashMap(lines);
				break;
			default:
				System.out.println("Please enter valid command.");
				break;
			}

			TokenizerUtilities.startSecondTime();
			switch (mapName) {
			case "TRADITIONAL":
				TraditionalHashMap.loadRandomMapEntries();
				break;
			case "TROVE":
				Trove.loadRandomMapEntries();
				break;
			case "MULTISET":
				GoogleMultiset.loadRandomMapEntries();
				break;
			default:
				break;
			}

			// Puts each Time and Memory result into an outer array
			// to have the average of all iterations calculated
			TokenizerUtilities.collectAverages(i, TokenizerUtilities
					.endTimeAndMemory(TokenizerUtilities.getUrlInput(),
							TokenizerUtilities.getLoadCount()));
			index = i + 1;
		}

		TokenizerUtilities.averageResults(index, mapName);

		System.out.println("Done.");
	}
}
