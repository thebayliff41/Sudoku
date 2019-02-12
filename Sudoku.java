
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Sudoku extends Application {

    public static void main(String[] args) {
        Application.launch();
    }// main

    @Override
    public void start(Stage primaryStage) {
        StackPane stack = new StackPane();
        BorderPane border = new BorderPane();
        stack.getChildren().add(border);

        Grid grid = new Grid();
        border.setCenter(grid);

        Top menu = new Top();
        VBox root = new VBox(menu, stack);

        Scene primaryScene = new Scene(root);

        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Sudoku");
        primaryStage.show();

    }// start

}// Application
