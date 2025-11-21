// Katherine Hoadley
package Tester;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InitializationGameOfLife;
import view.GridUI;

public class GOLTest extends Application {

    @Override
    public void start(Stage stage) {
        int rows = 40;
        int cols = 40;
        double startAliveChance = .3;

        GridUI gridUI = new GridUI(new InitializationGameOfLife());
        Scene scene = new Scene(gridUI.startGrid(rows, cols, startAliveChance, 1) );

        stage.setTitle("GOL Initialization Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}