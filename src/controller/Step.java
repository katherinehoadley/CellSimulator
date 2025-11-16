//Katherine Hoadley
package controller;

import model.GamePreset;

public abstract class Step {

	protected GamePreset automata;

	public Step() {}
	
	public abstract void update(int[][] grid);


    // Anna Rakes
    public void step(boolean isPaused, boolean takeStep, int[][] grid){
		if (!isPaused || takeStep) {
			update(grid);
		}
    }

	public GamePreset getCurrentAutomata(){
		return automata;
	}
	

    // Anna
    public void setCurrentAutomata(GamePreset automata) {
    		this.automata = automata;
    }
}
