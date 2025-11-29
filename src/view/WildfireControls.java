package view;

public class WildfireControls extends ControlPanel {
	
	protected double treeDensity;
	protected int burningTrees;

	/**
	 * method createGrid
	 * builds a new wildfire grid based on the parameters from user input.
	 * verifies user input is valid and then creates the grid for given simulation
	 */
	@Override
	protected void createGrid() {
		try {
			updateGridDimensionsFromInput();
			treeDensity = userInputPanel.getThirdValue();
			burningTrees = (int)userInputPanel.getFourthValue();
			if (numOfRows <= 4 || numOfColumns <= 4 || treeDensity < 0 || treeDensity > 1) {
				throw new NumberFormatException("The number of rows and columns must be positive and tree density must be between 0 and 1.");
			}
			createRespectiveGrid(numOfRows, numOfColumns, treeDensity, burningTrees);
		} catch (NumberFormatException nfe) {
			showError("Indvalid Input", "Please enter positive integers 5 or greater for the columns and rows and a rate between 0 and 1 for the density");
		}
	}

}
