
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

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
        reset.setOnAction(e -> app.getGrid().reset() );
        file.getItems().addAll(reset, exit);

        //Help options
        final Menu help = new Menu("Help");
        final MenuItem options = new MenuItem("Options");
        options.setOnAction(e -> {
            app.getTimer().pause();
            final Options popup = new Options(app.getStage(), app.getGrid(), app.getTimer());
            popup.show();
        });

        final Menu check = new Menu("Check");

        final MenuItem checkBoard = new MenuItem("Check Board");
        checkBoard.setOnAction(e -> app.getGrid().checkBoard());

        final MenuItem checkSquare = new MenuItem("Check Square");
        checkSquare.setOnAction(e -> app.getGrid().checkSquare());

        final MenuItem checkRow = new MenuItem("Check Row");
        checkRow.setOnAction(e -> app.getGrid().checkRow());

        final MenuItem checkCol = new MenuItem("Check Col");
        checkCol.setOnAction(e -> app.getGrid().checkCol());

        final MenuItem check3 = new MenuItem("Check 3x3");
        check3.setOnAction(e -> app.getGrid().check3());

        check.getItems().addAll(checkBoard, checkSquare, checkRow, checkCol, check3);

        help.getItems().addAll(options);

        final Menu solving = new Menu("Solve");

        final MenuItem solve = new MenuItem("Solve Board");
        solve.setOnAction(e -> {
            app.getGrid().solveBoard();
        });

        final MenuItem solveSpot = new MenuItem("Solve Square");
        solveSpot.setOnAction(e -> {
            app.getGrid().solveSquare();
        });

        final MenuItem solveRow = new MenuItem("Solve Row");
        solveRow.setOnAction(e -> app.getGrid().solveRow());

        final MenuItem solveCol = new MenuItem("Solve Col");
        solveCol.setOnAction(e -> app.getGrid().solveCol());

        final MenuItem solve3 = new MenuItem("Solve 3x3");
        solve3.setOnAction(e -> app.getGrid().solve3());

        solving.getItems().addAll(solve, solveSpot, solveRow, solveCol, solve3);

        this.getMenus().addAll(file, help, solving, check);
    }//Top
}//Class Top
