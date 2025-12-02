// Katherine Hoadley
//tests the initialization of game of life
package Tester;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.GridUI;

public class GOLInitializationTest extends Application {

    public void start(Stage stage) {
        int rows = 40;
        int cols = 40;
        double startAliveChance = .3;

        double[] values = { cols, rows, startAliveChance };
        GridUI gridUI = new GridUI();
        Scene scene = new Scene(gridUI.startGrid("GameOfLife", values));

        stage.setTitle("GOL Initialization Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}