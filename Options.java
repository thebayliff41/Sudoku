
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class Options {

    final Scene pop;
    final Stage stage;

    public Options(Stage stage, Grid grid, CountDownTimer timer) {
        this.stage = stage;
        final Label inStack = new Label("0");
        inStack.setTextFill(Coloring.properBackground(Coloring.currentHighlightColor));
        final Rectangle current = new Rectangle(15, 15);
        current.setFill(Coloring.currentHighlightColor);
        final StackPane stack = new StackPane(current, inStack);

        final Scene old = stage.getScene();
        final ColorPicker cp = new ColorPicker();
        final Button colorUpdate = new Button("Update!");
        colorUpdate.setOnAction(a -> {
                Coloring.changeLine("-fx-background-color: " + Coloring.convertColorToHex
                        (cp.getValue()) + ";", ":focused", "Sudoku.css", "temp_css_file");
                current.setFill(cp.getValue());
                Coloring.currentHighlightColor = cp.getValue();
                inStack.setTextFill(Coloring.properBackground(cp.getValue()));
                //Coloring.currentNumberColor = Coloring.properBackground(cp.getValue()); //+
        });

        final HBox boxColor = new HBox(15, cp, colorUpdate, stack);
        boxColor.setAlignment(Pos.CENTER);

        final ColorPicker pick = new ColorPicker();
        final Button numUpdate = new Button("Update!");

        final Label numEx = new Label("0");
        numEx.setTextFill(GridSquare.getNumColor()); 
        numUpdate.setOnAction(a -> {
            Coloring.setTextColor(grid.getBoard(), pick.getValue());
            numEx.setTextFill(pick.getValue());
            Coloring.currentNonConstNumberColor = pick.getValue();
        });
        final HBox numColor = new HBox(15, pick, numUpdate, numEx);
        numColor.setAlignment(Pos.CENTER);

        final Label resetlbl = new Label("Reset to Default");
        final Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(a -> {
            Coloring.reset();
            Coloring.setTextColor(grid.getBoard(), Color.GRAY);
            numEx.setTextFill(Coloring.defaultNonConstNumberColor);
            current.setFill(Coloring.defaultHighlightColor);
            //inStack.setTextFill(Coloring.defaultNumberColor);
            inStack.setTextFill(Coloring.properBackground(Coloring.currentHighlightColor));
        });

        final HBox resetPop = new HBox(15, resetlbl, resetBtn);
        resetPop.setAlignment(Pos.CENTER);

        final Button close = new Button("Exit");
        close.setOnAction((a) -> {
            timer.resume();
            stage.setScene(old);
        });

        final VBox root = new VBox(15, boxColor, numColor, resetPop,  close);   
        //root.setAlignment(Pos.CENTER);

         pop = new Scene(root);

    }//constructor

    /**
     * Shows the created scene
     */
    public void show() { stage.setScene(pop); }

}//options
