//Lance Nelkin

package model;

import java.util.List;

public abstract class CellularAutomata extends GamePreset {

    protected List<Integer> livableNeighborCount;
    protected List<Integer> spawnableNeighborCount;

    @Override
    public int[][] updateGrid(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];

        for (int y = 1; y < rows - 1; y++) {
            for (int x = 1; x < cols - 1; x++) {

                Cell cell = Cell.fromValue(grid[y][x]);
                int neighbors = getNeighborCount(grid, x, y);

                //This else if chain will never grow, so it's ok.
                if (cell == Cell.ALIVE) {
                    // dies unless neighbor count is in livable list
                    newGrid[y][x] = livableNeighborCount.contains(neighbors)
                            ? Cell.ALIVE.getDisplayValue() : Cell.DEAD.getDisplayValue();

                } else if (cell == Cell.DEAD) {
                    // spawns if in spawnable list
                    newGrid[y][x] = spawnableNeighborCount.contains(neighbors)
                            ? Cell.ALIVE.getDisplayValue() : Cell.DEAD.getDisplayValue();

                } else {
                    // carry over value from original grid if it's not DEAD or ALIVE
                    newGrid[y][x] = grid[y][x];
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
                    if (neighbor == Cell.ALIVE) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}

