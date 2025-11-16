package model;

public abstract class GamePreset {
	
	public GamePreset() {}

    public abstract String getPreset();
    public abstract int[][] updateGrid(int[][] grid);
}
