package screens;

import automata.CellularAutomata;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import world.Cell;

public class GameUI extends BaseUI {

	private final Color ALIVE_COLOR = Color.AQUA; 
	private final Color DEAD_COLOR = Color.DARKGREY;
	
    private byte[][] cells;
    private Rectangle[][] visualizer;
    private CellularAutomata automata;
    private int simulationSpeed;
    private WindowDimensions window;

    
    public GameUI() { }
    
    
    private void setCellSize() { } 
    private void setGridDimensions(int x, int y) { }
    private void setSimulationSpeed() { }
    private void start() { }
    private void pause() { }
    
    
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            start();
        } 
        else if (event.getCode() == KeyCode.P) {
            pause();
        }
    }
    // will render the grid and tie together all private functions to 
    // visually display the cells
    public void render() { }
    public void step() { } 
}
