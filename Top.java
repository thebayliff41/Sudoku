import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 *  Class for the top menu of the Sudoku app
 * @author Bailey Nelson
 * @author baileyd.nelson@gmail.com
*/
public class Top extends MenuBar {
    public Top() {
        super();

        //File options
        final Menu file = new Menu("File");
        final MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> System.exit(0));
        file.getItems().addAll(exit);

        //Help options
        final Menu help = new Menu("Help");
        final MenuItem options = new MenuItem("options");
        options.setOnAction(e -> {
            System.out.println("optins popup"); //TODO
        });
        help.getItems().addAll(options);

        this.getMenus().addAll(file, help);
    }//Top
}//Class Top