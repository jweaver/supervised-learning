/**
 * File:        KNNRecord.java
 * Author:      Jack Weaver <jhweaver@usc.edu>
 * Course:      CSCI 561, Spring 2012
 * Assignment:  Assignment 3 - Supervised Learning Systems
 * Target:      aludra.usc.edu running Java 1.6.0_23
 */
package com.weaverworx.usc.csci561.assignment3.knn;

/**
 * @author jw
 *
 */
public class KNNRecord implements Comparable<KNNRecord> {
	private int example_class;		//TODO: Bad name, refactor then change getters & setters.
	private double distance;
	
	public KNNRecord(int numericalClass, double distanceToTarget) {
		this.example_class = numericalClass;
		this.distance = distanceToTarget;
	}
	
	public KNNRecord() {	}

	/**
	 * @param numerical_class the numerical_class to set
	 */
	public void setExampleClass(int numerical_class) {
		this.example_class = numerical_class;
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
		return example_class;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * 
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
		sb.append("example class: " + this.example_class);
		return sb.toString();
	}
}
