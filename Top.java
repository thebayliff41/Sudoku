
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
            final Options popup = new Options(app);
            popup.show();
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
