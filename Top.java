import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class Top extends MenuBar {
    public Top() {
        super();

        //File options
        final Menu file = new Menu("File");
        final MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> System.exit(0));
        file.getItems().addAll(exit);

        this.getMenus().addAll(file);
    }
}