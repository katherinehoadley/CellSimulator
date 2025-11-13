// Anna Rakes
package view;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import controller.Step;
import controller.StepGameOfLife;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ControlPanel {
	
	// UI Components and current game state
	private Button pauseButton;
	private boolean paused = false;		
	private TextField rows;
	private TextField columns;
	private TextField aliveDensity;
	private 	HBox buttons = new HBox();
	private VBox controlPanel = new VBox();
	private GridPane gridPane;
	private int bufferPeriod = 100; 

	
	
	// Constants
	private final int TEXT_FIELD_WIDTH = 50;
	private final int BUTTON_SPACING = 10;
	private final int INPUT_BOX_SPACING = 15;
	private final int GRID_BUTTON_SPACING = 25;
	private final int TEXT_FONT_SIZE = 20;
	// Cell simulation logic 
	private Step stepper = new StepGameOfLife(); // make abstract
	private GridUI gridUI = new GridUI();
		
    
	// Lance: keeps step running continuously.
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static ScheduledFuture<?> scheduledTask;


	/**
	 * method setUpControlButtons
	 * Sets up all the control buttons shown on the screen to control the simulation. 
	 * Adds a start, pause, and step button. Adds the grid too.
	 * @return HBox containing all the buttons. 
	 */
	private HBox setupControlButtons(){
		buttons.setAlignment(Pos.BASELINE_CENTER);
		buttons.setSpacing(BUTTON_SPACING);
		createStartButton();
		createPauseButton();
		createStepButton();
		createSlowDownButton();
		return buttons;
	}
	
	public VBox setupControlPanel() {
	    controlPanel.setAlignment(Pos.TOP_CENTER);
	    controlPanel.setSpacing(GRID_BUTTON_SPACING); 

	    HBox buttonBox = setupControlButtons();
	    HBox inputBox = setupGridSize();
	    HBox densityBox = getAliveRateBox();

	    controlPanel.getChildren().addAll(inputBox, densityBox, buttonBox);

	    return controlPanel;
	}
	
	/**
	 * method createStartButton
	 * creates the start button that starts the simulation and prompts step to start running.
	 */
	private void createStartButton() {
		Button startButton = new Button("Start");
		startButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		startButton.setOnAction(value ->  {
			runSimulation();
		});
		buttons.getChildren().add(startButton);
	}
	
	
	/**
	 * method runSimulation created by Lance
	 * Uses a scheduled executor to repeatedly call the step method.
	 */
	private void runSimulation() {
		// Lance: 
		if (scheduledTask != null && !scheduledTask.isCancelled()) {
			scheduledTask.cancel(true); 
		}
		Runnable step = () -> {
			stepper.step(paused, false, gridUI.getGrid());
			gridUI.updateVisuals();
		};
		scheduledTask = scheduler.scheduleAtFixedRate(step, 0, bufferPeriod, TimeUnit.MILLISECONDS);
	}
	
	private void createSlowDownButton() {
		Button slowDownButton = new Button("Slow Down");
		slowDownButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		slowDownButton.setOnAction(value ->  {
			bufferPeriod += 100;
			runSimulation();
		});
		buttons.getChildren().add(slowDownButton);
	}
	
	/**
	 * method createPauseButton
	 * Creates the pause button which stops the simulation. 
	 * 	Updates the pause button to say resume. 
	 */
	private void createPauseButton() {
		pauseButton = new Button("Pause");
		pauseButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		pauseButton.setOnAction(value ->  {
			pressPause();
		});
		buttons.getChildren().add(pauseButton);
	}
	
	
	/**
	 * method createStepButton
	 * Creates the step button which manually takes one individual step 
	 * instead of using the scheduled executor. 
	 */
	private void createStepButton() {
		Button stepButton = new Button("Step");
		stepButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		stepButton.setOnAction(value ->  {
			stepper.step(paused, true, gridUI.getGrid()); 
			gridUI.updateVisuals();
		});
		buttons.getChildren().add(stepButton);
	}
	
	
	
	/**
	 * method createGridButton
	 * creates the button that allows you to generate the grid based on
	 * input from the text field. Shows an error if the input is not an integer.
	 */
	private Button createGridButton() {
		Button createGridButton = new Button("Create Grid From Input");
		createGridButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		createGridButton.setOnAction(value -> {
			try {
				int numOfRows = Integer.parseInt(rows.getText());
				int numOfColumns = Integer.parseInt(columns.getText());
				double densityRate = Double.parseDouble(aliveDensity.getText());
				if (numOfRows <= 0 || numOfColumns <= 0) {
					throw new NumberFormatException("The number of rows and columns must be positive.");
				}
				controlPanel.getChildren().remove(gridPane);
				gridPane = gridUI.startGrid(numOfRows, numOfColumns, densityRate);
				controlPanel.getChildren().add(gridPane);
			} catch (NumberFormatException nfe) {
				showError("Indvalid Input", "Please enter positive integers for the columns and rows and a rate for the density");
			}
		});
		return createGridButton;
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
	 * method setUpGridSize
	 * Sets up the input fields for specifying grid size.
     * Adds labeled text fields for rows and columns to an HBox.
	 * @return the HBox with the text fields. 
	 */
	private HBox setupGridSize(){
		HBox inputBox = new HBox();
		inputBox.setAlignment(Pos.BASELINE_CENTER);
		inputBox.setSpacing(INPUT_BOX_SPACING);

		Label rowsLabel = new Label("Rows: ");
		rowsLabel.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		rows = new TextField();
		rows.setPrefWidth(TEXT_FIELD_WIDTH);

		Label columnsLabel = new Label("Columns: ");
		columnsLabel.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		columns = new TextField();
		columns.setPrefWidth(TEXT_FIELD_WIDTH);
				
		inputBox.getChildren().addAll(rowsLabel, rows, columnsLabel, columns, createGridButton());
	
		return inputBox;
	}
	
	private HBox getAliveRateBox() {
		HBox inputBox = new HBox();
		inputBox.setAlignment(Pos.BASELINE_CENTER);
		inputBox.setSpacing(INPUT_BOX_SPACING);
		
		Label rateLabel = new Label ("Alive Density: ");
		rateLabel.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		aliveDensity = new TextField();
		aliveDensity.setPrefWidth(TEXT_FIELD_WIDTH);
		
		inputBox.getChildren().addAll(rateLabel, aliveDensity);
		return inputBox;
	}
	
	
	/**
	 * method pressPause
	 * changes the state of the simulation. 
	 */
	private void pressPause(){
		this.paused = !this.paused;
		if(this.paused){
			pauseButton.setText("Resume");
		}
		else{
			pauseButton.setText("Pause");
		}
	}
}
