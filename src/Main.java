// Anna Rakes
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.ControlPanel;

public class Main extends Application {


	public void start(Stage primaryStage) {
		ControlPanel controlPanel = new ControlPanel();

		VBox root = new VBox(20);
		root.getChildren().addAll(
				controlPanel.setupGridSize(),
				controlPanel.setupControlButtons()
				);

		Scene scene = new Scene(root, 600, 600);
		primaryStage.setTitle("ControlPanel Test View");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}


}
