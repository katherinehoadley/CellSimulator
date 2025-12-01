// Katherine Hoadley
//tests the initialization of rock paper scissors
package Tester;

import controller.InitializationRockPaperScissors;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.GridUI;

public class RPSInitializationTest extends Application {

    public void start(Stage stage) {
        int rows = 10;
        int cols = 10;

        GridUI gridUI = new GridUI(new InitializationRockPaperScissors());
        Scene scene = new Scene(gridUI.startGrid(rows, cols, 1.0, 1) );

        stage.setTitle("RPS Initialization Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}