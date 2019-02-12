import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import java.util.Random;

public class Grid extends GridPane {
	private GridSquare[][] gridsquares;

	public Grid() {
		super();
		this.setStyle("-fx-background-color: transparent;");

		gridsquares = new GridSquare[9][9];
		Line blackLine;

		for (int r = 0; r < gridsquares.length; r++) {
			for (int c = 0; c < gridsquares[r].length; c++) {
				gridsquares[r][c] = new GridSquare();
				this.add(gridsquares[r][c], r, c);
			} // for
		} // for

	}// Grid

}// Grid