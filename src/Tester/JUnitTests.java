//Katherine Hoadley
package Tester;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.Cell;
import model.Initialization;

public class JUnitTests {
	
	//tests that the initialization actually works with correct number of rows/columns
	@Test
    public void testInitializeGridDimensions() {
        Initialization init = new Initialization();
        int rows = 6;
        int cols = 8;

        int[][] grid = init.initializeGrid(rows, cols, .3);

        assertNotNull(grid, "Grid should not be null");
        assertEquals(rows, grid.length, "Grid should have the correct number of rows");
        assertEquals(cols, grid[0].length, "Grid should have the correct number of columns");
    }
	
	//tests that display values are accurate to enum
    @Test
    public void testGetDisplayValue() {
        assertEquals(0, Cell.DEAD.getDisplayValue());
        assertEquals(1, Cell.ALIVE.getDisplayValue());
        assertEquals(2, Cell.SPAWNING_SOON.getDisplayValue());
        assertEquals(3, Cell.DYING_SOON.getDisplayValue());
    }
    
    //tests enum values are accurate
    @Test
    public void testEnumValue() {
        assertEquals(Cell.DEAD, Cell.fromValue(0));
        assertEquals(Cell.ALIVE, Cell.fromValue(1));
        assertEquals(Cell.SPAWNING_SOON, Cell.fromValue(2));
        assertEquals(Cell.DYING_SOON, Cell.fromValue(3));

        // test invalid value returns DEAD
        assertEquals(Cell.DEAD, Cell.fromValue(99));
    }

    //tests initialization spawn rate is accurate in a 5x5
    @Test
    public void testSpawnRateIsAccurate() {
        Initialization init = new Initialization();
        int rows = 5;
        int cols = 5;
        double aliveRate = .3;

        int[][] grid = init.initializeGrid(rows, cols, aliveRate);
        
        int aliveCount = 0;
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == Cell.ALIVE.getDisplayValue()) {
                    aliveCount++;
                }
            }
        }
        
        assertTrue(aliveCount >= 1 && aliveCount <= 5, "Alive count is accurate.");
        
    }

}
