
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridSquare extends Rectangle {

    public GridSquare() {
	super(50, 50);
	this.setFill(Color.TRANSPARENT);
	this.setStroke(Color.BLACK);
	this.setStrokeWidth(1.0);

	this.setOnMouseClicked(e -> System.out.println("I work!"));
    }//GridSquare
}//GridSquare
