package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class WildFire extends GamePreset {
  
    private final ArrayList<TimePos> timeBurnMap = new ArrayList<>();
    private record TimePos(int time, int x, int y){}

    private final int maxBurnTime = 1;
    private final double spreadChance = 0.4;

    //////////////////////////////
    //TODO - move to initialization (KAATTTTTTTTTTTTT):
    private double forestDensity = 1.0;
    private double spawnAsBurnChance = 1.0;
    //////////////////////////////

    @Override
    public int[][] updateGrid(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];

        updateBurningTrees(newGrid);

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
        Random random = new Random();

        if (originalValue != Cell.BURNING_TREE.getDisplayValue()) return originalValue;

        //WildFire core neighbor loop logic. Check + of neighbors (not middle)
        for (int directionY = -1; directionY <= 1; directionY++) {
            for (int directionX = -1; directionX <= 1; directionX++) {
                if (Math.abs(directionX) == Math.abs(directionY)) continue;

                int newX = x + directionX;
                int newY = y + directionY;

                // check if it's within the grid, so it doesn't give IndexOutOfBoundsException pain
                if (newX >= 0 && newX < cols && newY >= 0 && newY < rows) {

                    Cell neighbor = Cell.fromValue(grid[newY][newX]);

                    if (neighbor == Cell.ALIVE_TREE) {

                        //If its above the spread chance, turn the neighbor into a burning tree.
                        if (random.nextDouble() >= spreadChance) {
                            timeBurnMap.add(new TimePos(0, newX, newY));
                            grid[newX][newY] = Cell.BURNING_TREE.getDisplayValue();
                        }
                    }
                }
            }
        }

        return originalValue;
    }

    // Go through each value of timeBurnMap and add 1 to time. If it exceeded the time limit, set 
    // it to dead tree. I couldn't get this to work with normal for loops so Iterator it is.
    private void updateBurningTrees(int[][] grid){
        Iterator<TimePos> iterator = timeBurnMap.iterator();

        while (iterator.hasNext()) {
            TimePos pos = iterator.next();

            if (pos.time() >= maxBurnTime) {
                iterator.remove();
                grid[pos.x][pos.y] = Cell.DEAD_TREE.getDisplayValue();
                continue;
            }

            int newTime = pos.time() + 1;
            int x = pos.x();
            int y = pos.y();

            iterator.remove();
            timeBurnMap.add(new TimePos(newTime, x, y));
        }
    }

    @Override
    public String getPreset() {
        return "WildFire";
    }
}
