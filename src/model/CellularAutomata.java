//Lance Nelkin

package model;

import data.GamePreset;

import java.util.List;

public abstract class CellularAutomata implements GamePreset {

    protected List<Integer> livableNeighborCount;
    protected List<Integer> spawnableNeighborCount;

    public int[][] updateGrid(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];

        // pass 1 to mark what should live or die
        for (int y = 1; y < rows-1; y++) {
            for (int x = 1; x < cols-1; x++) {

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
        for (int y = 1; y < rows-1; y++) {
            for (int x = 1; x < cols-1; x++) {

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
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;

        // Check all 8 neighbors (MOOSE neighborhood)
        for (int directionY = -1; directionY <= 1; directionY++) {
            for (int directionX = -1; directionX <= 1; directionX++) {
                if (directionX == 0 && directionY == 0) continue; // skip self
                int newX = x + directionX;
                int newY = y + directionY;

                // check if it's within the grid, so it doesn't give IndexOutOfBoundsException pain
                if (newX >= 0 && newX < cols && newY >= 0 && newY < rows) {
                    Cell neighbor = Cell.fromValue(grid[newY][newX]);
                    if (neighbor == Cell.ALIVE || neighbor == Cell.DYING_SOON) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
