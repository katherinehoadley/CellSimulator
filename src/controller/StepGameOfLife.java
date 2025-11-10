//Katherine Hoadley
package controller;

import java.awt.Point;
import java.util.Collection;

import model.Bacteria;
import model.CellularAutomata;
import view.GridUI;

public class StepGameOfLife extends Step{
	
	

    //TODO - this class will update the grid to the next iteration every tick.
	private int[][] grid;
	private CellularAutomata automata;
	
	public StepGameOfLife() {}
	
	public StepGameOfLife(int[][] startingGrid) {
		this.grid = startingGrid;
		this.automata = new Bacteria();
	}
		
	@Override
    public void update(){
		grid = automata.updateGrid(grid);


    }
    public int[][] getGrid(){
    	return grid;
    }
    
    // Anna Rakes
    public void step(boolean isPaused){
		if (!isPaused) {
			update();
		}
    }

}
