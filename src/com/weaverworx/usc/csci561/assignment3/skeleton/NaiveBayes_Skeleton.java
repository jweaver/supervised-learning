/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weaverworx.usc.csci561.assignment3.skeleton;

import java.io.*;
import java.util.*;

import com.weaverworx.usc.csci561.assignment3.util.FileReader;
import com.weaverworx.usc.csci561.assignment3.util.LearningUtil;

/**
 * 
 * @author femto
 */
public class NaiveBayes_Skeleton {


	final static int numberOfClasses = 10;
	final static int numberOfFeatures = 784;
	final static int exampleClassIndex = 784;
	
	private static double[] N_Y = new double[numberOfClasses];
	private static double[] P_Y = new double[numberOfClasses];
	

	public static void main(String args[]) {

		int threshold = LearningUtil.parseKArgument(args); // read threshold

		int[][] trainingData = FileReader.getTrainingData(numberOfFeatures, numberOfClasses); // last column 785 is a class of train
											// image
		int[][] testData = FileReader.getTestData(numberOfFeatures, numberOfClasses); // last column 785 is a class of test
										// image
		
		//Setup N_Y: the number of times a given training data appears amongst ALL the training data.
		//eg - number of 1's in all training data: N_Y[1]
		int totalTrainingSize = 0;
		for (int i = 0; i < numberOfClasses; i++) {

			for (int j = 0; j < trainingData.length; j++) {
				//For each "record" in all test data
				//
				if (trainingData[j][trainingData[j].length -1] == i) {
					N_Y[i] = N_Y[i] + 1;
					totalTrainingSize++;
				}
			}
			if (i == numberOfClasses - 1) {
				for (int k = 0; k < P_Y.length; k++) {
					P_Y[k] = N_Y[k] / totalTrainingSize;
				}
			}
		}

//		double[] P_Y = getClassProbabilities(train); // Learn P(Y) parameters
		double[][][] P_X_given_Y = getConditionalProbabilities(threshold, trainingData); // Learn
																					// P(X|Y)
																					// parameters
																					// threshold
																					// used
																					// for
																					// binarized

		int[] correct = new int[numberOfClasses];
		int[] incorrect = new int[numberOfClasses];

		for (int i = 0; i < testData.length; i++) { // for each test example

			int actual_class = testData[i][exampleClassIndex];
			int predict_class = predict(testData[i], threshold, P_Y, P_X_given_Y);// predict
																				// by
																				// using
																				// P_Y
																				// and
																				// P_X_given_Y
																				// parameters
			// threshold used for binarized
			if (actual_class == predict_class) {
				correct[actual_class]++; // if actual_class same as
											// predict_class, increasing correct
											// of that class
											// (correct[actual_class])
			} else {
				incorrect[actual_class]++;
			}

		}

		// display output

		System.out.println("Threshold = " + threshold);

		for (int i = 0; i < 10; i++) {
			double accuracy = (correct[i] * 1.0) / (correct[i] + incorrect[i]);
			System.out.println("Class = " + i + " Correct = " + correct[i]
					+ " Incorrect = " + incorrect[i] + " Accuracy = "
					+ accuracy);
		}

	}

	static int readUserInput() {
		return 100;
	}

	static int[][] readTrainFile() {

		ArrayList<String> tem = new ArrayList<String>();

		for (int i = 0; i < numberOfClasses; i++) {
			try {

				String filename = "train" + i + ".txt";

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

	static int[][] readTestFile() {

		ArrayList<String> tem = new ArrayList<String>();

		for (int i = 0; i < numberOfClasses; i++) {
			try {

				String filename = "test" + i + ".txt";

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

	static double[] getClassProbabilities(int[][] train) {

		double[] P_Y = new double[numberOfClasses];

		return P_Y;
	}

	static double[][][] getConditionalProbabilities(int threshold, int[][] trainingData) {
		//Convert training data to 'binary training data'
		int[][] binaryTrainingData = new int[trainingData.length][trainingData[trainingData.length - 1].length];
		for (int i = 0; i < trainingData.length; i++) {
			for (int j = 0; j < trainingData[trainingData.length - 1].length; j++) {
				if (trainingData[i][j] <= threshold) {
					binaryTrainingData[i][j] = 0;
				}
				else {
					binaryTrainingData[i][j] = 1;
				}
			}
		}
		
		
		double[][][] P_X_given_Y = new double[numberOfClasses][numberOfFeatures][2];
		double[][][] N_XY = new double[numberOfClasses][numberOfFeatures][2];
		
//		int c0 = 0, c1 = 0;
		for (int i = 0; i < binaryTrainingData.length; i++) {	//For each image in training data...
			for (int j = 0; j < numberOfFeatures; j++) {
				//Iterate over every image in training data, incrementing it's
				//corresponding slot in the N_XY array
				if (binaryTrainingData[i][j] == 0) {
					N_XY[trainingData[i][trainingData[i].length -1]][j][0]++;
				} else {
					N_XY[trainingData[i][trainingData[i].length -1]][j][1]++;
				}
			}
		}
		
//		for (int i = 0; i < numberOfClasses; i++) {
//			for (int j = 0; j < numberOfFeatures; j++) {
//				
//				
//				if (binaryTrainingData[i][j] == 0) {
//					N_XY[i][j][0] = c0;
//					c0 = 0;
//				} else {
//					N_XY[i][j][1] = c1;
//					c1 = 0;
//				}
//			}
//		}
		
		
		for(int i = 0; i < numberOfClasses; i++) {
			for(int j = 0; j < numberOfFeatures; j++) {
				P_X_given_Y[i][j][0] = (N_XY[i][j][0] + 1) / (N_Y[i] + 2);
				P_X_given_Y[i][j][1] = (N_XY[i][j][1] + 1) / (N_Y[i] + 2);
			}
		}
		
		
		return P_X_given_Y;
	}

	static int predict(int[] inputImage, double threshold, double[] P_Y, double[][][] P_X_given_Y) {
		int[] binaryInputImage = new int[inputImage.length];
		for (int i = 0; i < binaryInputImage.length; i++) {
			if (inputImage[i] > threshold) {
				binaryInputImage[i] = 1;
			} else {
				binaryInputImage[i] = 0;
			}
		}
		
		
		double[] results = new double[numberOfClasses];
		for (int i = 0; i < results.length; i++) {
			results[i] = Math.log10(P_Y[i] * 100);
			for (int j = 0; j < numberOfFeatures; j++) {
				results[i] = results[i] + Math.log10(P_X_given_Y[i][j][binaryInputImage[j]] * 100);
			}
		}
		
		//Find the max index
		int max_index = 0;
		double max_num = 0;
		for (int i = 0; i < results.length; i++) {
			if (results[i] > max_num) {
				max_num = results[i];
				max_index = i;
			}
		}
		return max_index;
	}

}
