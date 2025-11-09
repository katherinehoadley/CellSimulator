//Katherine Hoadley
package view;

import world.Cell;
import world.Step;
import world.StepGameOfLife;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import automata.Bacteria;
import automata.CellularAutomata;

public class Initialization {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);	//lance says this ok. kat disagrees.
    private int[][] grid;
    private ControlPanel controlPanel;

    
    public Initialization(ControlPanel controlPanel) {
    	this.controlPanel = controlPanel;
    }

    //Might be moved to HomeUI?

    public void initializeMap(){
    	int rows = controlPanel.getRows();
    	int cols = controlPanel.getColumns();
    	
    	grid = initializeGrid(rows, cols);
    	
        new GameUI().setGridDimensions(0, 0);
        
        CellularAutomata automata = new Bacteria();
        StepGameOfLife stepper = new StepGameOfLife(grid);
        
        GameUI ui = new GameUI(stepper.getGrid());

        // Start tick loop (currently its 10 times per second, this can change)
        scheduler.scheduleAtFixedRate(step::update, 0, 100, TimeUnit.MILLISECONDS);
    }
    
    private int[][] initializeGrid(int rows, int cols){
    	int[][] newGrid = new int[rows][cols];
    	
    	for (int y = 0; y < rows; y++) {
    		for (int x = 0; x < cols; x++) {
    			double randomValue = Math.random();
    			
    			if(randomValue < 0.2) {
    				newGrid[y][x] = Cell.ALIVE.getDisplayValue();
    			}
    			else {
    				newGrid[y][x] = Cell.DEAD.getDisplayValue();
    			}
    		}
    	}
    	return newGrid;
    }

}
