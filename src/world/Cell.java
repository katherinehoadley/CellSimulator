//Lance Nelkin

package world;

public enum Cell {

    DEAD((int) 0),
    ALIVE((int) 1),
    SPAWNING_SOON((int) 2),
    DYING_SOON((int) 3);

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
