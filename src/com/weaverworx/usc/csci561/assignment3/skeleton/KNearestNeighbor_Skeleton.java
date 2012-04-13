/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weaverworx.usc.csci561.assignment3.skeleton;

import com.weaverworx.usc.csci561.assignment3.knn.KNNRecord;
import com.weaverworx.usc.csci561.assignment3.util.FileReader;
import com.weaverworx.usc.csci561.assignment3.util.KNNUtil;

/**
 * 
 * @author femto
 */
public class KNearestNeighbor_Skeleton {

	final static int numberOfClasses = 10;
	final static int numberOfFeatures = 784;
	final static int example_class_index = 784;

	public static void main(String args[]) {

		int K = readUserInput(); // read K from user input

		int[][] train = FileReader.getTrainingData(numberOfFeatures, numberOfClasses); // last column 785 is a class of train
											// image
		int[][] test = FileReader.getTestData(numberOfFeatures, numberOfClasses); // last column 785 is a class of test
										// imag	e

		KNNRecord[] knn_records = new KNNRecord[train.length]; //
		for (int j = 0; j < train.length; j++) {
			knn_records[j] = new KNNRecord(); // initialized knn record
		}

		int[] correct = new int[numberOfClasses];
		int[] incorrect = new int[numberOfClasses];
		// Note computational time is test.length*train.length*784

		for (int i = 0; i < test.length; i++) { // for each training example

			for (int j = 0; j < train.length; j++) { // test[i] comparing with
														// all train
//				knn_records[j].example_class = train[j][example_class_index]; // class is train class
				knn_records[j].setExampleClass(train[j][example_class_index]);
//				knn_records[j].distance = getEuclideanDistance(test[i], train[j]); // difference between test i and train j
				knn_records[j].setDistance(KNNUtil.getEuclideanDistance(test[i], train[j]));
			}

			int actual_class = test[i][example_class_index];
			int predict_class = KNNUtil.predict(K, knn_records);
			// System.out.println(i + ":" + actual_class + ":" + predict_class +
			// " : " + knn_records[train.length-1].distance);

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

		System.out.println("K = " + K);

		for (int i = 0; i < numberOfClasses; i++) {
			double accuracy = (correct[i] * 1.0) / (correct[i] + incorrect[i]);
			System.out.println("Class = " + i + " Correct = " + correct[i]
					+ " Incorrect = " + incorrect[i] + " Accuracy = "
					+ accuracy);
		}

	}

	static int readUserInput() {
		return 9;
	}

	

	static double getEuclideanDistance(int[] v1, int[] v2) {
		double distance = 0; // set distance to 0
		return distance;
	}

	static int predict(int K, KNNRecord[] knn) {
		int max_index = 0;
		return max_index;
	}

//	private static class KNNRecord {
//		public int example_class;
//		public double distance;
//	}
//
//	private static class KNNRecord_Comparator implements Comparator<KNNRecord> {
//
//		public int compare(KNNRecord r1, KNNRecord r2) {
//			if (r1.distance > r2.distance)
//				return 1;
//			else if (r1.distance < r2.distance)
//				return -1;
//			else
//				return 0;
//		}
//	}

}
