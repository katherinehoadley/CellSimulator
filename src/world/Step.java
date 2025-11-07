package world;

import automata.Bacteria;
import automata.CellularAutomata;

public class Step {

    //TODO - this class will update the grid to the next iteration every tick.

    public static void update(){
        System.out.println("HI MOM");

        //TODO - get these correctly
        byte[][] grid = null;
        CellularAutomata automata = new Bacteria();

        grid = automata.updateGrid(grid);
        //TODO - redraw visualizer
    }

}
