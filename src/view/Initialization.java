//Katherine Hoadley
package view;

import world.Cell;
import world.Step;
import world.StepGameOfLife;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import automata.Bacteria;
import automata.CellularAutomata;

public class Initialization {

	private final double START_ALIVE_CHANCE = .3;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);	//lance says this ok. kat disagrees.
    private int[][] grid;

    
    public Initialization() {};
    
    //creates a new grid when ui calls this function.
    //starts a 3x3 grid of potentially alive bacteria
    //in the center of the grid. the rest is dead.
    public int[][] initializeGrid(int rows, int cols){
    	
    	grid = new int[rows][cols];

        int startRow = (rows / 2) - 1;
        int endRow = startRow + 3;
        int startCol = (cols / 2) - 1;
        int endCol = startCol + 3;
        
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
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
    	
        CellularAutomata automata = new Bacteria();
        StepGameOfLife step = new StepGameOfLife(grid);

        scheduler.scheduleAtFixedRate(step::update, 0, 100, TimeUnit.MILLISECONDS);
        
    	return grid;
    }

}
