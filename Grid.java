import javafx.scene.layout.GridPane;
import java.util.Random;
import javafx.css.PseudoClass;


/**
 * Class that represents the Grid of the Sudoku game
 */
public class Grid extends GridPane {
	private GridSquare[][] gridsquares; //representation of the grid
	private int test = 0;

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
		// Random rand = new Random();

		for (int r = 0; r < gridsquares.length; r++)
			for (int c = 0; c < gridsquares[r].length; c++) {
				gridsquares[r][c].setNum(0);
				gridsquares[r][c].psuedoset(0);
			}

		// for (int i = 0, col = 0, row = 0, value = 0; i < 10; i++) {
		// 	row = rand.nextInt(9);
		// 	col = rand.nextInt(9);
		// 	value = rand.nextInt(9) + 1;

		// 	if (checkValid(row, col, value)) 
		// 		gridsquares[row][col].setNum(value);
		// 	else i--;
		// } 

			solve();
			unsolve();
	}

	//Unnecessary for internal use, the return value of nextEmpty does the same
	public boolean isFull() {
		for (int r = 0; r < gridsquares.length; r++)
			for (int c = 0; c < gridsquares[r].length; c++)
				if (gridsquares[r][c].getNum() == 0) return false;
				
		return true;
	}

	private GridSquare nextEmpty() {
		// Random rand = new Random();
		// int x = 0;
		// int y = 0;
		// for (int r = rand.nextInt(9) + 1; x < 100; x++, r++ )
		// 	for (int c = rand.nextInt(9) + 1; y < 100; y++)
		// 		if (gridsquares[r%9][c%9].getNum() == 0) return gridsquares[r%9][c%9];

		for (int r = 0; r < gridsquares.length; r++)
			for (int c = 0; c < gridsquares[r].length; c++)
				if (gridsquares[r][c].getNum() == 0) return gridsquares[r][c];

		return null;
	}

	public boolean solve() {
		GridSquare empty;
		Random rand = new Random();
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

	/**
	 * Returns a random valid value to insert into the square.
	 * Sacrifices time for being more truly random
	 * 
	 * @param square the space to check
	 * @return a random valid integer, or -1 if there is no valid integer
	 */
	private int getRandValid(GridSquare square) {
		Random rand = new Random();

		int[] values = new int[9];

		int arrayindex = 0;
		int row = square.getRow();
		int col = square.getCol();

		for (int i = 1; i < 10; i++)
			if (checkValid(row, col, i))
				values[arrayindex++] = i;

		return (arrayindex > 0) ? values[rand.nextInt(arrayindex)] : -1;

	}

	private void unsolve() {
		Random rand = new Random();
		boolean notLeast = true;
		copy();
		while(notLeast && test++ < 100) {
			int row = rand.nextInt(9);
			int col = rand.nextInt(9);
			int copy = gridsquares[row][col].getNum();
			gridsquares[row][col].setNum(0);
			// System.out.println(test);
			if (!solvecopy()) {
				gridsquares[row][col].setNum(copy);
				break;
			}
		}
		// System.out.println(test);
	}

	// private void show() {
	// 	for (int r = 0; r < gridsquares.length; r++)
	// 		for (int c = 0; c < gridsquares[r].length; c++)
	// 			gridsquares[r][c].setNum(gridsquares[r][c].getPsuedo());
	// }

	// private boolean copySolve() {
	// 	// GridSquare[][] copy = new GridSquare[9][9];
	// 	// for (int r = 0; r < gridsquares.length; r++) {
	// 	// 	for (int c = 0; c < gridsquares[r].length; c++) {
	// 	// 		copy[r][c] = gridsquares[r][c];
	// 	// 		// System.out.print(copy[r][c].getNum());
	// 	// 	}
	// 	// 	// System.out.println("");
	// 	// }//for

	// 	// boolean toReturn = solvecopy();
	// 	// System.out.println(toReturn);

	// 	// for (int r = 0; r < gridsquares.length; r++)
	// 	// 	for (int c = 0; c < gridsquares[r].length; c++)
	// 	// 		gridsquares[r][c] = copy[r][c];

	// 	// return toReturn;
	// 	// return solvecopy();
	// }
	
	private void copy() {
		for (int i = 0; i < gridsquares.length; i++)
			for (int c = 0; c < gridsquares[i].length; c++)
				gridsquares[i][c].psuedoset(gridsquares[i][c].getNum());
	}

	public boolean solvecopy() {
		GridSquare empty;
		Random rand = new Random();
		for (int i = 1; i < 30; i++) {
			if ((empty = nextcopyEmpty()) == null) return true;

			int val;
			if ((val = getRandValid(empty)) == -1) return false;

				empty.psuedoset(val);
				if (solvecopy()) return true;
				empty.psuedoset(0);
		}	
		return false;
	}//setup


	private GridSquare nextcopyEmpty() {
		for (int r = 0; r < gridsquares.length; r++)
			for (int c = 0; c < gridsquares[r].length; c++)
				if (gridsquares[r][c].getPsuedo() == 0) return gridsquares[r][c];

		return null;
	}

	private boolean checkValidCopy(int row, int col, int val) {
		for (int r = 0; r < gridsquares.length; r++)  //check row
			if (gridsquares[r][col] != null && gridsquares[r][col].getPsuedo() == val)
				return false;
		
		for (int c = 0; c < gridsquares[row].length; c++) //check col
			if (gridsquares[row][c] != null && gridsquares[row][c].getPsuedo() == val)
				return false;	

		for (int r = 0; r < 3; r++) { //check box
			for (int c = 0; c < 3; c++)
				if (gridsquares[row - row%3 + r][col-col%3 + c].getPsuedo() == val) return false;

		}
		return true;
	}
}// Grid