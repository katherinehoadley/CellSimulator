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

public class GridUI {

	private final Color ALIVE_COLOR = Color.AQUA; 
	private final Color DEAD_COLOR = Color.DARKGREY;
	private final int CELL_SIZE = 10;

    private int[][] grid;
    private Rectangle[][] visualizer = new Rectangle[][] {};
    private Initialization initialization = new Initialization();
    
    private int numCols;
    private int numRows;

    
    public GridUI() { }
    
    public GridPane startGrid (int rows, int columns, double aliveDensity) {
    		grid = initialization.initializeGrid(rows, columns, aliveDensity);
    		setGridDimensions(grid, rows, columns);
    		updateVisuals();
    		return render(rows, columns);
    }
    
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
                    System.out.println(this.visualizer[y][x]);
                } 
                else {
                    this.visualizer[y][x].setFill(DEAD_COLOR);
                }
            }
        }
    }
    
    
    public void setGridDimensions(int[][] grid, int rows, int cols) {
        this.numRows = Math.max(rows, 5); 
        this.numCols = Math.max(cols, 5);
        
        this.visualizer = new Rectangle[numRows][numCols];

        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                Rectangle rect = createCell();
                this.visualizer[y][x] = rect;
            }
        }
        updateVisuals();
    }
    
    public boolean isRunning() {
        return false;
    }
    
    public GridPane render(int rows, int columns) { 
    	GridPane gridPane = new GridPane();
    	gridPane.setHgap(0);
    	gridPane.setVgap(0);
    	gridPane.setAlignment(Pos.CENTER); 

    	for (int y = 0; y < numRows; y++) {
    		for (int x = 0; x < numCols; x++) {
    			// Adds Rectangle to GridPane position
    			gridPane.add(this.visualizer[y][x], x, y);
    		}
    	}

    	return gridPane;
    }

    public int[][] getGrid() {
    	return grid;
    }
    
    // Anna
    public void setGrid(int[][] loadedGrid) {
        this.grid = loadedGrid;
        setGridDimensions(loadedGrid, loadedGrid.length, loadedGrid[0].length);
    }
  
}