// Author: Anna Rakes
package view;

public class WildfireControls extends ControlPanel {
	
	protected double treeDensity = 1.0;
	protected int burningTrees = 1;

	/**
	 * method createGrid
	 * builds a new wildfire grid based on the parameters from user input.
	 * verifies user input is valid and then creates the grid for given simulation
	 */
	@Override
	protected void createGrid() {
		try {
			updateGridDimensionsFromInput();

			values = new double[4];
			values[0] = numOfColumns;
			values[1] = numOfRows;
			values[2] = treeDensity;
			values[3] = burningTrees;
			createRespectiveGrid(values);
		} catch (NumberFormatException nfe) {
			showError("Indvalid Input", "Please enter positive integers 5 or greater for the columns and rows");
		}
	}

}
