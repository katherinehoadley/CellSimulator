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

public class Main extends Application {
	
	private final int SCREEN_WIDTH = 1000;
	private final int SCREEN_HEIGHT = 800;
	private final int SPACING = 20;


	//TODO - it would be good to move most of this out of main and into an initialization class of some sort..

	public void start(Stage primaryStage) {
		StackPane root = new StackPane();
		
		MainScreenUI mainScreen = new MainScreenUI();
		GameOfLifeControls gameOfLifeControls = new GameOfLifeControls();
		VBox gameOfLifePanel = gameOfLifeControls.setupControlPanel();
		gameOfLifePanel.setVisible(false);

		RockPaperScissorsControls rpsControls = new RockPaperScissorsControls();
		VBox rpsPanel = rpsControls.setupControlPanel();
		rpsPanel.setVisible(false);

		root.getChildren().addAll(mainScreen, gameOfLifePanel, rpsPanel);

		mainScreen.getGameOfLifeButton().setOnAction(e -> {
		    gameOfLifeControls.setSimulationChoice("GameOfLife");
		    mainScreen.setVisible(false);
		    gameOfLifePanel.setVisible(true);
		});

		mainScreen.RPSButton().setOnAction(e -> {
		    rpsControls.setSimulationChoice("RockPaperScissors");
		    mainScreen.setVisible(false);
		    rpsPanel.setVisible(true);
		});



		Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		primaryStage.setTitle("Conway's Game of Life Cell Simulation");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}


}
