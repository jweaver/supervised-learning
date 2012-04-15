/**
 * File:        CLASSIFIERS.java
 * Author:      Jack Weaver <jhweaver@usc.edu>
 * Course:      CSCI 561, Spring 2012
 * Assignment:  Assignment 3 - Supervised Learning Systems
 * Target:      aludra.usc.edu running Java 1.6.0_23
 */
package com.weaverworx.usc.csci561.assignment3.util;

/**
 * Standard list of input the user provides to internal mapping of the given
 * classifier.
 * 
 * @author jw
 *
 */
public enum ClassifierTypes {
	K_NEAREST_NEIGHBOR("knn"),
	NAIVE_BAYES("nb");
	
	private final String text;
	
	private ClassifierTypes(String name) {
		this.text = name;
	}
	
	@Override
	public String toString() {
		return this.text;
	}
	
}
