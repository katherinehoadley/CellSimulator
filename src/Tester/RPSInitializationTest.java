// Katherine Hoadley
//tests the initialization of rock paper scissors
package Tester;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.GridUI;

public class RPSInitializationTest extends Application {

    public void start(Stage stage) {
        int rows = 10;
        int cols = 10;
        double[] values = { cols, rows};


        GridUI gridUI = new GridUI();
        Scene scene = new Scene(gridUI.startGrid("RockPaperScissors", values));

        stage.setTitle("RPS Initialization Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}