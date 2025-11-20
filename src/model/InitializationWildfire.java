package model;

import java.util.ArrayList;
import java.util.Collections;

public class InitializationWildfire extends Initialization{
 
	@Override
	public int[][] initializeGrid(int rows, int cols, double treeDensity, int spawnBurnChance) {
		grid = new int[rows][cols];
        ArrayList<int[]> treeCells = new ArrayList<>();
		
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {

				if (y == 0 || y == rows - 1 || x == 0 || x == cols - 1) {
					grid[y][x] = Cell.DEAD_TREE.getDisplayValue(); 
					continue;
				}
				if (Math.random() < treeDensity) {
	                   grid[y][x] = Cell.ALIVE_TREE.getDisplayValue();
	                   treeCells.add(new int[]{y, x});
	            } 
				else {
	                    grid[y][x] = Cell.GROUND.getDisplayValue();
	                }
	            }
			}
			
		    if (spawnBurnChance > treeCells.size()) {
		        spawnBurnChance = treeCells.size();
		    }
		        
		    Collections.shuffle(treeCells);
		        
		    for (int i = 0; i < spawnBurnChance; i++) {
		        int[] pos = treeCells.get(i);
		        int y = pos[0];
		        int x = pos[1];
		        grid[y][x] = Cell.BURNING_TREE.getDisplayValue();
		    }
		
		return grid;
	}
}
