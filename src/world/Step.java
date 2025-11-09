//Katherine Hoadley
package world;

public abstract class Step {
	
	public Step() {
		//constructor, does nothing
	}
    public abstract void update();
    
    public abstract int[][] getGrid();
}
