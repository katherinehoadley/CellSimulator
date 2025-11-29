// Anna Rakes
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.ControlPanel;
import view.GameOfLifeControls;
import view.MainScreenUI;
import view.RockPaperScissorsControls;
import view.WildfireControls;

public class Main extends Application {
	
	// window dimensions:
	private final int SCREEN_WIDTH = 1000;
	private final int SCREEN_HEIGHT = 800;

	/**
	 * Called automatically when program is ran.
	 * Sets up the main window, adds the menu and panels, and shows everything on screen.
	 */
	public void start(Stage primaryStage) {
		StackPane root = new StackPane();
		MainScreenUI mainScreen = new MainScreenUI();
		
		buttonOperations(mainScreen, root);
		
		Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		primaryStage.setTitle("Cell Simulator");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * method buttonOperations
	 * @param mainScreen
	 * @param root
	 * Connects the menu buttons to the panel. 
	 */
	private void buttonOperations(MainScreenUI mainScreen, StackPane root) {
		VBox gameOfLifePanel = createSimulation(new GameOfLifeControls(), "GameOfLife", mainScreen);
		VBox rpsPanel = createSimulation(new RockPaperScissorsControls(), "RockPaperScissors", mainScreen);
		VBox wildfirePanel = createSimulation(new WildfireControls(), "WildFire", mainScreen);
		root.getChildren().addAll(mainScreen, gameOfLifePanel, rpsPanel, wildfirePanel);
		
		mainScreen.getGameOfLifeButton().setOnAction(e -> {
		    clickSimulation(mainScreen, gameOfLifePanel);
		});
		mainScreen.RPSButton().setOnAction(e -> {
		    clickSimulation(mainScreen, rpsPanel);
		});
		mainScreen.getWildfireButton().setOnAction(e -> {
			clickSimulation(mainScreen, wildfirePanel);
		});
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
