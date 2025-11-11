// Anna Rakes
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.ControlPanel;

public class Main extends Application {
	
	private final int SCREEN_WIDTH = 1000;
	private final int SCREEN_HEIGHT = 800;
	private final int SPACING = 20;


	//TODO - it would be good to move most of this out of main and into an initialization class of some sort..

	public void start(Stage primaryStage) {
		ControlPanel controlPanel = new ControlPanel();

		VBox root = new VBox(SPACING);
		root.getChildren().add(
				controlPanel.setupControlPanel()
				);

		Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		primaryStage.setTitle("Conway's Game of Life Cell Simulation");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}


}
