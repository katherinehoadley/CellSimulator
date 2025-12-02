// Author: Anna Rakes
// Specific control panel for the user input portion (columns, rows, threshold, burn rate, etc.)

package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import model.GamePreset;

public class UserInputPanel {
	
	// Text fields that hold input from users
	private TextField rows;
	private TextField columns;
	private TextField thirdRequirementValue;
	private TextField fourthRequirementValue;
	
	// References to respective simulation and control panel that use these inputs.
	private GamePreset preset;
	private ControlPanel controlPanel;
	
	// Layout constants
	private final int INPUT_BOX_SPACING = 15;
	private final int TEXT_FONT_SIZE = 20;
	private final int TEXT_FIELD_WIDTH = 50;
	
	// Constructor linking this UserInputPanel to the ControlPanel and preset.
	public UserInputPanel(ControlPanel controlPanel, GamePreset preset) {
		this.controlPanel = controlPanel;
		this.preset = preset;
	}

	/**
	 * method createBox
	 * Creates a box to hold all input fields for the user.
	 * includes button to right of all input fields to create the grid based on the input fields.
	 * @return the box with all inputs and buttons
	 */
	public HBox createBox() {
		HBox inputBox = new HBox();
		inputBox.setAlignment(Pos.BASELINE_CENTER);
		inputBox.setSpacing(INPUT_BOX_SPACING);
		
		Label rowLabel = createLabel("Rows");
		Label columnLabel = createLabel("Columns");
		Label thirdLabel = createLabel(preset.getThirdVariableLabel());
		rows = createTextField();
		columns = createTextField();
		thirdRequirementValue = createTextField();
		fourthRequirementValue = createTextField();
		
		if (preset.getPreset().equals("WildFire")) {
			inputBox.getChildren().addAll(rowLabel, rows, columnLabel, columns,  createGridButton());
		} else {
			inputBox.getChildren().addAll(rowLabel, rows, columnLabel, columns, thirdLabel, thirdRequirementValue, createGridButton());
		}
		return inputBox;
	}
	
	// create a label for any of the input fields
	private Label createLabel(String textLabel){
		Label label = new Label(textLabel + ": ");
		label.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		return label;
	}
	
	// creates a text field for any of the input fields
	private TextField createTextField() {
		TextField input = new TextField();
		input.setPrefWidth(TEXT_FIELD_WIDTH);
		return input;
	}
	
	/**
	 * method createGridButton
	 * creates a button for the user to press to spawn the grid based on their input
	 * @return the button
	 */
	private Button createGridButton() {
		Button createGridButton = new Button("Create Grid From Input");
		createGridButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		createGridButton.setOnAction(value -> {
			controlPanel.createGrid();
		});
		return createGridButton;
	}
	
	public int getRows() {
		return Integer.parseInt(rows.getText());
	}
	
	public int getColumns() {
		return Integer.parseInt(columns.getText());
	}
	
	public double getThirdValue() {
		return Double.parseDouble(thirdRequirementValue.getText());
	}
	
	public double getFourthValue() {
		return Double.parseDouble(fourthRequirementValue.getText());
	}
}
