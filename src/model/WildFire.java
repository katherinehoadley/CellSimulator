package model;

import java.util.ArrayList;
import java.util.Random;

public class WildFire extends GamePreset {

    private final ArrayList<TimePos> timeBurnMap = new ArrayList<>();
    private record TimePos(int time, int y, int x) {}

    private final int maxBurnTime = 1;
    private final double spreadChance = 0.4;

    @Override
    public int[][] updateGrid(int[][] grid) {
        super.updateGrid(grid);
        
        for (int i = 0; i < grid.length; i++){
            System.arraycopy(grid[i], 0, newGrid[i], 0, grid.length);
        }

        // clean??? :O

        updateBurningTrees(grid);

        spreadFire(grid);

        return newGrid;
    }

    private void spreadFire(int[][] oldGrid) {
        int rows = oldGrid.length;
        int cols = oldGrid[0].length;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {

                if (oldGrid[y][x] != Cell.BURNING_TREE.getDisplayValue())
                    continue;

                // Changed ugly loop into just up down left right
                trySpreadTo(oldGrid, y - 1, x);
                trySpreadTo(oldGrid, y + 1, x);
                trySpreadTo(oldGrid, y, x - 1);
                trySpreadTo(oldGrid, y, x + 1);
            }
        }
    }

    private void trySpreadTo(int[][] oldGrid, int newY, int newX) {
        if (newY < 0 || newY >= oldGrid.length) return;
        if (newX < 0 || newX >= oldGrid[0].length) return;

        Cell neighbor = Cell.fromValue(oldGrid[newY][newX]);
        if (neighbor != Cell.ALIVE_TREE)
            return;

        Random random = new Random();
        if (random.nextDouble() > spreadChance)
            return;

        newGrid[newY][newX] = Cell.BURNING_TREE.getDisplayValue();
        timeBurnMap.add(new TimePos(0, newY, newX));
    }

    // Go through each value of timeBurnMap and add 1 to time. If it exceeded the time limit, set 
    // it to dead tree. I couldn't get this to work with normal for loops so Iterator it is.
    private void updateBurningTrees(int[][] grid) {
        //Cant use Iterator because it is not safe from ConcurrentModification.
        ArrayList<TimePos> newBurnMap = new ArrayList<>();

        for (TimePos pos : timeBurnMap) {
            if (pos.time() >= maxBurnTime) {
                newGrid[pos.y()][pos.x()] = Cell.DEAD_TREE.getDisplayValue();
            } else {
                newBurnMap.add(new TimePos(pos.time() + 1, pos.y(), pos.x()));
            }
        }

        timeBurnMap.clear();
        timeBurnMap.addAll(newBurnMap);
    }


    @Override
    public String getPreset() {
        return "WildFire";
    }
    
    // Anna:
    @Override
    public String getThirdVariableLabel() {
    	return "Tree Density";
    }
    
    @Override
    public String getFourthVariableLabel() {
    	return "Burning Trees";
    }
}
