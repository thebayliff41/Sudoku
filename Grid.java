
import javafx.scene.layout.GridPane;
import java.util.ArrayList; //arraylist methods
import java.util.Collections; //collections.shuffle
import java.util.Random;
import javafx.css.PseudoClass;
import javafx.scene.Scene; //setOnKeyPressed
import javafx.scene.input.KeyCode; //various keycodes
import javafx.scene.paint.Color; //Color.aColor

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Class that represents the Grid of the Sudoku game
 * @author Bailey Nelson
 * @author baileyd.nelson@gmail.com
 */
public class Grid extends GridPane {
    private GridSquare[][] gridsquares; //representation of the grid
    private ArrayList<GridSquare> emptyList;
    private ArrayList<GridSquare> allSquares;
    private int wrong = 0; 
    private final Sudoku app;

    /**
     * Getter for the current board
     *
     * @return the current board
     */
    public GridSquare[][] getBoard() { return gridsquares; }


    /**
     * Constructor that creates a random valid board and then
     * unsolves the board so that it is only one valid solution
     */
    public Grid(Sudoku app) {
        super();
        this.app = app;

        //Coloring.currentHighlightColor = Coloring.readCurrentColorFromFile("./Sudoku.css");
        //Coloring.currentNumberColor = Color.GRAY;

        gridsquares = new GridSquare[9][9];
        emptyList = new ArrayList<GridSquare>(81);
        allSquares = new ArrayList<GridSquare>(81);

        PseudoClass right = PseudoClass.getPseudoClass("right");
        PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

        //Fill the grid with squares
        for (int r = 0; r < gridsquares.length; r++) {
            //randomizes if the column goes in the beginnning or end of the list
            int index = (Math.random() < .5) ? 0 :emptyList.size(); 

            for (int c = 0; c < gridsquares[r].length; c++) {
                gridsquares[r][c] = new GridSquare(0, r, c);
                gridsquares[r][c].getStyleClass().add("cell");
                gridsquares[r][c].pseudoClassStateChanged(right, r == 2 || r == 5);
                gridsquares[r][c].pseudoClassStateChanged(bottom, c == 2 || c == 5);
                this.add(gridsquares[r][c], r, c);
                emptyList.add(index, gridsquares[r][c]);
                allSquares.add(gridsquares[r][c]);

                gridsquares[r][c].setOnMouseClicked((e) -> {
                    GridSquare square = (GridSquare) e.getSource();
                    if (square.isConst()) return;
                    square.requestFocus();
                });

                //When the square is clicked and a number is typed, put it in the square
                gridsquares[r][c].setOnKeyPressed((e) -> {
                        GridSquare square = (GridSquare) e.getSource(); //safe cast
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
                        //square.setColor(Color.GREY);
                        square.setNum(Integer.parseInt(e.getCode().getName()));
                        square.setTextVisible(true);

                        if (square.getNum() != square.getTrue()) wrong++;

                        if (wrong == 0) System.out.println("WINNER!");
                });//seton
            } // for
        } // for

        // Collections.shuffle(emptyList); Causes the program to be too slow
        Collections.shuffle(allSquares);

        create();
    }// Grid

    /**
     * Creates the puzzle by solving the board, then 
     * unsolving it, then replacing the 0's with blank spaces
     *
     * {@see} solve, unsolve, update
     */
    public void create() {
        solve();
        unsolve();
        update();
    }//create

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
                gridsquares[r][c].reset();
                emptyList.add(index, gridsquares[r][c]);
                allSquares.add(gridsquares[r][c]);
            }
        }

        Collections.shuffle(allSquares);

        this.requestFocus();

        create();
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
            empty.setTrue(num); 
            if (solve()) return true;
            empty.setNum(0);
            empty.setTrue(0);
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
                    gridsquares[r][c].setColor(Color.GRAY);
                } else gridsquares[r][c].setConst(true);
            }
    }//update

    /**
     * Solves the given square and decrements the counter of how many squares are wrong
     *
     * @param s The square to solve
     */
    private void solve(GridSquare s) {
        s.solve();
        wrong--;
    }//solve

   /**
    * Solves the entire board, putting the correct value in the 
    * correct spot
    */
    public void solveBoard() {
        for (GridSquare[] g : gridsquares) for (GridSquare s : g) solve(s);
    }//solveBoard
    
    /**
    * Solves the currently highlighted square based on the focus
    */
    public void solveSquare() {
        if (!(app.getStage().getScene().focusOwnerProperty().get() instanceof GridSquare)) return;
        GridSquare square = (GridSquare) app.getStage().getScene().focusOwnerProperty().get();
        solve(square);
    }//solveSquare

    /**
     * Resets the focus off of the square
     */
    public void resetFocus() {
        this.requestFocus();
    }//resetFocus

    /**
     * Solves the col of hte currently highlighted square
     */
    public void solveCol() {
        if (!(app.getStage().getScene().focusOwnerProperty().get() instanceof GridSquare)) return;
        GridSquare square = (GridSquare) app.getStage().getScene().focusOwnerProperty().get();
        for (GridSquare g : gridsquares[square.getRow()]) solve(g);
    }//solveRow

    /**
     * Solves the row of the currently highlighted square
     */
    public void solveRow() {
        if (!(app.getStage().getScene().focusOwnerProperty().get() instanceof GridSquare)) return;
        GridSquare square = (GridSquare) app.getStage().getScene().focusOwnerProperty().get();
        for(int i = 0; i < 9; i++) solve(gridsquares[i][square.getCol()]);
    }//solveCol

    /**
     * Solves the 3x3 square containing the highlighted square
     */
    public void solve3() {
        if (!(app.getStage().getScene().focusOwnerProperty().get() instanceof GridSquare)) return;
        GridSquare square = (GridSquare) app.getStage().getScene().focusOwnerProperty().get();
        for (int r = 0; r < 3; r++) for (int c = 0; c < 3; c++) 
                solve(gridsquares[square.getRow() - square.getRow()%3 + r][square.getCol() - square.getCol()%3 + c]);
    }//solve3

    /**
     * Checks the entire board, highlights in red the wrong squares and in green the correct squares
     */
   public void checkBoard() {
        if (!(app.getStage().getScene().focusOwnerProperty().get() instanceof GridSquare)) return;
        GridSquare square = (GridSquare) app.getStage().getScene().focusOwnerProperty().get();
        for (GridSquare[] g : gridsquares) for (GridSquare s : g) s.check();
        square.focusedProperty().addListener(new GridSquareListener(square));
   }//check

    /**
     * Checks the current square, highlights in red if the square is wrong and in green if it is correct
     */
    public void checkSquare() {
        if (!(app.getStage().getScene().focusOwnerProperty().get() instanceof GridSquare)) return;
        GridSquare square = (GridSquare) app.getStage().getScene().focusOwnerProperty().get();
        square.check();
        square.focusedProperty().addListener(new GridSquareListener(square));
   }//checkSquare

    /**
     * Checks the curren row of the highlighted square, highlights in red if the square is wrong and in green if it is correct
     */
    public void checkRow() {
        if (!(app.getStage().getScene().focusOwnerProperty().get() instanceof GridSquare)) return;
        GridSquare square = (GridSquare) app.getStage().getScene().focusOwnerProperty().get();
        for (int i = 0; i < 9; i++) gridsquares[i][square.getCol()].check();
        square.focusedProperty().addListener(new GridSquareListener(square));
    }//checkRow

    /**
     * Checks the current col of the highlighted square, highlights in red if the square is wrong and in green if it is correct
     */
    public void checkCol() {
        if (!(app.getStage().getScene().focusOwnerProperty().get() instanceof GridSquare)) return;
        GridSquare square = (GridSquare) app.getStage().getScene().focusOwnerProperty().get();
        for (GridSquare g : gridsquares[square.getRow()]) g.check();
        square.focusedProperty().addListener(new GridSquareListener(square));
    }//checkCol

    /**
     * Checks the current 3x3 square of the highlighted square, highlights in red if the square is wrong and in green if it is correct
     */
    public void check3() {
        if (!(app.getStage().getScene().focusOwnerProperty().get() instanceof GridSquare)) return;
        GridSquare square = (GridSquare) app.getStage().getScene().focusOwnerProperty().get();
        for (int r = 0; r < 3; r++) for (int c = 0; c < 3; c++)
            gridsquares[square.getRow()-square.getRow()%3 + r][square.getCol() - square.getCol()%3 + c].check();
        square.focusedProperty().addListener(new GridSquareListener(square));
    }//check3

    /**
     * Class to add a ChangeListener to a square to uncheck squares when the focus changes
     * @author Bailey Nelson
     * @author baileyd.nelson@gmail.com
     *
     */
    private class GridSquareListener implements ChangeListener<Boolean> {
        private final GridSquare square;

        GridSquareListener(GridSquare square) {
            this.square = square;
        }//GridSquareListener

        @Override
        public void changed(ObservableValue<? extends Boolean> obs, Boolean oldval, Boolean newval) {
            if (!newval) for (GridSquare[] s : gridsquares) for (GridSquare g : s) g.uncheck();
            square.focusedProperty().removeListener(this);
        }//changed
    }//GridSquareListener
}// Grid
