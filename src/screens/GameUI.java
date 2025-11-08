package screens;

import java.util.Random;

import automata.CellularAutomata;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import world.Cell;

public class GameUI extends BaseUI {

	private final Color ALIVE_COLOR = Color.AQUA; 
	private final Color DEAD_COLOR = Color.DARKGREY;
	private final int CELL_SIZE = 10;
	private final int SIMULATION_SPEED = 1;
	
    private byte[][] cells;
    private Rectangle[][] visualizer;
    private CellularAutomata automata;
    
    private int numCols;
    private int numRows;

    
    public GameUI() { }
    
    
    private void randomizeCellState(int x, int y) {
    	Random rand = new Random();
    			
		if (rand.nextDouble() < 0.2) { 
            this.cells[y][x] = Cell.ALIVE.getDisplayValue();
        } 
        else {
            this.cells[y][x] = Cell.DEAD.getDisplayValue();
        }
    }
    
    private Rectangle createCell() {
        Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
        rect.setStroke(Color.GRAY);
        rect.setStrokeWidth(0.5);
        return rect;
    }
    
    private void updateVisuals() {
        if (this.cells == null || this.visualizer == null) {
            return; 
        }
        
        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                if (this.visualizer[y][x] == null) {
                	continue; 
                }
                
                Cell cellState = Cell.fromValue(this.cells[y][x]);
                
                if (cellState == Cell.ALIVE) {
                    this.visualizer[y][x].setFill(ALIVE_COLOR);
                } 
                else {
                    this.visualizer[y][x].setFill(DEAD_COLOR);
                }
            }
        }
    }
    
    
    public void setGridDimensions(int rows, int cols) {
        this.numRows = Math.max(rows, 10); 
        this.numCols = Math.max(cols, 10);
        
        this.cells = new byte[numRows][numCols];
        this.visualizer = new Rectangle[numRows][numCols];

        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                Rectangle rect = createCell();
                this.visualizer[y][x] = rect;
                randomizeCellState(x, y);
            }
        }
       
        updateVisuals(); 
    }
    
    public void start() {  
    	System.out.println("Start pressed (no timer implemented).");
    }
    
    public void pause() { 
    	System.out.println("Pause pressed (no timer implemented).");
    }
    
    public boolean isRunning() {
        return false;
    }
    
    public GridPane render() { 
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
        
        updateVisuals(); 
        
        return gridPane;
    }
    
    public void step() { 
        System.out.println("Step pressed (no logic implemented yet).");
    }
}