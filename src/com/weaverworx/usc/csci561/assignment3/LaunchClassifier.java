/**
 * File:        Launcher.java
 * Author:      Jack Weaver <jhweaver@usc.edu>, <weaver.jack@gmail.com>
 * Target:      aludra.usc.edu running Java 1.6.0_23
 */
package com.weaverworx.usc.csci561.assignment3;

import com.weaverworx.usc.csci561.assignment3.knn.KNNRecord;
import com.weaverworx.usc.csci561.assignment3.util.ClassifierTypes;
import com.weaverworx.usc.csci561.assignment3.util.ClassifierInputReader;
import com.weaverworx.usc.csci561.assignment3.util.LearningUtil;

/**
 * Main class to launch the application.
 * 
 * @author jw
 * 
 * TODO: Reduce main() size, refactor out the logic necessary for the classifications.
 * Since there are two logical separations, this can abstracted somewhat simply.
 * TODO: Remove hard typed class index example, provide for this to be given to the
 * system in a different file as part of it's bootstrap.
 */
public class LaunchClassifier {
	private final static int EX_CLASS_INDEX = 784;

	/**
	 * Entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ClassifierTypes classifier = LearningUtil.parseClassifierSystem(args);

		if (classifier.name().compareTo(ClassifierTypes.K_NEAREST_NEIGHBOR.name()) == 0) {
			int k = LearningUtil.parseKArgument(args); // Read k from user input
			// Get the training data and test data
			int[][] trainData = ClassifierInputReader.getTrainingData(LearningUtil.NUMBER_OF_FEATURES,
					LearningUtil.NUMBER_OF_CLASSES);
			int[][] testData = ClassifierInputReader.getTestData(LearningUtil.NUMBER_OF_FEATURES,
					LearningUtil.NUMBER_OF_CLASSES);

			// Set up the K-Nearest Neighbor Records
			KNNRecord[] knnRecords = new KNNRecord[trainData.length];
			for (int i = 0; i < trainData.length; i++) {
				knnRecords[i] = new KNNRecord();
			}

			// Set up the arrays for # correct and # incorrect
			int[] correct = new int[LearningUtil.NUMBER_OF_CLASSES], incorrect = 
					new int[LearningUtil.NUMBER_OF_CLASSES];
			for (int i = 0; i < testData.length; i++) {
				for (int j = 0; j < trainData.length; j++) {
					// Set the distance & the record class
					knnRecords[j].setExampleClass(trainData[j][EX_CLASS_INDEX]);
					knnRecords[j].setDistance(LearningUtil
							.getEuclideanDistance(testData[i], trainData[j]));
				}
				int actualClass = testData[i][EX_CLASS_INDEX];
				int predictedClass = LearningUtil.predictKNN(k, knnRecords);
				// Set the counters for accuracy, every time we correctly
				// predict
				// the numerical class, tally it. Otherwise tally the miss.
				if (actualClass == predictedClass) {
					correct[actualClass]++;
				} else {
					incorrect[actualClass]++;
				}
			}
			// Display the results to Stdout
			LearningUtil.outputKNNResultsToStdOut(k, correct, incorrect);
			
			
		} else if (classifier.name().compareTo(ClassifierTypes.NAIVE_BAYES.name()) == 0) {
			int threshold = LearningUtil.parseTArgument(args);
			double[] N_Y = new double[LearningUtil.NUMBER_OF_CLASSES];
			double[] P_Y = new double[LearningUtil.NUMBER_OF_CLASSES];
			int[][] trainingData = ClassifierInputReader.getTrainingData(LearningUtil.NUMBER_OF_FEATURES, 
					LearningUtil.NUMBER_OF_CLASSES);
			int[][] testData = ClassifierInputReader.getTestData(LearningUtil.NUMBER_OF_FEATURES, 
					LearningUtil.NUMBER_OF_CLASSES);
			
			//Setup N_Y: the number of times a given training data entry
			//appears among ALL the training data, ie- N_Y[1] is # of 1s
			int totalTrainingSize = 0;
			for (int i = 0; i < LearningUtil.NUMBER_OF_CLASSES; i++) {
				for (int j = 0; j < trainingData.length; j++) {
					//For each "record" in all test data
					if (trainingData[j][trainingData[j].length -1] == i) {
						N_Y[i] = N_Y[i] + 1;
						totalTrainingSize++;
					}
				}
				if (i == LearningUtil.NUMBER_OF_CLASSES - 1) {
					for (int k = 0; k < P_Y.length; k++) {
						P_Y[k] = N_Y[k] / totalTrainingSize;
					}
				}
			}
			
			double[][][] P_X_given_Y = 
					LearningUtil.getBayesConditionalProbabilities(threshold, 
															trainingData, N_Y);
			int[] correct = new int[LearningUtil.NUMBER_OF_CLASSES];
			int[] incorrect = new int[LearningUtil.NUMBER_OF_CLASSES];
			for (int i = 0; i < testData.length; i++) { // for each test example
				int actual_class = testData[i][EX_CLASS_INDEX];
				/*
				 *  predict by using P_Y and P_X_given_Y parameters threshold used 
				 *  for converting to binary (1,0) data format.
				 */
				int predict_class = LearningUtil.predictBayes(testData[i], threshold, 
						P_Y, P_X_given_Y);
				if (actual_class == predict_class) {
					/* 
					 * if actual_class same as predict_class, 
					 * increasing correct of that class 
					 * (correct[actual_class])
					 */ 
					correct[actual_class]++; 
				} else {
					incorrect[actual_class]++;
				}
			}
			LearningUtil.outputBayesResultsToStdOut(threshold, correct, incorrect);
			// End of NAIVE BAYES
		} else {
			System.out.println(LearningUtil.getUsage());
			System.exit(0);
		}
	}
}
