//Lance Nelkin

package model;

import java.util.List;

public class Bacteria extends CellularAutomata {

    public Bacteria() {
        this.livableNeighborCount = List.of(2, 3);
        this.spawnableNeighborCount = List.of(3);
    }

    @Override
    public String getPreset() {
        return "Bacteria";
    }
}
