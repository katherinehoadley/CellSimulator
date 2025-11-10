// Anna Rakes
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.ControlPanel;

public class Main extends Application {
	
	private final int SCREEN_SIZE = 600;
	private final int SPACING = 20;


	public void start(Stage primaryStage) {
		ControlPanel controlPanel = new ControlPanel();

		VBox root = new VBox(SPACING);
		root.getChildren().addAll(
				controlPanel.setupGridSize(),
				controlPanel.setupControlButtons()
				);

		Scene scene = new Scene(root, SCREEN_SIZE, SCREEN_SIZE);
		primaryStage.setTitle("Conway's Game of Life Cell Simulation");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}


}
