/**
 * File:        FileReader.java
 * Author:      Jack Weaver <jhweaver@usc.edu>
 * Course:      CSCI 561, Spring 2012
 * Assignment:  Assignment 3 - Supervised Learning Systems
 * Target:      aludra.usc.edu running Java 1.6.0_23
 */
package com.weaverworx.usc.csci561.assignment3.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author jw
 * 
 */
public class FileReader {
	private static final String TRAINING_DATA_PATH = "training_data/";
	private static final String TESTING_DATA_PATH = "test_data/";

	private FileReader() {	}

	//TODO: Refactor getTrainingData and getTestData
	public static int[][] getTrainingData(int numberOfFeatures,
			int numberOfClasses) {
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
			} catch (Exception e) {// Catch exception if any
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
			} catch (Exception e) {// Catch exception if any
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
