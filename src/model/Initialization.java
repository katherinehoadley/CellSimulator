//Katherine Hoadley
package model;

import controller.StepGameOfLife;

public class Initialization {

	private final double START_ALIVE_CHANCE = .3;
	private final int START_INDEX = 0;
	   
    private int[][] grid;

    public Initialization() {};
    
    //creates a new grid when ui calls this function.
    //starts a 3x3 grid of potentially alive bacteria
    //in the center of the grid. the rest is dead.
    public int[][] initializeGrid(int rows, int cols) {
        grid = new int[rows][cols];

        for (int y = START_INDEX; y < rows; y++) {
            for (int x = START_INDEX; x < cols; x++) {
                double randomValue = Math.random();

                if (randomValue < START_ALIVE_CHANCE) {
                    grid[y][x] = Cell.ALIVE.getDisplayValue();
                } else {
                    grid[y][x] = Cell.DEAD.getDisplayValue();
                }
            }
        }

        CellularAutomata automata = new Bacteria();
        StepGameOfLife step = new StepGameOfLife(grid);

        return grid;
    }

}
