/**
 * File:        ClassifierInputReader.java
 * Author:      Jack Weaver <jhweaver@usc.edu>
 * Target:      aludra.usc.edu running Java 1.6.0_23
 */
package com.weaverworx.usc.csci561.assignment3.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Strictly for handling input files to the Classifier systems.  In this system,
 * some knowledge about the input file locations and types are known prior to
 * run time, so these locations cannot change:
 * <ul>
 * 	<li>training_data/</li>
 * 	<li>test_data/</li>
 * </ul>
 * 
 * The intent of this class is to contain the logic necessary for parsing the 
 * input files, and has two main static classes that can parse the data:
 * <ul>
 * 	<li>getTrainingData</li>
 * 	<li>getTestData</li>
 * </ul>
 * 
 * @author jw
 * 
 * TODO: Refactor getTrainingData and getTestData
 * TODO: Remove hard paths
 * 
 */
public class ClassifierInputReader {
	private static final String TRAINING_DATA_PATH = "training_data/";
	private static final String TESTING_DATA_PATH = "test_data/";

	private ClassifierInputReader() {	}

	
	/**
	 * Retrieves training data from the input file locations.
	 * 
	 * @param numberOfFeatures is the number of "features" per class.  With
	 * default data, this might be 784 (28 * 28).
	 * @param numberOfClasses is the number of classes per file, in the case of
	 * digit recognition, there are 10 digits thus 10 classes.
	 * @return a 2 dimension array representing the grayscale values of the
	 * input file, each row being a number and each number in the row being a
	 * value from 0-255.
	 */
	public static int[][] getTrainingData(int numberOfFeatures, int numberOfClasses) {
		ArrayList<String> tem = new ArrayList<String>();

		for (int i = 0; i < numberOfClasses; i++) {
			try {

				String filename = TRAINING_DATA_PATH + "train" + i + ".txt";

				FileInputStream fstream = new FileInputStream(filename);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));
				String strLine;

				// Read File Line By Line with their class
				while ((strLine = br.readLine()) != null) {
					tem.add(strLine + "," + i);
				}
				// Close the input stream
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		int[][] train = new int[tem.size()][numberOfFeatures + 1];

		for (int i = 0; i < tem.size(); i++) {
			String[] line = tem.get(i).split(",");

			for (int j = 0; j < line.length; j++) {
				train[i][j] = Integer.parseInt(line[j]);
			}

		}
		return train;
	}


	/**
	 * 
	 * @param numberOfFeatures
	 * @param numberOfClasses
	 * @return
	 */
	public static int[][] getTestData(int numberOfFeatures, int numberOfClasses) {
		ArrayList<String> tem = new ArrayList<String>();

		for (int i = 0; i < numberOfClasses; i++) {
			try {

				String filename = TESTING_DATA_PATH + "test" + i + ".txt";

				FileInputStream fstream = new FileInputStream(filename);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));
				String strLine;

				// Read File Line By Line with their class
				while ((strLine = br.readLine()) != null) {
					tem.add(strLine + "," + i);

				}

				// Close the input stream
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		int[][] test = new int[tem.size()][numberOfFeatures + 1];

		for (int i = 0; i < tem.size(); i++) {
			String[] line = tem.get(i).split(",");

			for (int j = 0; j < line.length; j++) {
				test[i][j] = Integer.parseInt(line[j]);
			}

		}
		return test;
	}
}
