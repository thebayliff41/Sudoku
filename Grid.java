import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javafx.css.PseudoClass;


/**
 * Class that represents the Grid of the Sudoku game
 */
public class Grid extends GridPane {
	private GridSquare[][] gridsquares; //representation of the grid
	private ArrayList<GridSquare> emptyList;

	public Grid() {
		super();

		gridsquares = new GridSquare[9][9];
		emptyList = new ArrayList<GridSquare>();

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
				emptyList.add(gridsquares[r][c]);
			} // for
		} // for

		// Collections.shuffle(emptyList); Causes the program to be too slow
		//come up with a random solution
		solve();
		update();

	}// Grid

	/**
	 * Resets the board and resolves it with a new solution 
	 */
	public void reset() {
		for (int r = 0; r < gridsquares.length; r++)
			for (int c = 0; c < gridsquares[r].length; c++) {
				gridsquares[r][c].setNum(0);
				emptyList.add(gridsquares[r][c]);
			}

		solve();
	}

	/**
	 * Returns all of the valid numebers that can go in a given GridSquare, randomized
	 * 
	 * @param tocheck the gridsquare to check
	 * @return an ArrayList filled with valid numbers
	 */
	public ArrayList<Integer> getValids(GridSquare tocheck) {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		int row = tocheck.getRow();
		int col = tocheck.getCol();

		for (int i = 1; i < 10; i++)
			if (checkValid(row, col, i))
				numbers.add(i);

		Collections.shuffle(numbers);
		return numbers;
	}

	/**
	 * Comes up with a valid solution for the java application recursively
	 * 
	 */
	public boolean solve() {
		GridSquare empty;
		ArrayList<Integer> numbers;
		if ((empty = (emptyList.size() > 0) ? emptyList.remove(0) : null) == null) return true;
		numbers = getValids(empty);

		while(numbers.size() > 0) {
			int num = numbers.remove(0);

			empty.setNum(num);
			if (solve()) return true;
			empty.setNum(0);
		}

		emptyList.add(0, empty);

		return false;
	}


	/**
	 * Checks if the number trying to be inserted is a valid number
	 * return if the value is valid for the location
	 * 
	 * @param row the row of the box
	 * @param col the col of the box
	 * @param val the value to check in the row, col, and surrounding square
	 */
	private boolean checkValid(int row, int col, int val) {
		for (int r = 0; r < gridsquares.length; r++)  //check row
			if (gridsquares[r][col].getNum() == val) return false;
		
		for (int c = 0; c < gridsquares[row].length; c++) //check col
			if (gridsquares[row][c].getNum() == val) return false;	

		for (int r = 0; r < 3; r++) //check box
			for (int c = 0; c < 3; c++)
				if (gridsquares[row - row%3 + r][col-col%3 + c].getNum() == val) return false;

		return true;
	}

	/**
	 * @deprecated
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

	/**
	 * Updates the board so that all 0's are invisible 
	 */
	public void update() {
		for (int r = 0; r < gridsquares.length; r++)
			for (int c = 0; c < gridsquares[r].length; c++)
				if (gridsquares[r][c].getNum() == 0) gridsquares[r][c].setTextVisible(false);
	}


}// Grid
