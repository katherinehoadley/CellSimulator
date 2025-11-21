//Katherine Hoadley
//this class is in charge of maintaining the update logic and the relationship
//between model and view
package controller;

import model.GamePreset;

public class Step {
	
	//fields
	private GamePreset preset;

	//constructor
	public Step(GamePreset preset) {
		this.preset = preset;
	}
	

    // Anna Rakes
	//step function to update the grid unless game is paused
    public void step(boolean isPaused, boolean takeStep, int[][] grid){
		if (!isPaused || takeStep) {
			update(grid);
		}
    }

    //get the game type
	public GamePreset getCurrentPreset(){
		return preset;
	}
	

    // Anna
	//set the game type
    public void setCurrentPreset(GamePreset preset) {
    		this.preset = preset;
    }
    
    //updates the grid using the rules of the specific preset being used
    public void update(int[][] grid){
		int[][] newGrid = preset.updateGrid(grid);
		
		for (int y = 0; y < grid.length; y++) {
            System.arraycopy(newGrid[y], 0, grid[y], 0, grid[y].length);
	    }
    }
}
