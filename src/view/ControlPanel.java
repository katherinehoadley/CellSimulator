// Anna Rakes
package view;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import controller.Step;
import data.DataStorage;
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
import model.Initialization;
import model.InitializationFactory;
import model.PresetFactory;

public abstract class ControlPanel {
	
	// UI Components and current game state
		protected Button pauseButton;
		protected boolean paused = false;		
		protected TextField rows;
		protected TextField columns;
//		protected TextField aliveDensity;
//		protected TextField threshold;
		protected TextField additionalRequirementValue;
		protected TextField saveFileNameAs;
		protected TextField fileName;
		protected HBox buttons = new HBox();
		protected VBox controlPanel = new VBox();
		protected HBox loadingButtons = new HBox();
		protected GridPane gridPane;
		protected int bufferPeriod = 100; 
		protected String simulationChoice = "GameOfLife";

		
		// Constants
		protected final int TEXT_FIELD_WIDTH = 50;
		protected final int BUTTON_SPACING = 10;
		protected final int INPUT_BOX_SPACING = 15;
		protected final int GRID_BUTTON_SPACING = 25;
		protected final int TEXT_FONT_SIZE = 20;
		protected static final int MIN_BUFFER_PERIOD = 50;
		
		// Cell simulation logic 
		protected Step stepper;
		protected GridUI gridUI = new GridUI();	
		protected DataStorage dataStorage = new DataStorage();
		protected PresetFactory presetFactory = new PresetFactory();
		protected GamePreset preset;
		protected Initialization initialization;
		protected InitializationFactory initFactory = new InitializationFactory();
			
	    
		// Lance: keeps step running continuously.
		protected static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		protected static ScheduledFuture<?> scheduledTask;


		/**
		 * method setUpControlButtons
		 * Sets up all the control buttons shown on the screen to control the simulation. 
		 * Adds a start, pause, and step button. Adds the grid too.
		 * @return HBox containing all the buttons. 
		 */
		protected HBox setupControlButtons(){
			buttons.setAlignment(Pos.BASELINE_CENTER);
			buttons.setSpacing(BUTTON_SPACING);
			createStartButton();
			createPauseButton();
			createStepButton();
			createSlowDownButton();
			createSpeedUpButton();
			return buttons;
		}
		
		protected HBox setUpLoadingButtons() {
			loadingButtons.setAlignment(Pos.TOP_CENTER);
		    loadingButtons.setSpacing(GRID_BUTTON_SPACING); 
		    
		    setFileName();
		    createSaveButton();
			getSavedFile();
			createLoadButton();
			
			return loadingButtons;
		}
		
		public VBox setupControlPanel() {
		    controlPanel.setAlignment(Pos.TOP_CENTER);
		    controlPanel.setSpacing(GRID_BUTTON_SPACING); 

		    HBox buttonBox = setupControlButtons();
		    HBox loadingBox = setUpLoadingButtons();
		    HBox inputBox = setUpGridSize();

		    controlPanel.getChildren().addAll(inputBox, loadingBox, buttonBox);

		    return controlPanel;
		}
		
		protected void createSaveButton() {
			Button saveButton = new Button("Save");
			saveButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
			saveButton.setOnAction(value -> dataStorage.saveData(gridUI.getGrid(), simulationChoice, saveFileNameAs.getText()));
			loadingButtons.getChildren().add(saveButton);
		}
		
		protected void createLoadButton() {
			Button loadButton = new Button("Load");
			loadButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
			loadButton.setOnAction(value ->  {
				dataStorage.loadData(fileName.getText());
				int[][] grid = dataStorage.getLoadedGrid();
				GamePreset preset = dataStorage.getAutomata();
				
				if (grid != null && preset != null) {
		            stepper.setCurrentPreset(preset);
		            if (gridPane != null) controlPanel.getChildren().remove(gridPane);
		            gridUI.setGrid(grid);
		            gridPane = gridUI.render(grid.length, grid[0].length);
		            controlPanel.getChildren().add(gridPane);
		        } else {
		            showError("Upload Failed", "Could not load simulation from file.");
		        }
			});
			loadingButtons.getChildren().add(loadButton);
		}
		
		public void setSimulationChoice(String choice) {
			this.simulationChoice = choice;
			this.preset = presetFactory.makeSimulation(choice);
			
			this.initialization = initFactory.getInitialization("Initialization"+choice);
			
			System.out.println("Preset: " + preset);
			System.out.println("Initialization: " + initialization);
			
			stepper = new Step(preset);
			stepper.setCurrentPreset(preset);
			gridUI = new GridUI(initialization);
		}
		
		
		
		/**
		 * method createStartButton
		 * creates the start button that starts the simulation and prompts step to start running.
		 */
		protected void createStartButton() {
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
		protected void runSimulation() {
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
		
		protected void createSlowDownButton() {
			Button slowDownButton = new Button("Slow Down");
			slowDownButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
			slowDownButton.setOnAction(value ->  {
				bufferPeriod += 100;
				runSimulation();
			});
			buttons.getChildren().add(slowDownButton);
		}
		
		//Katherine Hoadley
		//Speeds up the simulation by lowering buffer period. has a limit so that it doesn't break animation
		protected void createSpeedUpButton() {
		    Button speedUpButton = new Button("Speed Up");
		    speedUpButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		    speedUpButton.setOnAction(value -> {

		    	if (bufferPeriod - 100 >= MIN_BUFFER_PERIOD) {
		            bufferPeriod -= 100;
		            runSimulation();
		        }

		    });

		    buttons.getChildren().add(speedUpButton);
		}
		
		/**
		 * method createPauseButton
		 * Creates the pause button which stops the simulation. 
		 * 	Updates the pause button to say resume. 
		 */
		protected void createPauseButton() {
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
		protected void createStepButton() {
			Button stepButton = new Button("Step");
			stepButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
			stepButton.setOnAction(value ->  {
				stepper.step(paused, true, gridUI.getGrid()); 
				gridUI.updateVisuals();
			});
			buttons.getChildren().add(stepButton);
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

		/**
		 * method setUpGridSize
		 * Sets up the input fields for specifying grid size.
	     * Adds labeled text fields for rows and columns to an HBox.
		 * @return the HBox with the text fields. 
		 */
		protected HBox setUpGridSize(){
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
			
			HBox additionalRequirementBox = createAdditionalRequirementsBox();
					
			inputBox.getChildren().addAll(rowsLabel, rows, columnsLabel, columns, additionalRequirementBox, createGridButton());
		
			return inputBox;
		}
		
		protected abstract HBox createAdditionalRequirementsBox();
		
		protected Button createGridButton() {
			Button createGridButton = new Button("Create Grid From Input");
			createGridButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
			createGridButton.setOnAction(value -> {
				createGrid();
			});
			return createGridButton;
		}
		
		protected abstract void createGrid();
		
		
		
		protected HBox setFileName() {
			HBox inputBox = new HBox();
			inputBox.setAlignment(Pos.BASELINE_CENTER);
			inputBox.setSpacing(INPUT_BOX_SPACING);
			
			Label saveName = new Label ("Save file name as: ");
			saveName.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
			saveFileNameAs = new TextField();
			saveFileNameAs.setPrefWidth(100);
			
			loadingButtons.getChildren().addAll(saveName, saveFileNameAs);
			return inputBox;
		}
		
		protected HBox getSavedFile() {
			HBox inputBox = new HBox();
			inputBox.setAlignment(Pos.BASELINE_CENTER);
			inputBox.setSpacing(INPUT_BOX_SPACING);
			
			Label retrieveFile = new Label ("Load file name: ");
			retrieveFile.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
			fileName = new TextField();
			fileName.setPrefWidth(100);
			
			loadingButtons.getChildren().addAll(retrieveFile, fileName);
			return inputBox;
		}
		
		
		/**
		 * method pressPause
		 * changes the state of the simulation. 
		 */
		protected void pressPause(){
			this.paused = !this.paused;
			if(this.paused){
				pauseButton.setText("Resume");
			}
			else{
				pauseButton.setText("Pause");
			}
		}
	}




