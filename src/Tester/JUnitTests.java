//Katherine Hoadley
package Tester;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import controller.Initialization;
import controller.InitializationGameOfLife;
import controller.InitializationRockPaperScissors;
import controller.InitializationWildFire;
import model.Cell;

public class JUnitTests {
	
    private Initialization getInitializationGOL(int rows, int cols, double aliveChance) {
        double[] values = { cols, rows, aliveChance };
        return new InitializationGameOfLife(values);
    }

    private Initialization getInitializationRPS(int rows, int cols) {
        double[] values = { cols, rows };
        return new InitializationRockPaperScissors(values);
    }

    private Initialization getInitializationWF(int rows, int cols, double treeDensity, double burnChance) {
        double[] values = { cols, rows, treeDensity, burnChance };
        return new InitializationWildFire(values);
    }
	
	//tests that the initialization actually works with correct number of rows/columns
	@Test
    public void testInitializeGridDimensions() {
        int rows = 6;
        int cols = 8;

        Initialization init = getInitializationGOL(rows, cols, 0.3);
        int[][] grid = init.initializeGrid();
        
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
        int rows = 5;
        int cols = 5;
        double aliveRate = .3;

        Initialization init = getInitializationGOL(rows, cols, aliveRate);
        int[][] grid = init.initializeGrid();
        
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
        int rows = 50;
        int cols = 50;

        Initialization init = getInitializationRPS(rows, cols);
        int[][] grid = init.initializeGrid();
        
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
    
    //ensures the assigned burning trees amount is the same as the actual value of burning trees
    @Test
    public void wildfireBurningTreeCount() {
    	int rows = 50;
    	int cols = 50;
    	
    	int assignedBurningTreeCount = 10;
    	
        Initialization init = getInitializationWF(rows, cols, .5, 10);
        int[][] grid = init.initializeGrid();
        
        int burningTreeCount = 0;
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == Cell.BURNING_TREE.getDisplayValue()) {
                	burningTreeCount++;
                }
            }
        }
        
        assertEquals(burningTreeCount, assignedBurningTreeCount);
    }
    

    //ensures that when too many burning trees are inputed, the burning tree count defaults
    //to the maxed-out value of available tree cells.
    @Test
    public void wildfireBurningTreeOver() {
    	int rows = 20;
    	int cols = 20;
    	int assignedBurningTrees = 400;
        Initialization init = getInitializationWF(rows, cols, 1.0, assignedBurningTrees);
        int burningTreeCount = 0;
        int aliveTreeCount = 0;
        int[][] grid = init.initializeGrid();
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == Cell.BURNING_TREE.getDisplayValue()) {
                	burningTreeCount++;
                }
                if (grid[r][c] == Cell.ALIVE_TREE.getDisplayValue()) {
                	aliveTreeCount++;
                }
            }
        
        }
        
        System.out.println("Burning: " + burningTreeCount);
        System.out.println("Alive: " + aliveTreeCount);

        assertTrue(burningTreeCount > aliveTreeCount && aliveTreeCount == 0);
      
    }
    

}
