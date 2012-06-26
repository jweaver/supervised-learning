package com.weaverworx.classifier.skeleton;

import com.weaverworx.classifier.knn.KNNRecord;
import com.weaverworx.classifier.util.ClassifierInputReader;
import com.weaverworx.classifier.util.LearningUtil;

/**
 * 
 * @author femto
 */
public class KNearestNeighbor_Skeleton {

	final static int numberOfClasses = 10;
	final static int numberOfFeatures = 784;
	final static int example_class_index = 784;

	public static void main(String args[]) {

		int K = LearningUtil.parseKArgument(args); // read K from user input

		int[][] train = ClassifierInputReader.getTrainingData(numberOfFeatures, numberOfClasses); // last column 785 is a class of train
											// image
		int[][] test = ClassifierInputReader.getTestData(numberOfFeatures, numberOfClasses); // last column 785 is a class of test
										// image

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
				knn_records[j].setDistance(LearningUtil.getEuclideanDistance(test[i], train[j]));
			}

			int actual_class = test[i][example_class_index];
			int predict_class = LearningUtil.predictKNN(K, knn_records);

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
		LearningUtil.outputKNNResultsToStdOut(K, correct, incorrect);
	}
	

//	static double getEuclideanDistance(int[] v1, int[] v2) {
//		double distance = 0; // set distance to 0
//		return distance;
//	}
//
//	static int predict(int K, KNNRecord[] knn) {
//		int max_index = 0;
//		return max_index;
//	}
//
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
