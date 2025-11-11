// Anna Rakes
package view;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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

public class ControlPanel {
	
	// UI Components and current game state
	private Button pauseButton;
	private boolean paused = false;		
	private TextField rows;
	private TextField columns;
	private 	HBox controls = new HBox();
	private GridPane gridPane;
	
	// Constants
	private final int TEXT_FIELD_WIDTH = 50;
	private final int BUTTON_SPACING = 10;
	private final int INPUT_BOX_SPACING = 15;
	private final int BUFFER_PERIOD = 100; 
	
	// Cell simulation logic 
	private Step stepper = new StepGameOfLife();
	private GridUI gridUI = new GridUI();
	
	
    
	// Lance: keeps step running continuously.
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	/**
	 * method setUpControlButtons
	 * Sets up all the control buttons shown on the screen to control the simulation. 
	 * Adds a start, pause, and step button. Adds the grid too.
	 * @return HBox containing all the buttons. 
	 */
	public HBox setupControlButtons(){
		controls.setAlignment(Pos.BASELINE_CENTER);
		controls.setSpacing(BUTTON_SPACING);
		createStartButton();
		createPauseButton();
		createStepButton();
		return controls;
	}
	
	/**
	 * method createStartButton
	 * creates the start button that starts the simulation and prompts step to start running.
	 * Uses a scheduled executor to repeatedly call the step method.
	 */
	private void createStartButton() {
		Button startButton = new Button("Start");
		startButton.setOnAction(value ->  {
			// Lance: 
			Runnable step = () -> {
				stepper.step(paused, false, gridUI.getGrid());
				gridUI.updateVisuals();
			};
			
		    scheduler.scheduleAtFixedRate(step, 0, BUFFER_PERIOD, TimeUnit.MILLISECONDS);

		});
		controls.getChildren().add(startButton);
	}
	
	/**
	 * method createPauseButton
	 * Creates the pause button which stops the simulation. 
	 * 	Updates the pause button to say resume. 
	 */
	private void createPauseButton() {
		pauseButton = new Button("Pause");
		pauseButton.setOnAction(value ->  {
			pressPause();
		});
		controls.getChildren().add(pauseButton);
	}
	
	
	/**
	 * method createStepButton
	 * Creates the step button which manually takes one individual step 
	 * instead of using the scheduled executor. 
	 */
	private void createStepButton() {
		Button stepButton = new Button("Step");
		stepButton.setOnAction(value ->  {
			stepper.step(paused, true, gridUI.getGrid()); 
			System.out.println("step worked");
			gridUI.updateVisuals();
		});
		controls.getChildren().add(stepButton);
	}
	
	/**
	 * method createGridButton
	 * creates the button that allows you to generate the grid based on
	 * input from the text field. Shows an error if the input is not an integer.
	 */
	private Button createGridButton() {
		Button createGridButton = new Button("Create Grid From Input");
		createGridButton.setOnAction(value -> {
			try {
				int numOfRows = Integer.parseInt(rows.getText());
				int numOfColumns = Integer.parseInt(columns.getText());
				if (numOfRows <= 0 || numOfColumns <= 0) {
					throw new NumberFormatException("The number of rows and columns must be positive.");
				}
				gridPane = gridUI.startGrid(numOfRows, numOfColumns);
				controls.getChildren().add(gridPane);
			} catch (NumberFormatException nfe) {
				showError("Indvalid Input", "Please enter positive integers for the columns and rows");
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
	public HBox setupGridSize(){
		HBox inputBox = new HBox();
		inputBox.setAlignment(Pos.BASELINE_CENTER);
		inputBox.setSpacing(INPUT_BOX_SPACING);

		Label rowsLabel = new Label("Rows: ");
		rows = new TextField();
		rows.setPrefWidth(TEXT_FIELD_WIDTH);

		Label columnsLabel = new Label("Columns: ");
		columns = new TextField();
		columns.setPrefWidth(TEXT_FIELD_WIDTH);
				
		inputBox.getChildren().addAll(rowsLabel, rows, columnsLabel, columns, createGridButton());
	
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
