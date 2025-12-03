//Katherine Hoadley
//abstract superclass for initialization, all initializations need an initializeGrid function and a grid variable
package controller;

public abstract class Initialization {
    
	//fields
    protected int[][] grid;
	double rows;
	double cols;
  
    //initialize grid method
    public abstract int[][] initializeGrid();

}
