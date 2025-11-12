//Katherine Hoadley
package controller;

public abstract class Step {

	public Step() {}
	
	public abstract void update(int[][] grid);


    // Anna Rakes
    public void step(boolean isPaused, boolean takeStep, int[][] grid){
		if (!isPaused || takeStep) {
			update(grid);
		}
    }
}
