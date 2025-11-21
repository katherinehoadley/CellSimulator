//Lance Nelkin

package model;

import java.util.List;

public class GameOfLife extends GamePreset {

    protected List<Integer> livableNeighborCount;
    protected List<Integer> spawnableNeighborCount;

    public GameOfLife() {
        this.livableNeighborCount = List.of(2,3);
        this.spawnableNeighborCount = List.of(3);
    }

    public GameOfLife(List<Integer> livable, List<Integer> spawnable) {
        this.livableNeighborCount = livable;
        this.spawnableNeighborCount = spawnable;
    }


    @Override
    public int[][] updateGrid(int[][] grid) {
        super.updateGrid(grid);
        for (int y = 1; y < rows - 1; y++) {
            for (int x = 1; x < cols - 1; x++) {

                Cell cell = Cell.fromValue(grid[y][x]);
                newGrid[y][x] = updateCell(grid, x, y, cell);
            }
        }
        return newGrid;
    }

    public int updateCell(int[][] grid, int x, int y, Cell cell) {
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

        //This else if chain will never grow, so it's ok.
        if (cell == Cell.ALIVE) {
            // dies unless neighbor count is in livable list
            return livableNeighborCount.contains(count)
                    ? Cell.ALIVE.getDisplayValue() : Cell.DEAD.getDisplayValue();

        } else if (cell == Cell.DEAD) {
            // spawns if in spawnable list
            return spawnableNeighborCount.contains(count)
                    ? Cell.ALIVE.getDisplayValue() : Cell.DEAD.getDisplayValue();

        } else {
            // carry over value from original grid if it's not DEAD or ALIVE
            return grid[y][x];
        }
    }

    public String getPreset() {
        return "Bacteria";
    }
}
