// Anna Rakes
package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class GameOfLifeControls extends ControlPanel {
	
	
	/**
	 * method createGridButton
	 * creates the button that allows you to generate the grid based on
	 * input from the text field. Shows an error if the input is not an integer.
	 */
	protected void createGrid() {
		try {
			int numOfRows = Integer.parseInt(rows.getText());
			int numOfColumns = Integer.parseInt(columns.getText());
			double densityRate = Double.parseDouble(additionalRequirementValue.getText());
			if (numOfRows <= 4 || numOfColumns <= 4 || densityRate < 0 || densityRate > 1) {
				throw new NumberFormatException("The number of rows and columns must be positive and density rate must be between 0 and 1.");
			}
			controlPanel.getChildren().remove(gridPane);
			gridPane = gridUI.startGrid(numOfRows, numOfColumns, densityRate);
			controlPanel.getChildren().add(gridPane);
			stepper.setCurrentPreset(preset);
		} catch (NumberFormatException nfe) {
			showError("Indvalid Input", "Please enter positive integers 5 or greater for the columns and rows and a rate between 0 and 1 for the density");
		}
	}
	

	
	protected HBox createAdditionalRequirementsBox() {
		HBox box = new HBox();
		box.setAlignment(Pos.BASELINE_CENTER);
		box.setSpacing(INPUT_BOX_SPACING);
		
		Label rateLabel = new Label ("Alive Density: ");
		rateLabel.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		additionalRequirementValue = new TextField();
		additionalRequirementValue.setPrefWidth(TEXT_FIELD_WIDTH);
		
		box.getChildren().addAll(rateLabel, additionalRequirementValue);
		return box;
	}
	

	

}
