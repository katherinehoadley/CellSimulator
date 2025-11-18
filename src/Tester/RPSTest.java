// Katherine Hoadley
package Tester;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InitializationRockPaperScissors;
import view.GridUI;

public class RPSTest extends Application {

    @Override
    public void start(Stage stage) {
        int rows = 50;
        int cols = 50;

        GridUI gridUI = new GridUI(new InitializationRockPaperScissors());
        Scene scene = new Scene(gridUI.startGrid(rows, cols, 1.0));

        stage.setTitle("RPS Initialization Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}