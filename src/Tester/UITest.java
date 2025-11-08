// Anna Rakes

package Tester;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import screens.ControlPanel;


public class UITest extends Application {

	public void start(Stage stage) {
		ControlPanel panel = new ControlPanel();

		VBox root = new VBox();
		root.getChildren().addAll(
				panel.setupGridSize(),
				panel.setupControlButtons());

		Scene scene = new Scene(root, 500, 600);
		stage.setTitle("ControlPanel Test");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
