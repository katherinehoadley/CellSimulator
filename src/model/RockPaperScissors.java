package model;

public class RockPaperScissors extends GamePreset{

    public RockPaperScissors() {}
    private int threshold = 3;
    
    // Anna Rakes:
    @Override
    public void configure(int threshold) {
    		this.threshold = threshold;
    }

    @Override
    public int[][] updateGrid(int[][] grid) {
        super.updateGrid(grid);
        //loop over every cell in the grid
        for (int y = 1; y < rows-1; y++) {
            for (int x = 1; x < cols-1; x++) {

                int originalValue = grid[y][x];
                newGrid[y][x] = updateCell(grid, x, y, originalValue);
            }
        }

        return newGrid;
    }

    public int updateCell(int[][] grid, int x, int y, int originalValue) {
        int rows = grid.length;
        int cols = grid[0].length;

        //this is a good replacement for Maps
        Cell center = Cell.fromValue(originalValue);
        Cell superior = getSuperior(center);
        
        int superiorCount = 0;

        //RPS core loop logic. Check 3x3 of neighbors and find out which Cell type theres most of.
        for (int directionY = -1; directionY <= 1; directionY++) {
            for (int directionX = -1; directionX <= 1; directionX++) {
                if (directionX == 0 && directionY == 0) continue;

                int newX = x + directionX;
                int newY = y + directionY;

                // check if it's within the grid, so it doesn't give IndexOutOfBoundsException pain
                if (newX >= 0 && newX < cols && newY >= 0 && newY < rows) {

                    Cell neighbor = Cell.fromValue(grid[newY][newX]);

                    if (neighbor == superior) {
                        superiorCount++;
                    }
                }
            }
        }
        
        if (superiorCount >= threshold) {
            return superior.getDisplayValue();
        }

        return originalValue;
    }

    private Cell getSuperior(Cell center) {
        return switch (center) {
            case ROCK -> Cell.PAPER;
            case PAPER -> Cell.SCISSORS;
            case SCISSORS -> Cell.ROCK;
            default -> Cell.DEAD;
        };
    }

    @Override
    public String getPreset() {
        return "RockPaperScissors";
    }
    
    // Anna:
    @Override
    public String getThirdVariableLabel() {
    	return "Threshold";
    }
}
