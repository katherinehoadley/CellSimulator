//Katherine Hoadley
package model;

public class InitializationGameOfLife extends Initialization {
	   

    public InitializationGameOfLife() {};
    
    //creates a new grid when ui calls this function.
    //starts a grid of potentially alive bacteria
    public int[][] initializeGrid(int rows, int cols, double startAliveChance, int fourthVariable) {
    		grid = new int[rows][cols];

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
