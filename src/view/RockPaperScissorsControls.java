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
		
		Label thresholdLabel = new Label ("Threshold: ");
		thresholdLabel.setFont(new Font("Montserrat", TEXT_FONT_SIZE));
		additionalRequirementValue = new TextField();
		additionalRequirementValue.setPrefWidth(TEXT_FIELD_WIDTH);
		
		box.getChildren().addAll(thresholdLabel, additionalRequirementValue);
		return box;
	}


	@Override
	protected void createGrid() {
		// TODO Auto-generated method stub
		
	}




}
