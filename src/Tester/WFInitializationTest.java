// Katherine Hoadley
//tests the initialization of wildfire
package Tester;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InitializationWildFire;
import view.GridUI;

public class WFInitializationTest extends Application {  

    public void start(Stage stage) {
        int rows = 30;
        int cols = 30;
        double treeDensity = .5;
        int burningTrees = 10;

        GridUI gridUI = new GridUI(new InitializationWildFire());
        Scene scene = new Scene(gridUI.startGrid(rows, cols, treeDensity, burningTrees));

        stage.setTitle("Wildfire Initialization Test");
        stage.setScene(scene);
        stage.show();
    } 

    public static void main(String[] args) {
        launch(args);
    }
}