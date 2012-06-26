/**
 * File:        CLASSIFIERS.java
 * Author:      Jack Weaver <jhweaver@usc.edu>
 * Target:      aludra.usc.edu running Java 1.6.0_23
 */
package com.weaverworx.classifier.util;

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
