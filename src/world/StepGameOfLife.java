//Katherine Hoadley
package world;

import automata.Bacteria;
import automata.CellularAutomata;

public class StepGameOfLife extends Step{

    //TODO - this class will update the grid to the next iteration every tick.
	
	public StepGameOfLife() {}

    public void update(){

        //TODO - get these correctly
        byte[][] grid = null;
        CellularAutomata automata = new Bacteria();

        grid = automata.updateGrid(grid);
        //TODO - redraw visualizer
    }

}
