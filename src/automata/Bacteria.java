package automata;

import java.util.List;

public class Bacteria extends CellularAutomata {

    public Bacteria() {
        super();
        //TODO - this value might become a constant of this class since Bacteria / Conway will always be 2/3
        this.livableNeighborCount = List.of(2, 3);
    }

    @Override
    public void updateNeighbors() {

    }

    @Override
    public void writeNeighbors() {

    }
}
