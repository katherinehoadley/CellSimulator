//Katherine Hoadley
//this class initializes the game of life game mode by randomly spawning in alive cells with
// alive rate given by user input
package controller;

import model.Cell;

public class InitializationGameOfLife extends Initialization {
	
	double rows;
	double cols;
	double startAliveChance;
	   

    public InitializationGameOfLife(double[] values ) {
    		this.rows = values[1];
    		this.cols = values[0];
    		this.startAliveChance = values[2];
    };
      
    //creates a new grid when ui calls this function.
    //starts a grid of potentially alive bacteria based on the startAliveChance given by user
    public int[][] initializeGrid() {
    		grid = new int[(int)rows][(int)cols];

    		for (int y = 0; y < rows; y++) {
    			for (int x = 0; x < cols; x++) {
    				double randomValue = Math.random();

    				if (y == 0 || y == rows - 1 || x == 0 || x == cols - 1) {
    					grid[y][x] = Cell.DEAD.getDisplayValue(); // always dead
    				} else {
    					if (randomValue < startAliveChance) {
    						grid[y][x] = Cell.ALIVE.getDisplayValue();
    					} else {
    						grid[y][x] = Cell.DEAD.getDisplayValue();
    					}
    				}
    			}
    		}
    		return grid;
    }

}
