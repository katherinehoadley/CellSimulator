package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainScreenUI extends VBox {
	private Label titleLabel;
	private Button startButton;
	private Button gameOfLifeButton;
	private Button rockPaperScissorsButton;
	private String selectedSimulation;
	
	
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

		gameOfLifeButton = new Button("Game of Life");
        gameOfLifeButton.setStyle("-fx-font-size: 20px; -fx-padding: 10 30 10 30;");
        gameOfLifeButton.setOnAction(e -> selectedSimulation = "GameOfLife");

        rockPaperScissorsButton = new Button("Rock Paper Scissors");
        rockPaperScissorsButton.setStyle("-fx-font-size: 20px; -fx-padding: 10 30 10 30;");
        rockPaperScissorsButton.setOnAction(e -> selectedSimulation = "RockPaperScissors");


		this.getChildren().addAll(titleLabel, gameOfLifeButton, rockPaperScissorsButton);
	}

	
	public Button getStartButton() {
        return startButton;
    }
	
	public String getSelectedSimulation() {
        return selectedSimulation;
    }
	
	public Button getGameOfLifeButton() {
		return gameOfLifeButton;
	}
	
	public Button RPSButton() {
		return rockPaperScissorsButton;
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
