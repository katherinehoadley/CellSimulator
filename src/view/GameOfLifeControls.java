// Anna Rakes
// Implements the logic for creating a grid for the game of life rules.
package view;


public class GameOfLifeControls extends ControlPanel {
		
	/**
	 * method createGrid
	 * builds a new gameoflife grid based on the parameters from user input.
	 * verifies user input is valid and then creates the grid for given simulation
	 */
	protected void createGrid() {
		try {
			updateGridDimensionsFromInput();
			double densityRate = userInputPanel.getThirdValue();
			if (numOfRows <= 4 || numOfColumns <= 4 || densityRate < 0 || densityRate > 1) {
				throw new NumberFormatException("The number of rows and columns must be positive and density rate must be between 0 and 1.");
			}
			values = new double[3];
			values[0] = numOfColumns;
			values[1] = numOfRows;
			values[2] = densityRate;
			createRespectiveGrid(values);
		} catch (NumberFormatException nfe) {
			showError("Indvalid Input", "Please enter positive integers 5 or greater for the columns and rows and a rate between 0 and 1 for the density");
		}
	}

}
