package screens;

import automata.CellularAutomata;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import world.Cell;

public class GameUI extends BaseUI {

	private final Color ALIVE_COLOR = Color.AQUA; 
	private final Color DEAD_COLOR = Color.DARKGREY;
	
    private byte[][] cells;
    private Rectangle[][] renderedCells;
    private CellularAutomata automata;
    private int simulationSpeed;
    private WindowDimensions window;

    
    public GameUI() { }
    
    
    private void setCellSize() { } 
    private void setGridDimensions(int x, int y) { }
    private void setSimulationSpeed() { }
    
    
    // Space key
    public void start() { }
    // P key
    public void pause() { } 
    // will render the grid and tie together all private functions to 
    // visually display the cells
    public void render() { }
    public void step() { } 
}
