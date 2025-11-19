// Anna Rakes
package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class RockPaperScissorsControls extends ControlPanel {
	
	protected int thresholdNumber;
	
	
	@Override
	protected HBox createAdditionalRequirementsBox() {
		HBox box = new HBox();
		box.setAlignment(Pos.BASELINE_CENTER);
		box.setSpacing(INPUT_BOX_SPACING);
		
		Label thresholdLabel = new Label ("Threshold: ");
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
			thresholdNumber = Integer.parseInt(additionalRequirementValue.getText());
			if (numOfRows <= 4 || numOfColumns <= 4 || thresholdNumber < 1 || thresholdNumber > 8) {
				throw new NumberFormatException("The number of rows and columns must be positive and threshold must be between 1 and 8.");
			}
			stepper.setCurrentPreset(preset);
			controlPanel.getChildren().remove(gridPane);
			gridPane = gridUI.startGrid(numOfRows, numOfColumns, thresholdNumber);
			controlPanel.getChildren().add(gridPane);
			preset.configure(thresholdNumber);
			
			
			
		} catch (NumberFormatException nfe) {
			showError("Indvalid Input", "Please enter positive integers 5 or greater for the columns and rows and a threshold between 1 and 8");
		}
	}
	
	/**
	 * method createStepButton
	 * Creates the step button which manually takes one individual step 
	 * instead of using the scheduled executor. 
	 */
	@Override
	protected void createStepButton() {
		Button stepButton = new Button("Step");
		stepButton.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		stepButton.setOnAction(value ->  {
			stepper.step(paused, true, gridUI.getGrid()); 
			gridUI.updateVisuals();
		});
		buttons.getChildren().add(stepButton);
	}




}
