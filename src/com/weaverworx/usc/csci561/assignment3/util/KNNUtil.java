/**
 * File:        KNNUtil.java
 * Author:      Jack Weaver <jhweaver@usc.edu>
 * Course:      CSCI 561, Spring 2012
 * Assignment:  Assignment 3 - Supervised Learning Systems
 * Target:      aludra.usc.edu running Java 1.6.0_23
 */
package com.weaverworx.usc.csci561.assignment3.util;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.weaverworx.usc.csci561.assignment3.knn.KNNRecord;

/**
 * @author jw
 *
 */
public class KNNUtil {
	private static final String TEN_SPACES = "          ";
	
	private KNNUtil() { }
	
	public static int parseKArgument(String arguments[]) {
		int k = 0;
		String kString = arguments[0];
		k = Integer.parseInt(kString);
		return k;
	}
	
	public static void outputResultsToStdOut(int k, int[] correct, int[] incorrect,
			int numberOfClasses) {
		StringBuilder sb = new StringBuilder();
		sb.append("K = " + k + "\n");
		//Build the table column headers
		sb.append("Class").append(TEN_SPACES).append("Correct").append(TEN_SPACES)
			.append("Incorrect").append(TEN_SPACES).append("Accuracy");
		sb.append("\n");
		//Fill in the table
		DecimalFormat accuracyFormatter = new DecimalFormat("#.#####");
		for (int i = 0; i < numberOfClasses; i++) {
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
		
		for (int i : v1) {
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
	public static int predict(int K, KNNRecord[] knn) {
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
}
