import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

public class Grid extends GridPane {
    private GridSquare[][] gridsquares;

    public Grid() {
	super();
	this.setStyle("-fx-background-color: transparent;");

	gridsquares  = new GridSquare[9][9];
	Line blackLine;

	for (int r = 0; r < gridsquares.length; r++) {
	    for (int c = 0; c < gridsquares[r].length; c++) {
		gridsquares[r][c] = new GridSquare();
		this.add(gridsquares[r][c], r, c);

		// if (r % 3 == 0 && r > 0) {
		//     blackLine = new Line(0, gridsquares[r][c].getY() +
		// 			 gridsquares[r][c].getHeight(),
		// 			 300,
		// 			 gridsquares[r][c].getY() +
		// 			 gridsquares[r][c].getHeight());
		//     this.add(blackLine, r, c);
		// }//if
		
		// if (c % 3 == 0 && c > 0) {
		//     blackLine = new Line(gridsquares[r][c].getX() +
		// 			 gridsquares[r][c].getWidth(), 0,
		// 			 gridsquares[r][c].getX() +
		// 			 gridsquares[r][c].getWidth(),
		// 			 300);
		//     this.add(blackLine, r, c);
		// }//if

	    }//for
	}//for

    }//Grid

}//Grid
