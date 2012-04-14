/**
 * File:        Launcher.java
 * Author:      Jack Weaver <jhweaver@usc.edu>, <weaver.jack@gmail.com>
 * Course:      CSCI 561, Spring 2012
 * Assignment:  Assignment 3 - Supervised Learning Systems
 * Target:      aludra.usc.edu running Java 1.6.0_23
 */
package com.weaverworx.usc.csci561.assignment3;

import com.weaverworx.usc.csci561.assignment3.knn.KNNRecord;
import com.weaverworx.usc.csci561.assignment3.util.FileReader;
import com.weaverworx.usc.csci561.assignment3.util.KNNUtil;

/**
 * Main class to launch the application.
 * 
 * @author jw
 *
 */
public class Launcher {
	private final static int NUMBER_OF_CLASSES = 10;
	private final static int NUMBER_OF_FEATURES = 784;
	private final static int EX_CLASS_INDEX = 784;
	
	/**
	 * Entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int k = KNNUtil.parseKArgument(args);	//Read k from user input
		//Get the training data and test data
		int[][] trainData = FileReader.getTrainingData(
				NUMBER_OF_FEATURES, NUMBER_OF_CLASSES);
		int[][] testData = FileReader.getTestData(
				NUMBER_OF_FEATURES, NUMBER_OF_CLASSES);
		
		//Set up the K-Nearest Neighbor Records
		KNNRecord[] knnRecords = new KNNRecord[trainData.length];
		for (int i = 0; i < trainData.length; i++) {
			knnRecords[i] = new KNNRecord();
		}
		
		//Set up the arrays for # correct and # incorrect
		int[] correct, incorrect = new int[NUMBER_OF_CLASSES];
		for (int i = 0; i < testData.length; i++) {
			for (int j = 0; j < trainData.length; j++) {
				//Set the distance & the record class
				knnRecords[j].setExampleClass(trainData[j][EX_CLASS_INDEX]);
				knnRecords[j].setDistance(KNNUtil.getEuclideanDistance(
						testData[i], trainData[j]));
			}
			
		}
		
		
	}
}
