/**
 * File:        KNNRecord.java
 * Author:      Jack Weaver <jhweaver@usc.edu>
 * Target:      aludra.usc.edu running Java 1.6.0_23
 */
package com.weaverworx.classifier.knn;

/**
 * For K-Nearest Neighbor, a Record is an instance of the data, and contains
 * the class type it represents, as well as the euclidean distance from it's
 * supposed target.
 * 
 * @author jw
 *
 */
public class KNNRecord implements Comparable<KNNRecord> {
	private int exampleClass;
	private double distance;
	
	public KNNRecord(int numericalClass, double distanceToTarget) {
		this.exampleClass = numericalClass;
		this.distance = distanceToTarget;
	}
	
	public KNNRecord() {	}

	/**
	 * @param numerical_class the numerical_class to set
	 */
	public void setExampleClass(int numerical_class) {
		this.exampleClass = numerical_class;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the numerical_class
	 */
	public int getExampleClass() {
		return exampleClass;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * For KNN, the comparison is conducted over it's euclidean distance from
	 * the input data.  This method allows for comparisons to be conducted 
	 * from the record data.
	 * 
	 * @param record
	 * @return 1 if the record is lower in distance, -1 if it is greater in 
	 * distance, and 0 if the distance is equal.
	 */
	@Override
	public int compareTo(KNNRecord record) {
		if (this.distance > record.distance)
				return 1;
			else if (this.distance < record.distance)
				return -1;
			else
				return 0;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("d: " + this.distance);
		sb.append("   ");
		sb.append("example class: " + this.exampleClass);
		return sb.toString();
	}
}
