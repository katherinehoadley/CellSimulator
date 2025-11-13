//Katherine Hoadley
package controller;

import model.CellularAutomata;

public abstract class Step {

	protected CellularAutomata automata;

	public Step() {}
	
	public abstract void update(int[][] grid);


    // Anna Rakes
    public void step(boolean isPaused, boolean takeStep, int[][] grid){
		if (!isPaused || takeStep) {
			update(grid);
		}
    }

	//TODO - this needs to become Object instead of CellularAutomata at some point
	public CellularAutomata getCurrentAutomata(){
		return automata;
	}
}
