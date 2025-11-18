package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class RockPaperScissorsControls extends ControlPanel {

	protected TextField threshold;

	@Override
	protected HBox createAdditionalRequirementsBox() {
		HBox box = new HBox();
		box.setAlignment(Pos.BASELINE_CENTER);
		box.setSpacing(INPUT_BOX_SPACING);

		Label thresholdLabel = new Label("Threshold: ");
		thresholdLabel.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		additionalRequirementValue = new TextField();
		additionalRequirementValue.setPrefWidth(TEXT_FIELD_WIDTH);

		box.getChildren().addAll(thresholdLabel, additionalRequirementValue);
		return box;
	}

	@Override
	protected void createGrid() {
		try {
			int numOfRows = Integer.parseInt(rows.getText());
			int numOfColumns = Integer.parseInt(columns.getText());
			double threshold = Double.parseDouble(additionalRequirementValue.getText());
			if (numOfRows <= 4 || numOfColumns <= 4 || threshold < 0 || threshold > 1) {
				throw new NumberFormatException(
						"The number of rows and columns must be positive and threshold must be between 0 and 1.");
			}
			controlPanel.getChildren().remove(gridPane);
			gridPane = gridUI.startGrid(numOfRows, numOfColumns, threshold);
			controlPanel.getChildren().add(gridPane);
			stepper.setCurrentPreset(preset);
		} catch (NumberFormatException nfe) {
			showError("Invalid Input",
					"Please enter positive integers 5 or greater for the columns and rows and a value between 0 and 1 for the threshold");
		}
	}

}
