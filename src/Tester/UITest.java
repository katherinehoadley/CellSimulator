package Tester;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import screens.ControlPanel;
import screens.GameUI;

public class UITest extends Application {

	private GameUI gameUI;
	private ControlPanel panel;
	private VBox root;

	
	private void createGrid(int rows, int cols) {
		gameUI = new GameUI();
		gameUI.setGridDimensions(rows, cols);
		
		root = new VBox();
		root.getChildren().addAll(
			panel.setupGridSize(),
			panel.setupControlButtons(),
			gameUI.render()
		);
	}
	
	private void refreshGrid() {
		try {
			int rows = panel.getRows();
			int cols = panel.getColumns();
			createGrid(rows, cols);
		} 
		catch (NumberFormatException e) {
			System.out.println("Please enter valid numbers for rows and columns");
		}
	}
	
	
	public void start(Stage stage) {
		panel = new ControlPanel();
		createGrid(20, 20);
		
		Button refreshButton = new Button("Create Grid from Input");
		refreshButton.setOnAction(e -> refreshGrid());

		root.getChildren().add(2, refreshButton);

		Scene scene = new Scene(root, 600, 700);
		stage.setTitle("Game of Life Test");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}