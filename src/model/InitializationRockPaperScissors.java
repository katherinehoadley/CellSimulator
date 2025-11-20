//Katherine Hoadley
//This class initializes the rock paper scissors game by separating the three into their own sections
package model;

import java.util.Random;

public class InitializationRockPaperScissors extends Initialization{

	//fields
	private double ROCK_SCISSORS_PROPORTION = .5; 
	private double TRANSITION_ROWS_PROPORTION = .3;

	//Initialize grid calculates the exact regions that each cell type is supposed to go in.
	//It then fills the grid with the correct cell type.
	public int[][] initializeGrid(int rows, int cols, double thirdVariable, int fourthVariable) {
		grid = new int[rows][cols];

	    int usableRows = rows - 2;
	    int usableCols = cols - 2;
	
	    int topRows = (int) (usableRows * ROCK_SCISSORS_PROPORTION);
	    int transitionRows = (int) (usableRows * TRANSITION_ROWS_PROPORTION);
	    int bottomStart = topRows + transitionRows;
	
	    int center = usableCols / 2;
	
	    for (int y = 0; y < rows; y++) {
	        for (int x = 0; x < cols; x++) {
	            if (y == 0 || y == rows - 1 || x == 0 || x == cols - 1) {
	                grid[y][x] = Cell.DEAD.getDisplayValue();
	                continue;
	            }
	
	            int innerY = y - 1;
	            int innerX = x - 1;
	
	            if (innerY < topRows) {
					if (innerX < center) {
					    grid[y][x] = Cell.ROCK.getDisplayValue();
					} else {
					    grid[y][x] = Cell.SCISSORS.getDisplayValue();
					}
	            } 
	            else if (innerY < bottomStart) {
	                int rowIndex = innerY - topRows;
	                double progress = (double) rowIndex / transitionRows;
	                int shrink = (int) (progress * center);
	
	                int leftLimit = center - shrink;
	                int rightLimit = center + shrink;
	
	                if (innerX < leftLimit) {
	                    grid[y][x] = Cell.ROCK.getDisplayValue();
	                } 
	                else if (innerX >= rightLimit) {
	                    grid[y][x] = Cell.SCISSORS.getDisplayValue();
	                } 
	                else {
	                    grid[y][x] = Cell.PAPER.getDisplayValue();
	                }
	            } 
	            else {
	                grid[y][x] = Cell.PAPER.getDisplayValue();
	            }
	        }
	    }
	    return grid;
	}

	//this class is not actually used, it is just for fun
    public int[][] randomInitializeGrid(int rows, int cols, double thirdVariable) {
        int[][] grid = new int[rows][cols];

        Random random = new Random();
        
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (y == 0 || y == rows - 1 || x == 0 || x == cols - 1) {
                    grid[y][x] = Cell.DEAD.getDisplayValue();
                    continue;
                }
                int number = random.nextInt(10);
                
                if (number < 3){
                    grid[y][x] = Cell.ROCK.getDisplayValue();
                } else if (number < 6){
                    grid[y][x] = Cell.PAPER.getDisplayValue();
                } else {
                    grid[y][x] = Cell.SCISSORS.getDisplayValue();
                }
            }
        }
        return grid;
    }


}

