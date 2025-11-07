// Anna Rakes
package screens;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ControlPanel {
	
	private Button pauseButton;
	private boolean paused = false;		
	private TextField rows;
	private TextField columns;
	
//	private Controller controller;


	
	public HBox setupControlButtons(){
		// Make the controls part
		HBox controls = new HBox();
		controls.setAlignment(Pos.BASELINE_CENTER);
		controls.setSpacing(10);
		
		Button startButton = new Button("Start");
		startButton.setOnAction(value ->  {
//			controller.start();
		});
		controls.getChildren().add(startButton);

		Button newMazeButton = new Button("New Grid");
		newMazeButton.setOnAction(value ->  {
//			controller.newMaze();
		});
		controls.getChildren().add(newMazeButton);

		pauseButton = new Button("Pause");
		pauseButton.setOnAction(value ->  {
			pressPause();
		});
		controls.getChildren().add(pauseButton);

		Button stepButton = new Button("Step");
		stepButton.setOnAction(value ->  {
//			controller.step(paused); // add boolean parameter for pause. only step if pause = false
		});
		controls.getChildren().add(stepButton);
		return controls;
	}

	
	public HBox setupGridSize(){
		HBox inputBox = new HBox();
		inputBox.setAlignment(Pos.BASELINE_CENTER);
		inputBox.setSpacing(5);

		Label rowsLabel = new Label("Rows: ");
		rows = new TextField();

		Label columnsLabel = new Label("Columns: ");
		columns = new TextField();
		
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
	

	
	
}
