// Anna Rakes
package view;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class RockPaperScissorsControls extends ControlPanel {
	
	protected int thresholdNumber;

	@Override
	protected void createGrid() {
		try {
			int numOfRows = userInputPanel.getRows();
			int numOfColumns = userInputPanel.getColumns();
			thresholdNumber = (int)userInputPanel.getThirdValue();
			if (numOfRows <= 4 || numOfColumns <= 4 || thresholdNumber < 1 || thresholdNumber > 8) {
				throw new NumberFormatException("The number of rows and columns must be positive and threshold must be between 1 and 8.");
			}
			stepper.setCurrentPreset(preset);
			controlPanel.getChildren().remove(gridPane);
			gridPane = gridUI.startGrid(numOfRows, numOfColumns, thresholdNumber, -1);
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
