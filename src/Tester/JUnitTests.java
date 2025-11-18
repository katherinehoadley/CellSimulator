//Katherine Hoadley
package Tester;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.Cell;
import model.Initialization;
import model.InitializationGameOfLife;
import model.InitializationRockPaperScissors;

public class JUnitTests {
	
	private Initialization getInitializationGOL() {
		return new InitializationGameOfLife();
	}
	
	private Initialization getInitializationRPS() {
		return new InitializationRockPaperScissors();
	}
	
	//tests that the initialization actually works with correct number of rows/columns
	@Test
    public void testInitializeGridDimensions() {
        Initialization init = getInitializationGOL();
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
    }
    
    //tests enum values are accurate
    @Test
    public void testEnumValue() {
        assertEquals(Cell.DEAD, Cell.fromValue(0));
        assertEquals(Cell.ALIVE, Cell.fromValue(1));

        // test invalid value returns DEAD
        assertEquals(Cell.DEAD, Cell.fromValue(99));
    }

    //tests initialization spawn rate is accurate in a 5x5 
    @Test
    public void testSpawnRateIsAccurate() {
        Initialization init = getInitializationGOL();
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
    
    //tests fair split among r,p,s (within 3%) in a 50x50 example
    @Test
    public void testRPSRate() {
        Initialization init = getInitializationRPS();
        int rows = 50;
        int cols = 50;
        int[][] grid = init.initializeGrid(rows, cols, 1);
        
        int rockCount = 0;
        int paperCount = 0;
        int scissorsCount = 0;
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == Cell.ROCK.getDisplayValue()) {
                	rockCount++;
                }
                if (grid[r][c] == Cell.PAPER.getDisplayValue()) {
                	paperCount++;
                }
                if (grid[r][c] == Cell.SCISSORS.getDisplayValue()) {
                	scissorsCount++;
                }
            }
        }
        
        System.out.println("R = " + rockCount + " P = " + paperCount + " S = " + scissorsCount);
        System.out.println((paperCount - rockCount));
        System.out.println((.03*rockCount));
        assertTrue(rockCount == scissorsCount && (paperCount - rockCount) <= (.03*rockCount));
    }

}
