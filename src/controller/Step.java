//Katherine Hoadley
package controller;

public abstract class Step {

	public Step() {
		//constructor, does nothing
	}
	public abstract void update();

	public abstract int[][] getGrid();

	public abstract void step(boolean isPaused, boolean takeStep);
}
