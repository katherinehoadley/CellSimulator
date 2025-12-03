// Lance
package model;

public abstract class GamePreset {
	
	public GamePreset() {}

    public abstract String getPreset();
   
    protected int rows;
    protected int cols;
    protected int[][] newGrid;
    
    
    public int[][] updateGrid(int[][] grid){
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.newGrid = new int[rows][cols];
        return newGrid;
    }
    
    // Anna Rakes:
    public void configure(int thirdVariable) {}
    
    // Anna Rakes: 
    public abstract String getThirdVariableLabel();
    
    // Anna Rakes
    public String getFourthVariableLabel() {
    		return "";
    }

}
