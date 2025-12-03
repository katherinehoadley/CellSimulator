// Johnathan Meeks
package view;

import java.util.HashMap;

import controller.Initialization;
import controller.InitializationFactory;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Cell;

public class GridUI {  
	private final Color ALIVE_COLOR = Color.AQUA; 
	private final Color DEAD_COLOR = Color.DARKGREY;
	private final Color ROCK_COLOR = Color.RED;
	private final Color PAPER_COLOR = Color.WHITE;
	private final Color SCISSORS_COLOR = Color.BLUE;
	private final Color ALIVE_TREE_COLOR = Color.GREEN;
	private final Color BURNING_TREE_COLOR = Color.rgb(242, 90, 19);
	private final Color DEAD_TREE_COLOR = Color.rgb(97, 85, 72);
	private final Color GROUND_COLOR = Color.OLIVE;


	private final int CELL_SIZE = 10;

    private int[][] grid;
    private Rectangle[][] visualizer;
	protected InitializationFactory initFactory = new InitializationFactory();
    private Initialization initialization;
    
    private int numCols;
    private int numRows;

    
    public GridUI() { }
    

    public GridPane startGrid (String choice, double[] values) {
		this.initialization = initFactory.getInitialization("Initialization"+choice, values);
		System.out.println("Initialization: " + initialization);
    		grid = initialization.initializeGrid();
    		setGridDimensions(grid, values[1], values[0]);
    		updateVisuals();
    		return render(values[1], values[0]);
    }

	/**
	 * Method:   createCell()
	 * @param:   none 
	 * @return:   Rectangle object
	 
	 * create a new cell to be attached to the GridPane created inside
	 * the function render().
	 */
    private Rectangle createCell() {
        Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
        rect.setStroke(Color.GRAY);
        rect.setStrokeWidth(0.5);
        return rect;
    }
    

	/**
	 * Method:   createCell()
	 * @param:   none 
	 * @return:   none 
	 
	 * Update the visual display of the grid based on the Cell enum value and 
	 * the color associated with it in the HashMap.
	 */
    public void updateVisuals() {
        HashMap<Cell, Color> map = new HashMap<>();
        map.put(Cell.ALIVE, ALIVE_COLOR);
        map.put(Cell.DEAD, DEAD_COLOR);
        map.put(Cell.ROCK, ROCK_COLOR);
        map.put(Cell.PAPER, PAPER_COLOR);
        map.put(Cell.SCISSORS, SCISSORS_COLOR);
        map.put(Cell.ALIVE_TREE, ALIVE_TREE_COLOR);
        map.put(Cell.BURNING_TREE, BURNING_TREE_COLOR);
        map.put(Cell.DEAD_TREE, DEAD_TREE_COLOR);
        map.put(Cell.GROUND, GROUND_COLOR);


    	
        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                if (this.visualizer[y][x] == null) {
                	continue; 
                }
                
                Cell cellState = Cell.fromValue(this.grid[y][x]);
                this.visualizer[y][x].setFill(map.get(cellState));
            }
        }
    }
    
    /**
	 * Method:   setGridDimensions()
	 * @param:   int[][] grid, double rows, double cols
	 * @return:   none
	 
	 * sets the numRows and numCols variable, and then loops
	 * to create Rectangle objects for each cell in the visualizer.
	 */
    public void setGridDimensions(int[][] grid, double rows, double cols) { 
        this.numRows = Math.max((int)rows, 5); 
        this.numCols = Math.max((int)cols, 5);
        
        this.visualizer = new Rectangle[numRows][numCols];

        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                Rectangle rect = createCell();
                this.visualizer[y][x] = rect;
            }
        }
        updateVisuals();
    }

	/**
	 * Method:   render()
	 * @param:   double rows, double columns
	 * @return:   GridPane object
	 
	 * Takes the Rectangle cells we created via setGridDimensions() and renders them within
	 * a GridPane object. 
	 */
    public GridPane render(double rows, double columns) { 
    	GridPane gridPane = new GridPane();
    	gridPane.setHgap(0);
    	gridPane.setVgap(0);
    	gridPane.setAlignment(Pos.CENTER); 

    	/**
    	 * Loop through and add rectangle objects to the gridPane (visualize the simulator).
    	 */
    	for (int y = 0; y < numRows; y++) {
    		for (int x = 0; x < numCols; x++) {
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
