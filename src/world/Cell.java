package world;

public enum Cell {

    DEAD((byte) 0),
    ALIVE((byte) 1),
    SPAWNING_SOON((byte) 2),
    DYING_SOON((byte) 3);

    private final byte displayValue;

    Cell(byte displayName) {
        this.displayValue = displayName;
    }

    public byte getDisplayValue() {
        return displayValue;
    }

    public static Cell fromValue(byte value) {
        for (Cell mode : values()) {
            if (mode.displayValue == value) {
                return mode;
            }
        }
        return DEAD;
    }
}
