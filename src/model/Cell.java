//Lance Nelkin

package model;

public enum Cell {

    DEAD(0),
    ALIVE(1),
    SPAWNING_SOON(2),
    DYING_SOON(3);

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
