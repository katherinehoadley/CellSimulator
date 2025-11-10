// Anna Rakes
package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import world.Step;

public class ControlPanel {
	
	private Button pauseButton;
	private boolean paused = false;		
	private TextField rows;
	private TextField columns;
	private 	HBox controls = new HBox();
	private final int TEXT_FIELD_WIDTH = 50;
	private Step stepper;
	private GameUI gameUI;
	
	public HBox setupControlButtons(){
		controls.setAlignment(Pos.BASELINE_CENTER);
		controls.setSpacing(10);
		
		createStartButton();
		createPauseButton();
		createStepButton();
		createGridButton();
	
		return controls;
	}
	
	private void createStartButton() {
		Button startButton = new Button("Start");
		startButton.setOnAction(value ->  {
//			controller.start();
		});
		controls.getChildren().add(startButton);
	}
	
	private void createPauseButton() {
		pauseButton = new Button("Pause");
		pauseButton.setOnAction(value ->  {
			pressPause(); // step must check for if paused
		});
		controls.getChildren().add(pauseButton);
	}
	
	private void createStepButton() {
		Button stepButton = new Button("Step");
		stepButton.setOnAction(value ->  {
			stepper.step(paused); 
		});
		controls.getChildren().add(stepButton);
	}
	
	private void createGridButton() {
		Button createGridButton = new Button("Create Grid From Input");
		createGridButton.setOnAction(value -> {
			//gameUI.createGrid(Integer.parseInt(rows.getText()), Integer.parseInt(columns.getText()))
		});
		controls.getChildren().add(createGridButton);
	}

	
	public HBox setupGridSize(){
		HBox inputBox = new HBox();
		inputBox.setAlignment(Pos.BASELINE_CENTER);
		inputBox.setSpacing(5);

		Label rowsLabel = new Label("Rows: ");
		rows = new TextField();
		rows.setPrefWidth(TEXT_FIELD_WIDTH);

		Label columnsLabel = new Label("Columns: ");
		columns = new TextField();
		columns.setPrefWidth(TEXT_FIELD_WIDTH);
		
		inputBox.getChildren().addAll(rowsLabel, rows, columnsLabel, columns);
	
		return inputBox;
	}
	
	private void pressPause(){
		this.paused = !this.paused;
		if(this.paused){
			pauseButton.setText("Resume");
		}
		else{
			pauseButton.setText("Pause");
		}
	}
	
	
	// can use these for creating grid, but maybe call createGrid method
	// in here so we dont have to use getters?
	public int getRows() {
		return Integer.parseInt(rows.getText());
	}
	public int getColumns() {
		return Integer.parseInt(columns.getText());
	}
	
	
	// can we create a create grid method in controller with these parameters?
//	public void createGrid() {
//		controller.createGrid(Integer.parseInt(rows.getText()), Integer.parseInt(columns.getText()));
//	}
	

	
	
}
