//Lance Nelkin

package model;

public enum Cell {

    DEAD(0),
    ALIVE(1),
    ALIVE_TREE(2),
    BURNING_TREE(3),
    DEAD_TREE(4),
    GROUND(5),
    ROCK(6),  
    PAPER(7),
    SCISSORS(8);

    private final int displayValue;

    Cell(int displayName) {
        this.displayValue = displayName;
    }

    public int getDisplayValue() {
        return displayValue;
    }

    public static Cell fromValue(int grid) {
        for (Cell mode : values()) {
            if (mode.displayValue == grid) {
                return mode;
            }
        }
        return DEAD;
    }
}
