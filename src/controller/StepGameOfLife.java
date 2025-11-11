//Katherine Hoadley
package controller;


import java.util.Arrays;

import model.Bacteria;
import model.CellularAutomata;


public class StepGameOfLife extends Step{
	
	

    //TODO - this class will update the grid to the next iteration every tick.
//	private int[][] grid;
	private CellularAutomata automata = new Bacteria();
	
	public StepGameOfLife() {}
	
	public StepGameOfLife(int[][] startingGrid) {
//		this.grid = startingGrid;
		this.automata = new Bacteria();
	}
		
	@Override
    public void update(int[][] grid){
		int[][] newGrid = automata.updateGrid(grid);
		
		for (int y = 0; y < grid.length; y++) {
	        for (int x = 0; x < grid[y].length; x++) {
	            grid[y][x] = newGrid[y][x];
	        }
	    }

    }
//    public int[][] getGrid(){
//    	return grid;
//    }
    
    // Anna Rakes
    public void step(boolean isPaused, boolean takeStep, int[][] grid){
		if (!isPaused || takeStep) {
			update(grid);
		}
    }


}
