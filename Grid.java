
import javafx.scene.layout.GridPane;
import java.util.ArrayList; //arraylist methods
import java.util.Collections; //collections.shuffle
import java.util.Random;
import javafx.css.PseudoClass;
import javafx.scene.Scene; //setOnKeyPressed
import javafx.scene.input.KeyCode; //various keycodes
import javafx.scene.paint.Color; //Color.aColor

/**
 * Class that represents the Grid of the Sudoku game
 */
public class Grid extends GridPane {
	private GridSquare[][] gridsquares; //representation of the grid
	private ArrayList<GridSquare> emptyList;
	private ArrayList<GridSquare> allSquares;
    private int wrong = 0; //doesn't work yet

	/**
	 * Constructor that creates a random valid board and then
	 * unsolves the board so that it is only one valid solution
	 */
	public Grid() {
		super();

		gridsquares = new GridSquare[9][9];
		emptyList = new ArrayList<GridSquare>(81);
		allSquares = new ArrayList<GridSquare>(81);

        PseudoClass right = PseudoClass.getPseudoClass("right");
        PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

		//Fill the grid with squares
		for (int r = 0; r < gridsquares.length; r++) {
			//randomizes if the column goes in the beginnning of the list, or at the end of the list
			int index = (Math.random() < .5) ? 0 :emptyList.size(); 

			for (int c = 0; c < gridsquares[r].length; c++) {
				gridsquares[r][c] = new GridSquare(0, r, c);
				gridsquares[r][c].getStyleClass().add("cell");
				gridsquares[r][c].pseudoClassStateChanged(right, r == 2 || r == 5);
				gridsquares[r][c].pseudoClassStateChanged(bottom, c == 2 || c == 5);
				this.add(gridsquares[r][c], r, c);
				emptyList.add(index, gridsquares[r][c]);
				allSquares.add(gridsquares[r][c]);

                //When the square is clicked and a number is typed, put it in the square
                gridsquares[r][c].setOnKeyPressed((e) -> {
                        System.out.println(wrong);
                        GridSquare square = (GridSquare) e.getSource();
                        if (square.getNum() == 0 || square.getNum() != square.getTrue()) wrong--;
                        if (!square.isFocused()) return;
                        if (!e.getCode().isDigitKey())  return;
                        if (square.isConst()) return;
                        if (e.getCode() == KeyCode.DIGIT0) { //reset square on 0
                                wrong++;
                                square.setTextVisible(false);
                                square.setColor(Color.BLACK);
                                return;
                        }

                        //place the key pressed inside the box
                        square.setColor(Color.GREY);
                        square.setNum(Integer.parseInt(e.getCode().getName()));
                        square.setTextVisible(true);

                        if (square.getNum() != square.getTrue()) wrong++;

                        if (wrong == 0) System.out.println("WINNER!");
                });//seton
			} // for
		} // for

		// Collections.shuffle(emptyList); Causes the program to be too slow
		Collections.shuffle(allSquares);

		//come up with a random solution
		solve();
		unsolve();
		update();
	}// Grid

	/**
	 * Resets the board and resolves it with a new solution 
	 */
	public void reset() {
		allSquares = new ArrayList<GridSquare>(81);
		emptyList = new ArrayList<GridSquare>(81);
        wrong = 0;
		for (int r = 0; r < gridsquares.length; r++) {
			int index = (Math.random() < .5) ? 0 : emptyList.size();

			for (int c = 0; c < gridsquares[r].length; c++) {
                gridsquares[r][c].setConst(false);
				gridsquares[r][c].setNum(0);
				gridsquares[r][c].setTextVisible(true);
                gridsquares[r][c].setColor(Color.BLACK);
				emptyList.add(index, gridsquares[r][c]);
				allSquares.add(gridsquares[r][c]);
			}
		}

		Collections.shuffle(allSquares);

		solve();
		unsolve();
		update();
	}

	/**
	 * Returns all of the valid numebers that can go in a given GridSquare, randomized
	 * 
	 * @param tocheck the gridsquare to check
	 * @return an ArrayList filled with valid valids
	 */
	private ArrayList<Integer> getValids(GridSquare tocheck) {
		ArrayList<Integer> valids = new ArrayList<Integer>();
		int row = tocheck.getRow();
		int col = tocheck.getCol();

		for (int i = 1; i < 10; i++) if (checkValid(row, col, i)) valids.add(i);

		Collections.shuffle(valids);
		return valids;
	}

	/**
	 * Comes up with a valid solution for the java application recursively
	 * Also used for showing the user the solution if they give up
	 */
	public boolean solve() {
		GridSquare empty;
		ArrayList<Integer> valids;
		if (emptyList.isEmpty()) return true;
		empty = emptyList.remove(0);
		valids = getValids(empty);

		while(valids.size() > 0) {
			int num = valids.remove(0);

			empty.setNum(num);
            empty.setTrue(num); //+
			if (solve()) return true;
			empty.setNum(0);
            empty.setTrue(0); //+
		}

		emptyList.add(0, empty);

		return false;
	}

	/**
	 * Takes a full board, and takes out squares while maintaining
	 * uniqueness
	 */
	private void unsolve() {
		//all of the removed values from the board
		ArrayList<GridSquare> removed = new ArrayList<GridSquare>(81);

		while (!allSquares.isEmpty()) {
			GridSquare square = allSquares.remove(0); //get a new square
			int old = square.getNum(); //the old value of the square
			square.setNum(0); //set the value to empty
			removed.add(0, square);

			if (!isValid(new int[]{0}, removed)) {
				square.setNum(old);
			}
		}
	}

	/**
	 * Tests if the board has only 1 unique solution
	 * 
	 * Naive appoach, without testing size of valid options, could run
	 * more than 40,000 times, way too slow.
	 * 
	 * @param solutions the number of solutions, int[] to pass by reference
	 * @param removed an ArrayList of the removed squares
	 * @return if the board is unique
	 */
	private boolean isValid(int[] solutions, ArrayList<GridSquare> removed) {
		if (removed.isEmpty()) return ++solutions[0] == 1;

		ArrayList<GridSquare> copy = new ArrayList<GridSquare>(removed);

		GridSquare empty = copy.remove(0);
		ArrayList<Integer> valids = getValids(empty);

		//if there are less than 2 results, it runs faster
		if (valids.size() > 1) return false;

		while (!valids.isEmpty()) {
			empty.setNum(valids.remove(0));
			if (!isValid(solutions, copy)) {
				empty.setNum(0);
				return false;
			}
			empty.setNum(0);
		}

		return true;
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
    @Deprecated
	private int getRandValid(GridSquare square) {
		Random rand = new Random();

		int[] values = new int[9];

		int arrayindex = 0;
		int row = square.getRow();
		int col = square.getCol();

		for (int i = 1; i < 10; i++) if (checkValid(row, col, i)) values[arrayindex++] = i;

		return (arrayindex > 0) ? values[rand.nextInt(arrayindex)] : -1;
	}

	/**
	 * Updates the board so that all 0's are invisible 
	 */
	private void update() {
		for (int r = 0; r < gridsquares.length; r++)
			for (int c = 0; c < gridsquares[r].length; c++) {
				GridSquare square = gridsquares[r][c];
				if (gridsquares[r][c].getNum() == 0) {
                    wrong++;
					gridsquares[r][c].setTextVisible(false);
					gridsquares[r][c].setOnMouseClicked((e) -> {
                        square.requestFocus();
                                /*
						ArrayList<Integer> valids = getValids(square);
						if (valids.size() == 1) { //if there is only one solution, display it
                                square.setNum(valids.remove(0));
                                square.setTextVisible(true);
                        }//if
                        */
					});
				} else gridsquares[r][c].setConst(true);
			}
	}
}// Grid
