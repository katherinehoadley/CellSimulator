package model;

import java.util.EnumMap;
import java.util.Map;

public class RockPaperScissorsAutomata extends GamePreset{

    @Override
    public int[][] updateGrid(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];

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

        //made a map to be more clean since it was looking ugly
        //TODO - might need optimization because this makes a whole new Map every cell check.
        // This is a LOT of GC overhead and object creation destruction stuff but I will just
        // hope that it is fine. If its not, this is becoming static final (constant)
        Map<Cell, Integer> counts = new EnumMap<>(Cell.class);
        counts.put(Cell.ROCK, 0);
        counts.put(Cell.PAPER, 0);
        counts.put(Cell.SCISSORS, 0);

        //RPS core loop logic. Check 3x3 of neighbors and find out which Cell type theres most of.
        for (int directionY = -1; directionY <= 1; directionY++) {
            for (int directionX = -1; directionX <= 1; directionX++) {

                //TODO - should this skip the center cell or not?
//                if (directionX == 0 && directionY == 0) continue;

                int newX = x + directionX;
                int newY = y + directionY;

                // check if it's within the grid, so it doesn't give IndexOutOfBoundsException pain
                if (newX >= 0 && newX < cols && newY >= 0 && newY < rows) {

                    Cell neighbor = Cell.fromValue(grid[newY][newX]);

                    if (counts.containsKey(neighbor)) {
                        counts.put(neighbor, counts.get(neighbor) + 1);
                    }
                }
            }
        }

        Cell winner = getWinningCell(counts);
        if (winner == null) {
            return originalValue; // tie
        }

        return winner.getDisplayValue();
    }

    private Cell getWinningCell(Map<Cell, Integer> counts) {
        int maxCount = -1;
        Cell maxCell = null;
        boolean tie = false;

        for (Map.Entry<Cell, Integer> entry : counts.entrySet()) {
            int count = entry.getValue();
            if (count > maxCount) {
                maxCount = count;
                maxCell = entry.getKey();
                tie = false;
            } else if (count == maxCount) {
                tie = true;
            }
        }

        if (tie) {
            return null;
        }

        return maxCell;
    }

    @Override
    public String getPreset() {
        return "RPS";
    }
}
