package com.comScore.TokenizerTests.Methods;

import gnu.trove.TObjectIntHashMap;
import gnu.trove.TObjectIntIterator;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TokenizerUtilities {

	// Arrays used to calculate averages
	private static long memoryArray[] = new long[3];
	private static long time[] = new long[3];
	private static long efficiency[] = new long[3];
	private static long secondTime[] = new long[3];
	private static long outputArray[] = new long[5];

	private static long startTime = 0;
	private static long secondStart = 0;
	private static Runtime runtime = Runtime.getRuntime();
	private static int urlCount = 0;
	private static int loadCount = 0;

	public static void writeOutTrove(TObjectIntHashMap<String> lines)
			throws IOException {
		PrintWriter writer = new PrintWriter(
				"C:/Users/tle/Documents/TROVE_TEST.txt");
		for (TObjectIntIterator<String> it = lines.iterator(); it.hasNext();) {
			it.advance();

			writer.printf("%-60s \t\t %d \n", it.key(), it.value());
		}

		writer.close();
	}

	public static void writeOutMap(Map<String, Integer> lines)
			throws IOException {

		PrintWriter writer = new PrintWriter(
				"C:/Users/tle/Documents/MAPS_TEST.txt");
		for (Entry<String, Integer> url : lines.entrySet()) {

			String urlString = url.getKey();
			Integer i = url.getValue();

			writer.printf("%-60s \t\t %d \n", urlString, i);
		} // for

		writer.close();
	}

	
	public static void splitFile(File f) throws IOException {
        int partCounter = 1;
                            

        int sizeOfFiles = (1024 * 1024) * 5; // 5 MBs per file, roughly 100,000 lines
        byte[] buffer = new byte[sizeOfFiles];

        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(f))) {
            String name = f.getName();

            int tmp = 0;
            while ((tmp = bis.read(buffer)) > 0) {
                //write each chunk of data into separate file with different number in name
                File newFile = new File(f.getParent(), name + "."
                        + String.format("%03d", partCounter++));
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, tmp);//tmp is chunk size
                }
            }
        }
    }
	
	// Reads file to ArrayList in order to prevent any system slow-downs
	public static List<String> fileToList(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream fileReader = new FileInputStream(file);

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(fileReader));

		fileReader.getChannel().position(0);
		bufferedReader = new BufferedReader(new InputStreamReader(fileReader));

		String line = "";
		List<String> dataRows = new ArrayList<String>();

		while ((line = bufferedReader.readLine()) != null) {
			String[] firstColumnArray = line.split("\t", -1);
			dataRows.add(firstColumnArray[0]);

		} // while

		fileReader.close();
		return dataRows;
	}
	
	public static void averageResults(int loopCount, String mapName)
			throws IOException {
		// output info to txt file

		long avgMemory = 0;
		long avgDuration = 0;
		int avgEfficiency = 0;
		long avgLoad = 0;

		PrintWriter writer = writerCreator();

		for (long inputs : efficiency)
			avgEfficiency += inputs;

		avgEfficiency /= loopCount;

		for (long mem : memoryArray)
			avgMemory += mem;

		avgMemory /= loopCount;

		for (long seconds : time)
			avgDuration += seconds;

		avgDuration /= loopCount;

		for (long loadTime : secondTime)
			avgLoad += loadTime;

		avgLoad /= loopCount;

		writer.println(mapName.toUpperCase() + " (Out of " + loopCount
				+ " trials)");
		writer.println("Number of rows processed: " + (avgEfficiency));
		writer.println("Average Duration: " + (avgDuration / 1000000000.0)
				+ " seconds");
		writer.println("Average Read efficiency : "
				+ ((avgEfficiency) / (avgDuration / 1000000000.0))
				+ " rows per second");
		writer.println("Average Load/Seek Time (Out of 100,000): " + (avgLoad / 1000000000.0)
				+ " seconds");
		writer.println("Average Load/Seel efficiency (Out of 100,000): "
				+ ((getLoadCount()) / (avgLoad / 1000000000.0))
				+ " rows per second");
		writer.println("Average memory used: " + (avgMemory / 1000000.0)
				+ " MBs\n");

		writer.close();

	}
	
	

	// Iterates to the proper amount for TokenizerMethods.indexOf()
	public static int nIndexOf(final String line, final String delimiter,
			final int index) {
		int j = 0;

		for (int i = 0; i < index; i++) {
			j = line.indexOf(delimiter, j + 1);

			if (j == -1)
				break;
		}

		return j;
	}

	public static void startTimeAndMemory() {
		startTime = System.nanoTime();
		runtime = Runtime.getRuntime();
	}

	public static void startSecondTime() {
		secondStart = System.nanoTime();
	}

	public static long[] endTimeAndMemory(int urlInput, int loadIter) {
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		long secondDuration = (endTime - secondStart);

		runtime.gc();
		long memory = runtime.totalMemory() - runtime.freeMemory();

		outputArray[0] = duration;
		outputArray[1] = memory;
		outputArray[2] = Long.valueOf(urlInput);
		outputArray[3] = secondDuration;
		outputArray[4] = Long.valueOf(loadIter);

		return outputArray;
	}

	// Puts results from possible iterations into a single array to calculate
	// average
	public static void collectAverages(int index, long[] outputArray) {
		time[index] = outputArray[0];
		memoryArray[index] = outputArray[1];
		efficiency[index] = Long.valueOf(outputArray[2]);
		secondTime[index] = outputArray[3];
	}

	// Creates new file if it doesn't exist
	// Otherwise, just appends to existing filePath
	public static PrintWriter writerCreator() throws IOException {

		String filePath = "C:/Users/tle/Documents/DS_results3.txt";

		File f = new File(filePath);
		if (!f.exists() || f.isDirectory()) {
			@SuppressWarnings({ "unused", "resource" })
			PrintWriter writer = new PrintWriter(filePath);
		}

		// converts PrintWriter to FileWriter to append rather than overwrite
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				filePath, true)));

		return writer;
	}

	// Used to print if only one iteration
	public static void outputResults(long[] outputArray, String methodName,
			String columnSelect) throws IOException {

		PrintWriter writer = writerCreator();

		writer.println("\n" + methodName + " Method ("
				+ columnSelect.toUpperCase() + "): ");
		writer.println("Number of rows processed: " + (outputArray[2]));
		writer.println("Total Duration: " + (outputArray[0] / 1000000000.0)
				+ " seconds");
		writer.println("Efficiency: " + (outputArray[2])
				/ (outputArray[0] / 1000000000.0) + " rows per second");
		writer.println("Memory used: " + (outputArray[1] / 1000000.0) + " MBs");

		writer.close();
	}

	public static void setUrlInput(int urlInput) {
		urlCount = urlInput;
	}

	public static int getUrlInput() {
		return urlCount;
	}

	public static void setLoadCount(int loadIter) {
		loadCount = loadIter;
	}

	public static int getLoadCount() {
		return loadCount;
	}

	// Cuts large data file into smaller, more manageable file
	public static void smallBytes(FileInputStream fileReader)
			throws IOException {

		int amountOfLines = 2000000;

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(fileReader));

		PrintWriter writer = new PrintWriter(
				"C:/Users/tle/Documents/NEWDUMMYDATA_2M.txt");

		for (int i = 0; i < amountOfLines; i++) {
			writer.println(bufferedReader.readLine());
		}

		bufferedReader.close();
		writer.close();
	}

} // class

