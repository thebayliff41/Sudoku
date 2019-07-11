
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos; //Pos.CENTER;

/**
 * Class for the top menu of the Sudoku app
 * @author Bailey Nelson
 * @author baileyd.nelson@gmail.com
*/
public class Top extends MenuBar {
    public Top(Sudoku app) {
        super();

        //File options
        final Menu file = new Menu("File");
        final MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> System.exit(0));
        final MenuItem reset = new MenuItem("Reset Board");
        reset.setOnAction(e -> app.getGrid().reset());
        file.getItems().addAll(reset, exit);

        //Help options
        final Menu help = new Menu("Help");
        final MenuItem options = new MenuItem("options");
        options.setOnAction(e -> {
            //sample rectangle to show the current color
            final Rectangle current = new Rectangle(15, 15);
            current.setFill(Coloring.currentHighlightColor);

            final Scene old = app.getStage().getScene();
            final ColorPicker cp = new ColorPicker();
            final Button colorUpdate = new Button("Update!");
            colorUpdate.setOnAction(a -> {
                    Coloring.changeLine("-fx-background-color: " + Coloring.convertColorToHex
                            (cp.getValue()) + ";", ":focused", "Sudoku.css", "temp_css_file");
                    current.setFill(cp.getValue());
            });

            final HBox boxColor = new HBox(15, cp, colorUpdate, current);
            boxColor.setAlignment(Pos.CENTER);

            final ColorPicker pick = new ColorPicker();
            final Button numUpdate = new Button("Update!");

            final Label numEx = new Label("0");
            numEx.setTextFill(GridSquare.getNumColor()); 
            numUpdate.setOnAction(a -> {
                app.getGrid().setTextColor(pick.getValue());
                numEx.setTextFill(pick.getValue());
            });
            final HBox numColor = new HBox(15, pick, numUpdate, numEx);
            numColor.setAlignment(Pos.CENTER);

            final Button close = new Button("Exit");
            close.setOnAction((a) -> app.getStage().setScene(old));

            final VBox root = new VBox(15, boxColor, numColor,  close);   
            //root.setAlignment(Pos.CENTER);

            final Scene pop = new Scene(root);
            app.getStage().setScene(pop);
        });

        final MenuItem solve = new MenuItem("Solve");
        solve.setOnAction(e -> {
            app.getGrid().solveBoard();
        });

        final MenuItem solveSpot = new MenuItem("Solve Square");
        solveSpot.setOnAction(e -> {
            app.getGrid().solveSquare();
        });

        help.getItems().addAll(options, solve, solveSpot);

        this.getMenus().addAll(file, help);
    }//Top
}//Class Top
