//Katherine Hoadley
package model;


public class Initialization {

	private final double START_ALIVE_CHANCE = .5;
	private final int HALF = 2;
	private final int START_SPAWN_SIZE = 20;
	private final int BACK_ONE = 1;
	private final int LOOP_START_VALUE = 0;
	
    private int[][] grid;

    
    public Initialization() {};
    
    //creates a new grid when ui calls this function.
    //starts a 3x3 grid of potentially alive bacteria
    //in the center of the grid. the rest is dead.
    public int[][] initializeGrid(int rows, int cols){
    	
    	grid = new int[rows][cols];

        int startRow = (rows / HALF) - BACK_ONE;
        int endRow = startRow + START_SPAWN_SIZE;
        int startCol = (cols / HALF) - BACK_ONE;
        int endCol = startCol + START_SPAWN_SIZE;
        
        for (int y = LOOP_START_VALUE; y < rows; y++) {
            for (int x = LOOP_START_VALUE; x < cols; x++) {
                if (y >= startRow && y < endRow && x >= startCol && x < endCol) {
                    double randomValue = Math.random();
                    if (randomValue < START_ALIVE_CHANCE) {
                        grid[y][x] = Cell.ALIVE.getDisplayValue();
                    } else {
                        grid[y][x] = Cell.DEAD.getDisplayValue();
                    }
                } 
                else {
                    grid[y][x] = Cell.DEAD.getDisplayValue();
                }
            }
        }
    	

    	return grid;
    }

}
