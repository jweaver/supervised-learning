/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weaverworx.usc.csci561.assignment3.skeleton;

import java.io.*;
import java.util.*;
/**
 *
 * @author femto
 */
public class NaiveBayes_Skeleton {

final static int numberOfClasses = 10;
final static int numberOffeatures = 784;
final static int example_class_index = 784;

public static void main(String args[]){

int threshold = readUserInput(); // read K from user input

int [][] train = readTrainFile(); // last column 785 is a class of train image
int [][] test = readTestFile(); // last column 785 is a class of test image

double[] P_Y = getClassProbabilities(train); // Learn P(Y) parameters
double[][][] P_X_given_Y = getConditionalProbabilities(threshold ,train); //Learn P(X|Y) parameters
                                                                          // threshold used for binarized

int[] correct = new int[numberOfClasses];
int[] incorrect = new int[numberOfClasses];



for(int i=0;i<test.length;i++){ // for each test exaple

int actual_class = test[i][example_class_index];
int predict_class = predict(test[i],threshold,P_Y,P_X_given_Y);// predict by using P_Y and P_X_given_Y parameters
                                           // threshold used for binarized
if(actual_class == predict_class){
    correct[actual_class]++; //if actual_class same as predict_class, increasing correct of that class (correct[actual_class])
  }else{
    incorrect[actual_class]++;
  }

}

// display output

System.out.println("Threshold = " + threshold);

for(int i =0;i<10;i++){
  double accuracy =   (correct[i]*1.0)/(correct[i] + incorrect[i]);
  System.out.println("Class = " + i + " Correct = " + correct[i] + " Incorrect = " + incorrect[i] + " Accuracy = " + accuracy);
}

}

static int readUserInput(){
    return 100;
}
static int[][] readTrainFile(){

   ArrayList<String> tem = new ArrayList<String>();


   for(int i=0;i<numberOfClasses;i++){
    try{

    String filename = "train"+i+".txt";

FileInputStream fstream = new FileInputStream(filename);
    DataInputStream in = new DataInputStream(fstream);
    BufferedReader br = new BufferedReader(new InputStreamReader(in));
    String strLine;

    //Read File Line By Line with their class
    while ((strLine = br.readLine()) != null)   {
      tem.add(strLine + "," + i);
    }
    //Close the input stream
    in.close();
    }catch (Exception e){//Catch exception if any
      e.printStackTrace();
    }

   }
   int[][]  train = new int[tem.size()][numberOffeatures+1];

   for(int i=0;i<tem.size();i++){
   String[] line = tem.get(i).split(",");

     for(int j=0;j<line.length;j++){
       train[i][j] = Integer.parseInt(line[j]);
     }

   }

   return train;
}

static int[][] readTestFile(){

   ArrayList<String> tem = new ArrayList<String>();


   for(int i=0;i<numberOfClasses;i++){
    try{

    String filename = "test"+i+".txt";


    FileInputStream fstream = new FileInputStream(filename);
    DataInputStream in = new DataInputStream(fstream);
    BufferedReader br = new BufferedReader(new InputStreamReader(in));
    String strLine;

    //Read File Line By Line with their class
    while ((strLine = br.readLine()) != null)   {
      tem.add(strLine + "," + i);

    }


    //Close the input stream
    in.close();
    }catch (Exception e){//Catch exception if any
      e.printStackTrace();
    }

   }
   int[][]  test = new int[tem.size()][numberOffeatures+1];

   for(int i=0;i<tem.size();i++){
   String[] line = tem.get(i).split(",");

     for(int j=0;j<line.length;j++){
       test[i][j] = Integer.parseInt(line[j]);
     }

   }

   return test;
}

static double[] getClassProbabilities(int [][] train){
  
    double[] P_Y = new double[numberOfClasses];
    
    return P_Y;
}

static double[][][] getConditionalProbabilities(int threshold,int [][] train){
   double[][][] P_X_given_Y = new double[numberOfClasses][numberOffeatures][2];

    return P_X_given_Y;
}

static int predict(int[] input_image,double threshold,double[] P_Y,double[][][] P_X_given_Y){

  
   int max_index =0;


   return max_index;
}




}
