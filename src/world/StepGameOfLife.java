//Katherine Hoadley
package world;

import automata.Bacteria;
import automata.CellularAutomata;
import screens.GameUI;

public class StepGameOfLife extends Step{

    //TODO - this class will update the grid to the next iteration every tick.
	private int[][] grid;
	private CellularAutomata automata;
	
	public StepGameOfLife(int[][] startingGrid) {
		this.grid = startingGrid;
		this.automata = new Bacteria();
	}
		
	@Override
    public void update(){
		grid = automata.updateGrid(grid);
		GameUI.redraw(grid);

    }
    public int[][] getGrid(){
    	return grid;
    }

}
