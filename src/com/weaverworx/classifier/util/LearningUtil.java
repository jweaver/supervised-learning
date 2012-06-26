/**
 * File:        KNNUtil.java
 * Author:      Jack Weaver <jhweaver@usc.edu>
 * Target:      aludra.usc.edu running Java 1.6.0_23
 */
package com.weaverworx.classifier.util;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.weaverworx.classifier.LaunchClassifier;
import com.weaverworx.classifier.knn.KNNRecord;

/**
 * Provides for common utility across both K-Nearest Neighbor (KNN) and Naive
 * Bayes classifier algorithms.
 * 
 * @author jw
 *
 * TODO:  Refactor hard typed number of features and classes; should expand 
 * for ability to specify arbitrary bit-value for the image sizes.
 */
public class LearningUtil {
	/*
	 * Hard-coded value of the number of classes (record types) for this 
	 * classifier.
	 */
	public static final int NUMBER_OF_CLASSES = 10;
	/*
	 * Hard-coded value of the number of 'features' each record/class has in
	 * this classifier.  This is how many data points there are per record/
	 * class.
	 */
	public static final int NUMBER_OF_FEATURES = 784;
	private static final String TEN_SPACES = "          ";
	
	
	private LearningUtil() { }
	
	public static int parseTArgument(String arguments[]) {
		int threshold = 0;
		String tString = arguments[1];
		Scanner scanner = new Scanner(tString).useDelimiter("-t=");
		threshold = scanner.nextInt();
		return threshold;
	}
	
	public static int parseKArgument(String arguments[]) {
		int k = 0;
		String kString = arguments[1];
		Scanner scanner = new Scanner(kString).useDelimiter("-k=");
		k = scanner.nextInt();
		return k;
	}
	
	public static void outputBayesResultsToStdOut(int t, int[] correct, int[] incorrect) {
		StringBuilder sb = new StringBuilder();
		sb.append("T = " + t + "\n");
		outputResults(sb, correct, incorrect);
	}
	
	public static void outputKNNResultsToStdOut(int k, int[] correct, int[] incorrect) {
		StringBuilder sb = new StringBuilder();
		sb.append("K = " + k + "\n");
		outputResults(sb, correct, incorrect);
	}
	
	private static void outputResults(StringBuilder sb, int[] correct, int[] incorrect) {
				//Build the table column headers
		sb.append("Class").append(TEN_SPACES).append("Correct").append(TEN_SPACES)
			.append("Incorrect").append(TEN_SPACES).append("Accuracy");
		sb.append("\n");
		//Fill in the table
		DecimalFormat accuracyFormatter = new DecimalFormat("#.#####");
		for (int i = 0; i < NUMBER_OF_CLASSES; i++) {
			double accuracy = (correct[i] * 1.0) / (correct[i] + incorrect[i]);
			sb.append(i).append("    ").append(TEN_SPACES);//Class
			sb.append(correct[i]).append("     ").append(TEN_SPACES);//# Correct
			sb.append(incorrect[i]).append("      ").append(TEN_SPACES);//# Incorrect
			sb.append(accuracyFormatter.format(accuracy)).append("\n");
		}
		System.out.println(sb.toString());
	}
	
	public static double getEuclideanDistance(int[] v1, int[] v2) {
		double distance = 0; // set distance to 0
		double runningTotal = 0;
		double delta = 0;
		
		for (int i = 0; i < v1.length; i++) {
			delta = (v1[i] - v2[i]);
			delta = Math.pow(delta, 2d);
			runningTotal += delta;
		}
		distance = Math.sqrt(runningTotal);
		return distance;
	}
	
	/**
	 * 
	 * @param K
	 * @param knn
	 * @return
	 */
	public static int predictKNN(int K, KNNRecord[] knn) {
		//Prep the data
		List<KNNRecord> recordList = Arrays.asList(knn);
		Collections.sort(recordList);
		
		//Do the voting
		int[] voteArray = new int[10];	//index position represents the numerical class
		for(int i = 0; i < K; i++) {
			KNNRecord voter = recordList.get(i);
			int number_class = voter.getExampleClass();
			voteArray[number_class]++;
		}
		
		//Find out who got the most votes
		int winner = -1;	//winner is the index with the most votes in the array
		int winnerVotes = 0;	//the actual *count* of the most votes
		for (int i = 0; i < voteArray.length; i++) {
			if (voteArray[i] > winnerVotes) {
				winnerVotes = voteArray[i];
				winner = i;
			}
		}
		return winner;
	}
	
	/**
	 * 
	 * @return the usage (arguments, commands, etc) used on command line to 
	 * operate the program.
	 */
	public static String getUsage() {
		StringBuilder sb = new StringBuilder();
		sb.append("Usage as follows:\n");
		sb.append(LaunchClassifier.class.getName());
		sb.append(" "); sb.append("[ classifier_type ]");
		sb.append(" [ classifier_argument ]" + "\n");
		sb.append("Where classifier_type is one of the following:\n\n");
		for (ClassifierTypes classifierType : ClassifierTypes.values()) {
			sb.append(classifierType.toString()); sb.append(" for ");
			sb.append(classifierType.name());
			sb.append("\n");
		}
		sb.append("\nClassifier Arguments:\n");
		sb.append(ClassifierTypes.K_NEAREST_NEIGHBOR.name() + " -k[=value]\n");
		sb.append(ClassifierTypes.NAIVE_BAYES.name() + " -t[=value]\n");
		sb.append("\nNote: Each value is an integer, whether specifying k for" +
				" neighbor count, or t for a Threshold.");
		sb.append("\n\nExamples:\n");
		sb.append(LaunchClassifier.class.getName() + " " + 
				ClassifierTypes.K_NEAREST_NEIGHBOR.toString() + " -k=10\n");
		sb.append(LaunchClassifier.class.getName() + " " +
				ClassifierTypes.NAIVE_BAYES.toString() + " -t=130\n");
		
		return sb.toString();
	}

	/**
	 * 
	 * @param threshold
	 * @param trainingData
	 * @param N_Y
	 * @return
	 */
	public static double[][][] getBayesConditionalProbabilities(int threshold,
			int[][] trainingData, double[] N_Y) {
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
		
		double[][][] P_X_given_Y = new double[NUMBER_OF_CLASSES][NUMBER_OF_FEATURES][2];
		double[][][] N_XY = new double[NUMBER_OF_CLASSES][NUMBER_OF_FEATURES][2];
		for (int i = 0; i < binaryTrainingData.length; i++) {
			//For each image in training data...
			for (int j = 0; j < NUMBER_OF_FEATURES; j++) {
				//Iterate over every image in training data, incrementing it's
				//corresponding slot in the N_XY array
				if (binaryTrainingData[i][j] == 0) {
					N_XY[trainingData[i][trainingData[i].length -1]][j][0]++;
				} else {
					N_XY[trainingData[i][trainingData[i].length -1]][j][1]++;
				}
			}
		}
		for(int i = 0; i < NUMBER_OF_CLASSES; i++) {
			for(int j = 0; j < NUMBER_OF_FEATURES; j++) {
				P_X_given_Y[i][j][0] = (N_XY[i][j][0] + 1) / (N_Y[i] + 2);
				P_X_given_Y[i][j][1] = (N_XY[i][j][1] + 1) / (N_Y[i] + 2);
			}
		}
		
		return P_X_given_Y;
	}

	/**
	 * 
	 * @param inputImage
	 * @param threshold
	 * @param P_Y
	 * @param P_X_given_Y
	 * @return
	 */
	public static int predictBayes(int[] inputImage, double threshold, 
			double[] P_Y, double[][][] P_X_given_Y) {
		int[] binaryInputImage = new int[inputImage.length];
		for (int i = 0; i < binaryInputImage.length; i++) {
			if (inputImage[i] > threshold) {
				binaryInputImage[i] = 1;
			} else {
				binaryInputImage[i] = 0;
			}
		}
		
		
		double[] results = new double[NUMBER_OF_CLASSES];
		for (int i = 0; i < results.length; i++) {
			results[i] = Math.log10(P_Y[i] * 100);
			for (int j = 0; j < NUMBER_OF_FEATURES; j++) {
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
	
	
	/**
	 * Given a list of classifiers, parses the input on args to determine which
	 * classifier is being tested/used.  See
	 * @param args
	 * @param classifiers
	 */
	public static ClassifierTypes parseClassifierSystem(String[] args) {
		if (args.length < 2) {
			System.out.println(LearningUtil.getUsage());
			System.exit(0);
		}
		if (args[0].compareTo(ClassifierTypes.K_NEAREST_NEIGHBOR.toString()) == 0) {
			return ClassifierTypes.K_NEAREST_NEIGHBOR;
			
		} else if(args[0].compareTo(ClassifierTypes.NAIVE_BAYES.toString()) == 0) {
			return ClassifierTypes.NAIVE_BAYES;
		} else {
			System.out.println(LearningUtil.getUsage());
			System.exit(0);
		}
		return null;
	}
}
