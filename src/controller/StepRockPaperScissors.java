//Lance Nelkin

package controller;

import model.RockPaperScissorsAutomata;

public class StepRockPaperScissors extends Step {

    public StepRockPaperScissors() {
        this.automata = new RockPaperScissorsAutomata();
    }

    //TODO - I smell duplicated code :)

    @Override
    public void update(int[][] grid){
        int[][] newGrid = automata.updateGrid(grid);

        for (int y = 0; y < grid.length; y++) {
            System.arraycopy(newGrid[y], 0, grid[y], 0, grid[y].length);
        }
    }
}
