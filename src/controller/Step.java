//Katherine Hoadley
package controller;

import model.GamePreset;

public class Step {

//	protected GamePreset automata;
	
	private GamePreset preset;

	public Step(GamePreset preset) {
		this.preset = preset;
	}
	

    // Anna Rakes
    public void step(boolean isPaused, boolean takeStep, int[][] grid){
		if (!isPaused || takeStep) {
			update(grid);
		}
    }

	public GamePreset getCurrentPreset(){
		return preset;
	}
	

    // Anna
    public void setCurrentPreset(GamePreset preset) {
    		this.preset = preset;
    }
    
    public void update(int[][] grid){
		int[][] newGrid = preset.updateGrid(grid);
		
		for (int y = 0; y < grid.length; y++) {
            System.arraycopy(newGrid[y], 0, grid[y], 0, grid[y].length);
	    }
    }
}
