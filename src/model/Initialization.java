//Katherine Hoadley
//abstract superclass for initialization, all initializations need an initializeGrid function and a grid variable
package model;

public abstract class Initialization {
    
	//fields
    protected int[][] grid;
  
    //initialize grid method
    public abstract int[][] initializeGrid(int rows, int cols, double thirdVariable, int fourthVariable);

}
