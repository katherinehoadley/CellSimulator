//Katherine Hoadley
package controller;

import model.Bacteria;

public class StepGameOfLife extends Step{
	
	public StepGameOfLife() {
		this.automata = new Bacteria();
	}
		
	@Override
    public void update(int[][] grid){
		int[][] newGrid = automata.updateGrid(grid);
		
		for (int y = 0; y < grid.length; y++) {
            System.arraycopy(newGrid[y], 0, grid[y], 0, grid[y].length);
	    }
    }
}
