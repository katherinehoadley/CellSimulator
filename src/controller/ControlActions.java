// Anna Rakes

package controller;

import data.DataStorage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.GamePreset;
import view.GridUI;
import view.SimulationController;

public class ControlActions {

	// UI Components
	private GridUI gridUI = new GridUI();
	private boolean paused = false;		
	private Runnable onBackToMenu;
	
	// Simulation Components
	private DataStorage dataStorage = new DataStorage();
	private SimulationController simulationController = new SimulationController();
	private Step stepper;
	

	// Constructor
	public ControlActions(Step stepper) {
		this.stepper = stepper;
	}

	// Handles saving the grid to the file. 
	public void onSaveButton(String simulationChoice, TextField saveFileNameAs) {
		dataStorage.saveData(gridUI.getGrid(), simulationChoice, saveFileNameAs.getText());
	}

	/**
	 * handles the logic that is executed when the load button is clicked.
	 * loads simulation data from the file name input and updates the UI with the
	 * loaded grid and preset.
	 */
	public GridPane onLoadButton( TextField fileName) {
		dataStorage.loadData(fileName.getText());
		int[][] grid = dataStorage.getLoadedGrid();
		GamePreset preset = dataStorage.getAutomata();

		if (grid != null && preset != null) {
			stepper.setCurrentPreset(preset);
			gridUI.setGrid(grid);
			return gridUI.render(grid.length, grid[0].length);
		} else {
			showError("Upload Failed", "Could not load simulation from file.");
			return null;
		}
	}

	/**
	 * method showError
	 * @param title: name at top of error box
	 * @param message: the error problem inside the error box. 
	 * Displays an error alert with the given title and message.
	 */
	private void showError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/**
	 * method runSimulation created by Lance
	 * starts the simulation loop, repeatedly stepping through the simulation.
	 */
	public void onStart( ) {
		simulationController.stopSimulation();
		Runnable step = () -> {
			stepper.step(paused, false, gridUI.getGrid());
			gridUI.updateVisuals();
		};
		simulationController.startSimulation(step);
	}

	// slows down the simulation speed and restarts loop with new timing.
	public void onSlowDown( ) {
		simulationController.slowDownSimSpeed();
		onStart();
	}

	// Kat:
	// speed up simulation speed (within acceptable limits) and restart
	// loop with new timing.
	public void onSpeedUp() {
		if (simulationController.checkLimitForSimSpeed()) {
			simulationController.speedUpSim();
			onStart();
		}
	}

	/**
	 * method pressPause
	 * changes the state of the simulation. 
	 */
	public void onPause(Button pauseButton){
		this.paused = !this.paused;
		if(this.paused){
			pauseButton.setText("Resume");
		}
		else{
			pauseButton.setText("Pause");
		}
	}

	// executes a single step manually
	public void onStep() {
		stepper.step(paused, true, gridUI.getGrid()); 
		gridUI.updateVisuals();
	}

	// creates a new grid based on the specified simulation and the values input by user.
	public GridPane startGrid(String simulationChoice, double[] values) {
		return gridUI.startGrid(simulationChoice, values);
	}


	// Kat:
	// handles the back button by hiding the simulation and returning to the main screen.
	public void onBack(VBox controlPanel, GridPane gridPane) {
		simulationController.stopSimulation();

		// Hide simulation UI
		controlPanel.setVisible(false);
		controlPanel.setManaged(false);

		if (gridPane != null) {
			gridPane.setVisible(false);
			gridPane.setManaged(false);
		}

		if (onBackToMenu != null) {
			onBackToMenu.run();
		}
	}

	//Kat Hoadley
	//sets the callback so control panel can reference mainscreen without tight coupling
	public void OnBackToMenu(Runnable callback) {
		this.onBackToMenu = callback;
	}

}
