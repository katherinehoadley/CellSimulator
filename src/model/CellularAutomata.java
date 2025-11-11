//Lance Nelkin

package model;

import java.util.List;

public abstract class CellularAutomata {

    protected List<Integer> livableNeighborCount;
    protected List<Integer> spawnableNeighborCount;

    public CellularAutomata() {}

    public int[][] updateGrid(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];

        // pass 1 to mark what should live or die
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {

                int value = grid[y][x];
                int neighbors = getNeighborCount(grid, x, y);
                Cell cell = Cell.fromValue(value);

                //TODO - ugly and shannon would kill us so maybe make prettier soon...
                if (cell == Cell.ALIVE) {
                    if (!livableNeighborCount.contains(neighbors)) {
                        newGrid[y][x] = Cell.DYING_SOON.getDisplayValue();
                    } else {
                        newGrid[y][x] = Cell.ALIVE.getDisplayValue();
                    }

                } else if (cell == Cell.DEAD) {
                    if (spawnableNeighborCount.contains(neighbors)) {
                        newGrid[y][x] = Cell.SPAWNING_SOON.getDisplayValue();
                    } else {
                        newGrid[y][x] = Cell.DEAD.getDisplayValue();
                    }

                } else {
                    // carry over value from original grid if it's not DEAD or ALIVE
                    newGrid[y][x] = value;
                }
            }
        }

        // pass 2 to update temp cells
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {

                Cell cell = Cell.fromValue(newGrid[y][x]);

                if (cell == Cell.DYING_SOON) {
                    newGrid[y][x] = Cell.DEAD.getDisplayValue();

                } else if (cell == Cell.SPAWNING_SOON) {
                    newGrid[y][x] = Cell.ALIVE.getDisplayValue();
                }
            }
        }

        return newGrid;
    }

    public int getNeighborCount(int[][] grid, int x, int y) {
        //TODO - probably should rename these because directionX and directionY, and newX and newY
        // being shortened that much is bad for a clean code grade
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;

        // Check all 8 neighbors (MOOSE neighborhood)
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue; // skip self
                int nx = x + dx;
                int ny = y + dy;

                // check if it's within the grid, so it doesn't give IndexOutOfBoundsException pain
                if (nx >= 0 && nx < cols && ny >= 0 && ny < rows) {
                    Cell neighbor = Cell.fromValue(grid[ny][nx]);
                    if (neighbor == Cell.ALIVE || neighbor == Cell.DYING_SOON) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}