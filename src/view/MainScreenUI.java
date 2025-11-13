package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainScreenUI extends VBox {
	private Label titleLabel;
	private Button startButton;
	
	
	public MainScreenUI() {
		setupUI();
	}

	
	private void setupUI() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(30);
		this.setStyle("-fx-background-color: #2b2b2b;");
		this.setPrefSize(1000, 800);
		this.setMinSize(1000, 800);

		titleLabel = new Label("Game of Life");
		titleLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: white;");

		startButton = new Button("Start");
		startButton.setStyle("-fx-font-size: 20px; -fx-padding: 10 30 10 30;");
		startButton.setOnAction(e -> hide());

		this.getChildren().addAll(titleLabel, startButton);
	}

	
	public Button getStartButton() {
        return startButton;
    }
	
	public void show() {
		this.setVisible(true);
		this.setManaged(true);
	}

	public void hide() {
		this.setVisible(false);
		this.setManaged(false);
	}
}
