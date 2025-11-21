// Katherine Hoadley
package Tester;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InitializationWildfire;
import view.GridUI;

public class WFTest extends Application {  

    @Override
    public void start(Stage stage) {
        int rows = 30;
        int cols = 30;

        GridUI gridUI = new GridUI(new InitializationWildfire());
        Scene scene = new Scene(gridUI.startGrid(rows, cols, .5, 10));

        stage.setTitle("Wildfire Initialization Test");
        stage.setScene(scene);
        stage.show();
    } 

    public static void main(String[] args) {
        launch(args);
    }
}