import javafx.scene.layout.GridPane;
import java.util.Random;
import javafx.css.PseudoClass;


/**
 * Class that represents the Grid of the Sudoku game
 */
public class Grid extends GridPane {
	private GridSquare[][] gridsquares; //representation of the grid

	public Grid() {
		super();
		Random rand = new Random();
		rand.nextInt(3);

		gridsquares = new GridSquare[9][9];
        PseudoClass right = PseudoClass.getPseudoClass("right");
        PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

		//Fill the grid with squares
		for (int r = 0; r < gridsquares.length; r++) {
			for (int c = 0; c < gridsquares[r].length; c++) {
				gridsquares[r][c] = new GridSquare(0, r, c);
				gridsquares[r][c].getStyleClass().add("cell");
				gridsquares[r][c].pseudoClassStateChanged(right, r == 2 || r == 5);
				gridsquares[r][c].pseudoClassStateChanged(bottom, c == 2 || c == 5);
				this.add(gridsquares[r][c], r, c);
			} // for
		} // for
		//come up with a random solution
		solve();
	}// Grid

	public GridSquare nextEmpty() {
		for (int r = 0; r < gridsquares.length; r++)
			for (int c = 0; c < gridsquares[r].length; c++)
				if (gridsquares[r][c].getNum() == 0) return gridsquares[r][c];

		return null;
	}

	public boolean solve() {
		GridSquare empty;
		for (int i = 1; i < 10; i++) {
			if ((empty = nextEmpty()) == null) return true;

			if (checkValid(empty.getRow(), empty.getCol(), i)) {
				empty.setNum(i);
				if (solve()) return true;
				empty.setNum(0);
			}
		}	
		return false;
	}//setup

	/**
	 * Checks if the number trying to be inserted is a valid number
	 * return if the value is valid for the location
	 */
	public boolean checkValid(int row, int col, int val) {
		for (int r = 0; r < gridsquares.length; r++)  //check row
			if (gridsquares[r][col] != null && gridsquares[r][col].getNum() == val)
				return false;
		
		for (int c = 0; c < gridsquares[row].length; c++) //check col
			if (gridsquares[row][c] != null && gridsquares[row][c].getNum() == val)
				return false;	

		for (int r = 0; r < 3; r++) { //check box
			for (int c = 0; c < 3; c++)
				if (gridsquares[row - row%3 + r][col-col%3 + c].getNum() == val) return false;

		}
		return true;
	}

}// Grid