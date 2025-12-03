//Katherine Hoadley
//This class contains a variety of junit tests to ensure the code is
//working properly and correctly updating cells
package Tester;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import controller.Initialization;
import controller.InitializationGameOfLife;
import controller.InitializationRockPaperScissors;
import controller.InitializationWildFire;
import model.Cell;
import model.GameOfLife;
import model.RockPaperScissors;

public class JUnitTests {
	
	//retrieve initialized grids
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
	
	//tests that the initialization actually works with correct number of rows/columns in GOL
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
        assertEquals(2, Cell.ALIVE_TREE.getDisplayValue());
        assertEquals(3, Cell.BURNING_TREE.getDisplayValue());
        assertEquals(4, Cell.DEAD_TREE.getDisplayValue());
        assertEquals(5, Cell.GROUND.getDisplayValue());
        assertEquals(6, Cell.ROCK.getDisplayValue());
        assertEquals(7, Cell.PAPER.getDisplayValue());
        assertEquals(8, Cell.SCISSORS.getDisplayValue());

    }
    
    //tests enum values are accurate
    @Test
    public void testEnumValue() {
        assertEquals(Cell.DEAD, Cell.fromValue(0));
        assertEquals(Cell.ALIVE, Cell.fromValue(1));
        assertEquals(Cell.ALIVE_TREE, Cell.fromValue(2));
        assertEquals(Cell.BURNING_TREE, Cell.fromValue(3));
        assertEquals(Cell.DEAD_TREE, Cell.fromValue(4));
        assertEquals(Cell.GROUND, Cell.fromValue(5));
        assertEquals(Cell.ROCK, Cell.fromValue(6));
        assertEquals(Cell.PAPER, Cell.fromValue(7));
        assertEquals(Cell.SCISSORS, Cell.fromValue(8));

        // test invalid value returns DEAD
        assertEquals(Cell.DEAD, Cell.fromValue(99));
    }

    //tests initialization spawn rate is accurate in a 5x5 in GOL
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
    
    //if a cell has more than three neighbors, it dies
    @Test
    public void testCellDiesWithTooManyNeighbors() {
        GameOfLife gol = new GameOfLife();

        int[][] grid = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
        };

        int result = gol.updateCell(grid, 1, 1, Cell.ALIVE);

        assertEquals(
            Cell.DEAD.getDisplayValue(),
            result,
            "Live cell with more than 3 neighbors should die"
        );
    }
    
    //if a dead cell has three neighbors it becomes alive
    @Test
    public void testCellSpawnsWithThreeNeighbors() {
        GameOfLife gol = new GameOfLife();

        int[][] grid = {
            {0, 1, 0},
            {1, 0, 0},
            {0, 1, 0}
        };

        int result = gol.updateCell(grid, 1, 1, Cell.DEAD);

        assertEquals(
            Cell.ALIVE.getDisplayValue(),
            result,
            "An empty cell with three neighbors should become alive."
        );
    }
    
    //if a cell has no alive cells next to it it dies of loneliness
    @Test
    public void testCellHasNoAliveNeighbors() {
        GameOfLife gol = new GameOfLife();

        int[][] grid = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
        };

        int result = gol.updateCell(grid, 1, 1, Cell.ALIVE);

        assertEquals(
            Cell.DEAD.getDisplayValue(),
            result,
            "An alive cell with no alive neighbors should die."
        );
    }
    
    //if a cell has only one alive cell next to it it dies of loneliness
    @Test
    public void testCellHasOneAliveNeighbor() {
        GameOfLife gol = new GameOfLife();

        int[][] grid = {
            {0, 0, 0},
            {1, 1, 0},
            {0, 0, 0}
        };

        int result = gol.updateCell(grid, 1, 1, Cell.ALIVE);

        assertEquals(
            Cell.DEAD.getDisplayValue(),
            result,
            "An alive cell with one alive neighbor should die."
        );
    }
    
    //if scissors is at or above threshold, scissors wins
    @Test
    public void testScissorsWins() {
    	RockPaperScissors rps = new RockPaperScissors();
        
    	int[][] grid = {
                {6, 6, 8},
                {8, 7, 6},
                {7, 8, 7}
        };
    	int result = rps.updateCell(grid, 1, 1, 7);
    	
        assertEquals(
                Cell.SCISSORS.getDisplayValue(),
                result,
                "Paper should lose to scissors."
            );
    	
    }
    
    //if rock is at or above threshold, rock wins
    @Test
    public void testRockWins() {
    	RockPaperScissors rps = new RockPaperScissors();
        
    	int[][] grid = {
                {6, 6, 7},
                {7, 8, 6},
                {8, 7, 8}
        };
    	
    	int result = rps.updateCell(grid, 1, 1, 8);
    	
        assertEquals(
                Cell.ROCK.getDisplayValue(),
                result,
                "Scissors should lose to rock."
        );
    	
    }
    
    //if paper is at or above threshold, paper wins
    @Test
    public void testPaperWins() {
    	RockPaperScissors rps = new RockPaperScissors();
        
    	int[][] grid = {
                {8, 8, 7},
                {7, 6, 8},
                {6, 7, 6}
        };
    	
    	int result = rps.updateCell(grid, 1, 1, 6);
    	
        assertEquals(
                Cell.PAPER.getDisplayValue(),
                result,
                "Rock should lose to paper."
        );
    }

}
