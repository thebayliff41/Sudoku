import javafx.scene.layout.GridPane;
import java.util.Random;
import javafx.css.PseudoClass;


/**
 * Class that represents the Grid of the Sudoku game
 */
public class Grid extends GridPane {
	private GridSquare[][] gridsquares; //representation of the grid
	Random rand = new Random();

	public Grid() {
		super();

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
		// unsolve();
		// show();

	}// Grid

	public void reset() {
		for (int r = 0; r < gridsquares.length; r++)
			for (int c = 0; c < gridsquares[r].length; c++) 
				gridsquares[r][c].setNum(0);

			solve();
			// unsolve();
	}

	//Unnecessary for internal use, the return value of nextEmpty does the same
	public boolean isFull() {
		for (int r = 0; r < gridsquares.length; r++)
			for (int c = 0; c < gridsquares[r].length; c++)
				if (gridsquares[r][c].getNum() == 0) return false;
				
		return true;
	}

	private GridSquare nextEmpty() {
		for (int r = 0; r < gridsquares.length; r++)
			for (int c = 0; c < gridsquares[r].length; c++)
				if (gridsquares[r][c].getNum() == 0) return gridsquares[r][c];

		return null;
	}

	public boolean solve() {
		GridSquare empty;
		// Random rand = new Random();
		for (int i = 1; i < 30; i++) {
			if ((empty = nextEmpty()) == null) return true;

			//Way faster than using the getRandValid() method
			int val = (i < 20) ? rand.nextInt(9) + 1 : i%9; //gives it 20 chances to guess the right number, otherwise goes from 1-9

			// int val;
			// if ((val = getRandValid(empty)) == -1) return false;

			if (checkValid(empty.getRow(), empty.getCol(), val)) {
				// System.out.println("val = " + val + ", but i = " + i);
				empty.setNum(val);
				// empty.psuedoset(val);
				if (solve()) return true;
				empty.setNum(0);
				// empty.psuedoset(0);
			}
		}	
		return false;
	}//setup

	/**
	 * Checks if the number trying to be inserted is a valid number
	 * return if the value is valid for the location
	 */
	private boolean checkValid(int row, int col, int val) {
		for (int r = 0; r < gridsquares.length; r++)  //check row
			if (gridsquares[r][col].getNum() == val)
				return false;
		
		for (int c = 0; c < gridsquares[row].length; c++) //check col
			if (gridsquares[row][c].getNum() == val)
				return false;	

		for (int r = 0; r < 3; r++) { //check box
			for (int c = 0; c < 3; c++)
				if (gridsquares[row - row%3 + r][col-col%3 + c].getNum() == val) return false;

		}
		return true;
	}

	/**
	 * Returns a random valid value to insert into the square.
	 * Sacrifices time for being more truly random
	 * 
	 * @param square the space to check
	 * @return a random valid integer, or -1 if there is no valid integer
	 */
	private int getRandValid(GridSquare square) {
		// Random rand = new Random();
		System.out.println("here");

		int[] values = new int[9];

		int arrayindex = 0;
		int row = square.getRow();
		int col = square.getCol();

		for (int i = 1; i < 10; i++)
			if (checkValid(row, col, i))
				values[arrayindex++] = i;

		return (arrayindex > 0) ? values[rand.nextInt(arrayindex)] : -1;

	}

}// Grid