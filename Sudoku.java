
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * Class that runs the Sudoku application
 * @author Bailey Nelson
 * @author baileyd.nelson@gmail.com
 */
public class Sudoku extends Application {

    private final Grid grid = new Grid();
    private Stage stage;

    public static void main(String[] args) { Application.launch(); }

    public Grid getGrid() { return grid; }

    public Stage getStage() { return stage; }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        StackPane stack = new StackPane();
        BorderPane border = new BorderPane();
        stack.getChildren().add(border);

        border.setCenter(grid);

        Top menu = new Top(this);
        VBox root = new VBox(menu, stack);

        Scene primaryScene = new Scene(root);

        primaryScene.getStylesheets().add("Sudoku.css");

        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Sudoku");
        primaryStage.show();

    }// start

}// Application
