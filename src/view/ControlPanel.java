// Anna Rakes
package view;

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
		protected TextField saveFileNameAs;
		protected TextField fileName;
		protected HBox buttons = new HBox();
		protected VBox controlPanel = new VBox();
		protected HBox loadingButtons = new HBox();
		protected GridPane gridPane;
		protected String simulationChoice = "GameOfLife"; // default simulation choice so its not null.
		protected Runnable onBackToMenu;


		// Constants
		protected final int BUTTON_SPACING = 10;
		protected final int GRID_BUTTON_SPACING = 25;
		protected final int TEXT_FONT_SIZE = 20;
		protected final int INPUT_BOX_SPACING = 15;

		// Cell simulation logic 
		protected Step stepper;
		protected GridUI gridUI = new GridUI();	
		protected DataStorage dataStorage = new DataStorage();
		protected PresetFactory presetFactory = new PresetFactory();
		protected GamePreset preset;
		protected Initialization initialization;
		protected InitializationFactory initFactory = new InitializationFactory();
		protected SimulationController simulationController = new SimulationController();
		protected UserInputPanel userInputPanel;
		protected int numOfRows;
		protected int numOfColumns;

		
		protected abstract void createGrid();
		
		protected void updateGridDimensionsFromInput() {
		    numOfRows = userInputPanel.getRows();
		    numOfColumns = userInputPanel.getColumns();
		}

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
			createBackButton();
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
		    HBox inputBox = userInputPanel.createBox();

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
		
		protected void rebuildGrid(int rows, int cols, double thirdValue) {
		    controlPanel.getChildren().remove(gridPane);
		    gridPane = gridUI.startGrid(rows, cols, thirdValue, -1);
		    controlPanel.getChildren().add(gridPane);
		    stepper.setCurrentPreset(preset);
		}
		
		public void setSimulationChoice(String choice) {
			this.simulationChoice = choice;
			this.preset = presetFactory.makeSimulation(choice);
			userInputPanel = new UserInputPanel(this, preset);
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
			simulationController.stopSimulation();
			Runnable step = () -> {
				stepper.step(paused, false, gridUI.getGrid());
				gridUI.updateVisuals();
			};
			simulationController.startSimulation(step);
		}
		
		protected void createSlowDownButton() {
			Button slowDownButton = new Button("Slow Down");
			slowDownButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
			slowDownButton.setOnAction(value ->  {
				simulationController.slowDownSimSpeed();
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

		    	if (simulationController.checkLimitForSimSpeed()) {
		            simulationController.speedUpSim();
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
		
		//Kat Hoadley
		//this function creates a back button that allows the user to return to the main screen
		protected void createBackButton() {

		    Button backButton = new Button("Back");
		    backButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));

		    backButton.setOnAction(e -> {
		        simulationController.stopSimulation();

		        // Hide simulation UI
		        this.controlPanel.setVisible(false);
		        this.controlPanel.setManaged(false);

		        if (gridPane != null) {
		            gridPane.setVisible(false);
		            gridPane.setManaged(false);
		        }

		        if (onBackToMenu != null) {
		            onBackToMenu.run();
		        }
		    });

		    buttons.getChildren().add(backButton);
		}
		
		//Kat Hoadley
		//sets the callback so control panel can reference mainscreen without tight coupling
		public void setOnBackToMenu(Runnable callback) {
		    this.onBackToMenu = callback;
		}
	}