// Anna Rakes
package view;

public class RockPaperScissorsControls extends ControlPanel {
	
	protected int thresholdNumber;

	/**
	 * method createGrid
	 * builds a new rock paper scissors grid based on the parameters from user input.
	 * verifies user input is valid and then creates the grid for given simulation
	 */
	@Override
	protected void createGrid() {
		try {
			updateGridDimensionsFromInput();
			thresholdNumber = (int)userInputPanel.getThirdValue();
			if (numOfRows <= 4 || numOfColumns <= 4 || thresholdNumber < 1 || thresholdNumber > 8) {
				throw new NumberFormatException("The number of rows and columns must be positive and threshold must be between 1 and 8.");
			}
			createRespectiveGrid(numOfRows, numOfColumns, thresholdNumber, -1);
			preset.configure(thresholdNumber);
		} catch (NumberFormatException nfe) {
			showError("Indvalid Input", "Please enter positive integers 5 or greater for the columns and rows and a threshold between 1 and 8");
		}
	}
}
