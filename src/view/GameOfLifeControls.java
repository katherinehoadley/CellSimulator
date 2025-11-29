// Anna Rakes
package view;


public class GameOfLifeControls extends ControlPanel {
		
	
	protected void createGrid() {
		try {
			updateGridDimensionsFromInput();
			double densityRate = userInputPanel.getThirdValue();
			if (numOfRows <= 4 || numOfColumns <= 4 || densityRate < 0 || densityRate > 1) {
				throw new NumberFormatException("The number of rows and columns must be positive and density rate must be between 0 and 1.");
			}
			rebuildGrid(numOfRows, numOfColumns, densityRate);
		} catch (NumberFormatException nfe) {
			showError("Indvalid Input", "Please enter positive integers 5 or greater for the columns and rows and a rate between 0 and 1 for the density");
		}
	}

}
