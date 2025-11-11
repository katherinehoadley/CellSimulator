package Tester;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.ControlPanel;
import view.GridUI;

public class UITest extends Application {
	private GridUI gameUI;
	private ControlPanel panel;
	private VBox root;

	
	private void createGrid(int rows, int cols) {
		gameUI = new GridUI();
		
		root.getChildren().clear();
		root.getChildren().addAll(
			panel.setupGridSize(),
			panel.setupControlButtons(),
			new Button("Create Grid from Input") {{
				setOnAction(e -> refreshGrid());
			}},
			gameUI.startGrid()
		);
	}
	
	private void refreshGrid() {
		int rows = 20; 
		int cols = 20;
		
		try {
			rows = panel.getRows();
			cols = panel.getColumns();
		} 
		catch (NumberFormatException e) {
			System.out.println("[NumberFormatException] using defauly values (20x20)");
		}
		
		createGrid(rows, cols);
	}
	
	
	public void start(Stage stage) {
		panel = new ControlPanel();
		root = new VBox();
		
		createGrid(20, 20);

		Scene scene = new Scene(root, 600, 700);
		stage.setTitle("Game of Life Test");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}