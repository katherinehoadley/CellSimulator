// Anna Rakes
// Abstract class representing the control panel for a simulation.
// Provides UI components 
// Subclasses implement createGrid to define simulation specific grid setup.
package view;

import controller.ControlActions;
import controller.Step;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.GamePreset;
import model.PresetFactory;

public abstract class ControlPanel {
	
	// Constants
	protected final int BUTTON_SPACING = 10;
	protected final int GRID_BUTTON_SPACING = 25;
	protected final int TEXT_FONT_SIZE = 20;
	protected final int INPUT_BOX_SPACING = 15;

	// UI Components and current game state
	protected Button pauseButton;
	protected boolean paused = false;		
	protected TextField saveFileNameAs;
	protected TextField fileName;
	protected HBox loadingButtons = new HBox();
	protected VBox controlPanel = new VBox();
	protected GridPane gridPane;
	protected String simulationChoice = "GameOfLife"; // default simulation choice so its not null when game starts.
	protected Font font = new Font("Montserrat", TEXT_FONT_SIZE);


	// Cell simulation logic 
	protected Step stepper;
	protected PresetFactory presetFactory = new PresetFactory();
	protected GamePreset preset;
	protected UserInputPanel userInputPanel;
	protected int numOfRows;
	protected int numOfColumns;
	protected double[] values;
	protected ControlActions controller;

	// each subclass (different simulation types), determine what parameters are involved in
	// creating the grid
	protected abstract void createGrid();

	// Reads the number of rows and columns from the user input panel
	protected void updateGridDimensionsFromInput() {
		numOfRows = userInputPanel.getRows();
		numOfColumns = userInputPanel.getColumns();
	}

	/**
	 * method setUpControlButtons
	 * Sets up all the control buttons shown on the screen to control the simulation. 
	 * Adds a start, pause, speed up, and slow down button.
	 * @return HBox containing all the buttons. 
	 */
	protected HBox setupControlButtons(){
		HBox buttons = new HBox();
		buttons.setAlignment(Pos.BASELINE_CENTER);
		buttons.setSpacing(BUTTON_SPACING);
		
		buttons.getChildren().addAll(
				createStartButton(),
				createPauseButton(),
				createStepButton(),
				createSlowDownButton(),
				createSpeedUpButton(),
				createBackButton());

		return buttons;
	}

	/**
	 * method setUpLoadingButtons
	 * creates and arranges the buttons used for loading or saving the simulation.
	 * @return the box with all the loading buttons.
	 */
	protected HBox setUpLoadingButtons() {
		loadingButtons.setAlignment(Pos.TOP_CENTER);
		loadingButtons.setSpacing(GRID_BUTTON_SPACING); 
		loadingButtons.getChildren().addAll( setFileName(), createSaveButton(), getSavedFile(), createLoadButton());

		return loadingButtons;
	}

	/**
	 * method setUpControlPanel
	 * builds the entire control panel with the input fields, loading/saving buttons, 
	 * and control buttons. 
	 * @return the entire control panel
	 */
	public VBox setupControlPanel() {			
		controlPanel.setAlignment(Pos.TOP_CENTER);
		controlPanel.setSpacing(GRID_BUTTON_SPACING); 

		HBox buttonBox = setupControlButtons();
		HBox loadingBox = setUpLoadingButtons();
		HBox inputBox = userInputPanel.createBox();

		controlPanel.getChildren().addAll(inputBox, loadingBox, buttonBox);
		return controlPanel;
	}


	// create the button that holds the functionality for saving a grid. 
	protected Button createSaveButton() {
		Button saveButton = new Button("Save");
		saveButton.setFont(font);
		saveButton.setOnAction(value -> controller.onSaveButton(simulationChoice, saveFileNameAs));
		return saveButton;
	}

	// create the load button, set its style, and assign the click handler.
	protected Button createLoadButton() {
		Button loadButton = new Button("Load");
		loadButton.setFont(font);
		loadButton.setOnAction(value ->  {
	        GridPane newGrid = controller.onLoadButton(fileName);
	        if (newGrid != null) {
	            if (gridPane != null) {
	                controlPanel.getChildren().remove(gridPane);
	            }
	            gridPane = newGrid; 
	            controlPanel.getChildren().add(gridPane);
	        }
	    });
		return loadButton;
	}


	/**
	 * method createRespectivGrid
	 * create a new grid with given input parameters for its setup, based on respective simulation.
	 * @param rows
	 * @param cols
	 * @param thirdValue
	 * @param fourthValue
	 */
	protected void createRespectiveGrid(double[] values) {
		if (gridPane != null) {
		    controlPanel.getChildren().remove(gridPane);
		}
		gridPane = controller.startGrid(simulationChoice, values);
		controlPanel.getChildren().add(gridPane);
		stepper.setCurrentPreset(preset);
	}
	
	

	/**
	 * method setSimulationChoice
	 * based on the user input, use the factories to determine the correct
	 * simulation initialization and preset. 
	 * @param choice
	 */
	public void setSimulationChoice(String choice) {
		this.simulationChoice = choice;
		this.preset = presetFactory.makeSimulation(choice);
		userInputPanel = new UserInputPanel(this, preset);

		stepper = new Step(preset);
		stepper.setCurrentPreset(preset);
		controller = new ControlActions(stepper);
	}

	/**
	 * method createStartButton
	 * creates the start button that starts the simulation and prompts step to start running.
	 */
	protected Button createStartButton() {
		Button startButton = new Button("Start");
		startButton.setFont(font);
		startButton.setOnAction(value ->  controller.onStart());
		return startButton;
	}


	/**
	 * method createSlowDownButton
	 * creates a slow down button that prompts the simulation to slow its speed.
	 */
	protected Button createSlowDownButton() {
		Button slowDownButton = new Button("Slow Down");
		slowDownButton.setFont(font);
		slowDownButton.setOnAction(value ->  controller.onSlowDown());
		return slowDownButton;
	}

	//Katherine Hoadley
	//Speeds up the simulation by lowering buffer period. has a limit so that it doesn't break animation
	protected Button createSpeedUpButton() {
		Button speedUpButton = new Button("Speed Up");
		speedUpButton.setFont(font);
		speedUpButton.setOnAction(value -> controller.onSpeedUp());
		return speedUpButton;
	}

	/**
	 * method createPauseButton
	 * Creates the pause button which stops the simulation. 
	 * 	Updates the pause button to say resume. 
	 */
	protected Button createPauseButton() {
		pauseButton = new Button("Pause");
		pauseButton.setFont(font);
		pauseButton.setOnAction(value ->  controller.onPause(pauseButton));
		return pauseButton;
	}

	/**
	 * method createStepButton
	 * Creates the step button which manually takes one individual step 
	 * instead of using the scheduled executor. 
	 */
	protected Button createStepButton() {
		Button stepButton = new Button("Step");
		stepButton.setFont(font);
		stepButton.setOnAction(value ->  controller.onStep());
		return stepButton;
	}


	/**
	 * method showError
	 * @param title: name at top of error box
	 * @param message: the error problem inside the error box. 
	 * Displays an error alert with the given title and message.
	 */
	protected void showError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	// creates an input box and label so user can input text for saving the grid
	// as a specified file name. 
	protected HBox setFileName() {
		HBox inputBox = new HBox();
		inputBox.setAlignment(Pos.BASELINE_CENTER);
		inputBox.setSpacing(INPUT_BOX_SPACING);

		Label saveName = new Label ("Save file name as: ");
		saveName.setFont(font);
		saveFileNameAs = new TextField();
		saveFileNameAs.setPrefWidth(100);

	    inputBox.getChildren().addAll(saveName, saveFileNameAs);
		return inputBox;
	}

	// creates an input box and label so user can input text for retrieving name of existing
	// saved grid file. 
	protected HBox getSavedFile() {
		HBox inputBox = new HBox();
		inputBox.setAlignment(Pos.BASELINE_CENTER);
		inputBox.setSpacing(INPUT_BOX_SPACING);

		Label retrieveFile = new Label ("Load file name: ");
		retrieveFile.setFont(font);
		fileName = new TextField();
		fileName.setPrefWidth(100);

		inputBox.getChildren().addAll(retrieveFile, fileName);
		return inputBox;
	}


	//Kat Hoadley
	//this function creates a back button that allows the user to return to the main screen
	protected Button createBackButton() {
		Button backButton = new Button("Back");
		backButton.setFont(font);
		backButton.setOnAction(e -> controller.onBack(controlPanel, gridPane));

		return backButton;
	}

	//Kat Hoadley
	//sets the callback so control panel can reference mainscreen without tight coupling
	public void setOnBackToMenu(Runnable callback) {
		controller.OnBackToMenu(callback);
	}
}