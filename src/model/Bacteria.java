//Lance Nelkin

package model;

import java.util.List;

public class Bacteria extends CellularAutomata {

    public Bacteria() {
        super();
        //TODO - thise values might become a constant of this class since Bacteria / Conway will always be 2/3
        this.livableNeighborCount = List.of(2, 3);
        this.spawnableNeighborCount = List.of(3);
    }
}
