package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import model.GamePreset;

public class UserInputPanel {
	
	private TextField rows;
	private TextField columns;
	private TextField additionalRequirementValue;
	
	private GamePreset preset;
	private ControlPanel controlPanel;
	
	private final int INPUT_BOX_SPACING = 15;
	private final int TEXT_FONT_SIZE = 20;
	private final int TEXT_FIELD_WIDTH = 50;
	
	public UserInputPanel(ControlPanel controlPanel, GamePreset preset) {
		this.controlPanel = controlPanel;
		this.preset = preset;
	}


	public HBox createBox() {
		HBox inputBox = new HBox();
		inputBox.setAlignment(Pos.BASELINE_CENTER);
		inputBox.setSpacing(INPUT_BOX_SPACING);
		
		Label rowLabel = createLabel("Rows");
		Label columnLabel = createLabel("Columns");
		Label thirdLabel = createLabel(preset.getThirdVariableLabel());
		
		rows = createTextField();
		columns = createTextField();
		additionalRequirementValue = createTextField();	
		
		inputBox.getChildren().addAll(rowLabel, rows, columnLabel, columns, thirdLabel, additionalRequirementValue, createGridButton());
		return inputBox;
	}
	
	/**
	 * method setUpGridSize
	 * Sets up the input fields for specifying grid size.
     * Adds labeled text fields for rows and columns to an HBox.
	 * @return the HBox with the text fields. 
	 */
	private Label createLabel(String textLabel){
		Label label = new Label(textLabel + ": ");
		label.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		return label;
	}
	
	private TextField createTextField() {
		TextField input = new TextField();
		input.setPrefWidth(TEXT_FIELD_WIDTH);
		return input;
	}
	
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
		return Double.parseDouble(additionalRequirementValue.getText());
	}
	
	


}
