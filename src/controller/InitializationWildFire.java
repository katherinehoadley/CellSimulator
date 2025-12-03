//Katherine Hoadley
//This class initializes wildfire by spawning alive trees
//based on the given density, then randomly places the given
//number of burning trees

package controller;

import java.util.ArrayList;
import java.util.Collections;

import model.Cell;

public class InitializationWildFire extends Initialization{
	
	//fields
	double treeDensity;
	double burningTrees;
	 
	//constructor
	public InitializationWildFire(double[] values) {
		this.rows = values[1];
		this.cols = values[0];
		this.treeDensity = values[2];
		this.burningTrees = values[3];
	}
	
	//initializes the grid by making the edge cells dead trees, randomly spawning live trees
	//based on the density given, and then randomly putting the burning trees in the grid 
	public int[][] initializeGrid() {
	    ArrayList<int[]> treeCells = new ArrayList<>();
		grid = new int[(int)rows][(int)cols];
		
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
			
		    if (burningTrees > treeCells.size()) {
		        burningTrees = treeCells.size();
		    }
		        
		    Collections.shuffle(treeCells);
		        
		    for (int i = 0; i < burningTrees; i++) {
		        int[] pos = treeCells.get(i);
		        int y = pos[0];
		        int x = pos[1];
		        grid[y][x] = Cell.BURNING_TREE.getDisplayValue();
		    }
		
		return grid;
	}

}
