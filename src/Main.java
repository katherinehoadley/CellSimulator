// Anna Rakes
// Main class for the Cell Simulator application, Sets up the JavaFX window, main menu, 
// and simulation panels

import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.ControlPanel;
import view.ControlPanelFactory;
import view.MainScreenUI;

public class Main extends Application {
	
	// window dimensions:
	private final int SCREEN_WIDTH = 1000;
	private final int SCREEN_HEIGHT = 800;
	private final String TITLE = "Cell Simulator";
	private final String[] simulationNames = {"GameOfLife", "RockPaperScissors", "WildFire"};

	/**
	 * Called automatically when program is ran.
	 * Sets up the main window, adds the menu and panels, and shows everything on screen.
	 */
	public void start(Stage primaryStage) {
		StackPane root = new StackPane();
		MainScreenUI mainScreen = new MainScreenUI();
		
		buttonOperations(mainScreen, root);
		
		Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		primaryStage.setTitle(TITLE);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	// Create a hashmap to get the button associated with each simulation.
	private HashMap<String, Button> createSimList(MainScreenUI mainScreen) {
		HashMap<String, Button> simButtons = new HashMap<>();
		simButtons.put("GameOfLife", mainScreen.getGameOfLifeButton());
		simButtons.put( "RockPaperScissors", mainScreen.RPSButton());
		simButtons.put("WildFire", mainScreen.getWildfireButton());
		return simButtons;
	}
	
	/**
	 * method buttonOperations
	 * @param mainScreen
	 * @param root
	 * Connects the menu buttons for each simulation to the panel. 
	 */
	private void buttonOperations(MainScreenUI mainScreen, StackPane root) {	
		root.getChildren().add(mainScreen);
		HashMap<String, Button> simButtons = createSimList(mainScreen);
		ControlPanelFactory controlFactory = new ControlPanelFactory();
		for (String simulation: simulationNames) {
			ControlPanel controls = controlFactory.makeControlPanel(simulation);
			VBox panel = createSimulation(controls, simulation, mainScreen);
			
			simButtons.get(simulation).setOnAction(e -> {
				clickSimulation(mainScreen, panel);
			});
			root.getChildren().add(panel);
		}
	}
	
	/**
	 * method clickSimulation
	 * @param mainScreen
	 * @param panel
	 * hides the main menu and shows the chosen simulation panel.
	 */
	private void clickSimulation(MainScreenUI mainScreen, VBox panel) {
		mainScreen.setVisible(false);
	    panel.setVisible(true);
	}
	
	/**
	 * method createSimulation
	 * @param controls
	 * @param simulationChoice
	 * @param mainScreen
	 * builds a simulation panel for a given type of simulation. 
	 * sets the simulation choice and prepares the controls.
	 * @return the respective simulation panel. 
	 */
	private VBox createSimulation(ControlPanel simulationControls, String simulationChoice, MainScreenUI mainScreen) {
		simulationControls.setSimulationChoice(simulationChoice);
		VBox panel = simulationControls.setupControlPanel();
		
		simulationControls.setOnBackToMenu(() -> {
		    panel.setVisible(false);
		    mainScreen.show();
		});
		panel.setVisible(false);
		return panel;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
