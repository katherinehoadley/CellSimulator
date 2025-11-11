package view;

import java.util.Arrays;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Cell;
import model.CellularAutomata;
import model.Initialization;

public class GridUI extends BaseUI {

	private final Color ALIVE_COLOR = Color.AQUA; 
	private final Color DEAD_COLOR = Color.DARKGREY;
	private final int CELL_SIZE = 10;
	private final int SIMULATION_SPEED = 1;
	
    private int[][] grid;
    private Rectangle[][] visualizer = new Rectangle[][] {};
    private CellularAutomata automata;
    private Initialization initialization = new Initialization();
    
    private int numCols;
    private int numRows;

    
    public GridUI() { }
    
//    public GridUI(int rows, int cols) {
//        setGridDimensions(rows, cols);
//    }
    
    
    public GridPane startGrid (int rows, int columns) {
    		grid = initialization.initializeGrid(rows, columns);
    		setGridDimensions(grid, rows, columns);
    		updateVisuals();
    		return render(rows, columns);
    }
//    public GridPane drawGrid(int[][] grid, int rows, int columns) {
//    		for (int y = 0; y < rows; y++) {
//    			for (int x = 0; x< columns; x++) {
//    				 int value = grid[y][x];
//    				 Cell cell = Cell.fromValue(value);
//    				 if (value == Cell.ALIVE.getDisplayValue()) {
//    					 this.visualizer[y][x].setFill(ALIVE_COLOR);
//    				 } else {
//    	                 this.visualizer[y][x].setFill(DEAD_COLOR);
//    				 }
//    			}
//    		}
//    		System.out.println("yes it worked");
//    		return render(rows, columns);
//    }
    
    
//    private void randomizeCellState(int x, int y) {
//    	Random rand = new Random();
//    			
//		if (rand.nextDouble() < 0.2) { 
//            this.cells[y][x] = Cell.ALIVE.getDisplayValue();
//        } 
//        else {
//            this.cells[y][x] = Cell.DEAD.getDisplayValue();
//        }
//    }
    
    private Rectangle createCell() {
        Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
        rect.setStroke(Color.GRAY);
        rect.setStrokeWidth(0.5);
        return rect;
    }
    
    public void updateVisuals() {
        
        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                if (this.visualizer[y][x] == null) {
                	continue; 
                }
                
                Cell cellState = Cell.fromValue(this.grid[y][x]);
                
                if (cellState == Cell.ALIVE) {
                    this.visualizer[y][x].setFill(ALIVE_COLOR);
                } 
                else {
                    this.visualizer[y][x].setFill(DEAD_COLOR);
                }
            }
        }
    }
    
    
    public void setGridDimensions(int[][] grid, int rows, int cols) {
        this.numRows = Math.max(rows, 10); 
        this.numCols = Math.max(cols, 10);
        
        this.visualizer = new Rectangle[numRows][numCols];

        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                Rectangle rect = createCell();
                this.visualizer[y][x] = rect;
            }
        }
        updateVisuals();
    }
    
//    public void start() {  
//    	System.out.println("Start pressed (no timer implemented).");
//    }
//    
//    public void pause() { 
//    	System.out.println("Pause pressed (no timer implemented).");
//    }
    
    public boolean isRunning() {
        return false;
    }
    
    private GridPane render(int rows, int columns) { 
    	GridPane gridPane = new GridPane();
        gridPane.setHgap(0);
        gridPane.setVgap(0);
        gridPane.setAlignment(Pos.CENTER); 

        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                if (this.visualizer[y][x] != null) {
                	
                	// Adds Rectangle to GridPane position
                    gridPane.add(this.visualizer[y][x], x, y);
                }
            }
        }
        
        return gridPane;
    }
    
    public int[][] getGrid() {
    	return grid;
    }
    
//    public void redraw() {
//        updateVisuals();
//    }
    
//    public void step() { 
//        System.out.println("Step pressed (no logic implemented yet).");
//    }
}