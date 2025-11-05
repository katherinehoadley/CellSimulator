package automata;

import java.util.List;

public abstract class CellularAutomata {

    protected List<Integer> livableNeighborCount;

    public CellularAutomata(){}


    //TODO - this function will be responsible for going through the whole dataset / grid and checking for
    // which ones should be newly spawned in. It should replace those tiles with "Temp" or some value for temp.
    public abstract void updateNeighbors();

    //TODO - This function will be responsible for going through the whole dataset / grid and it will write to
    // kill the alive cells that are too crowded or lonely, and THEN it will turn temp values alive.
    public abstract void writeNeighbors();

    public int getNeighborCount(int[][] grid, int x, int y){
        //TODO - get neighbor count. Always use VON NEUMAN method

        return 0;
    }

}
