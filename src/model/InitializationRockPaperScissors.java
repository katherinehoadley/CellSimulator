package model;

public class InitializationRockPaperScissors extends Initialization{

	private double ROCK_SCISSORS_PROPORTION = .4;
	private double TRANSITION_ROWS_PROPORTION = .3;
	
	@Override
	public int[][] initializeGrid(int rows, int cols, double startAliveChance) {
	    int[][] grid = new int[rows][cols];
	
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


}
