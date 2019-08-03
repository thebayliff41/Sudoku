
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Paint;

/**
 * Class that represents the squares that make up the grid 
 * of the Sudoku game
 * @author Bailey Nelson
 * @author baileyd.nelson@gmail.com
 */
public class GridSquare extends StackPane {

  private int num; //value of square
  private Rectangle square = new Rectangle(50, 50); //size of square
  private Text text;
  private boolean isConst = false; //Can the user change this tile
  private int row; //row in board
  private int col; //col in board
  private int trueNum; //The solution value
  private static Color numColor;

  /**
   * Gets the current text color, used for comparison to the 
   *
   * @return the paint value of the text
   */
  public Paint getTextColor() { return text.getFill(); }

  /**
   * Gets the color of the numbers in the square
   *
   * @return the color of the numbers in the square
   */
  public static Color getNumColor() { return numColor; }

  /**
    Gets the  row of the square
    @return the row of the square
    */
  public int getRow() { return row; }

  /**
    Get the col of the row
    @return the col of the square
    */
  public int getCol() { return col; }

  /**
   * Gets value of square
   * @return value of square
   */
  public int getNum() { return num; }//getNum
  /**
    * Getter for the solution number of the square
    * @return the solution number of the square
    */
  public int getTrue() { return trueNum; }

  /**
    Constructor taht sets various variables
    @param num the initial value of the square
    @param row the row of the square
    @param col the col of the square
    */
  public GridSquare(int num, int row, int col) {
    super();
    this.num = num;
    this.row = row;
    this.col = col;
    square.setFill(Color.TRANSPARENT);
    square.setStroke(Color.BLACK);
    text = new Text(String.valueOf(num));
    this.getChildren().addAll(square, text);
    numColor = Color.GRAY;
  }// GridSquare

  /**
   * Sets the color of the numbers in the square
   *
   * @param c the new color of the text
   */
  public static void setNumColor(Color c) { numColor = c; }

    /**
   * Sets the value of n if the square isn't constant
   * @param n the new value
   */
  public void setNum(int n) {
    if (isConst)
      return;

    num = n;

    text.setText(String.valueOf(num));
  }//setNum

  /**
   * Set if the user can change the value of the square
   * @param val if the user can change the value (True means they can't)
   */
  public void setConst(boolean val) { isConst = val; }//setConst

  /**
    Getter for if the square is a constant
    @return if the square is constant
    */
  public boolean isConst() { return isConst; }

  /**
    Wrapper function to set the visibility of the text in the square
    @param visible the visibility of the text
    @see setVisible
    */
  public void setTextVisible(boolean visible) { text.setVisible(visible); }

  /**
    Wrapper function to set the color of the text (user placed numbers
    are gray)
    @param c the new color of the number in the square
    */
  public void setColor(Color c) { text.setFill(c); }

  /**
    Setter for the actual value of what the square is supposed to be
    @param n the true value of the square
    */
  public void setTrue(int n) { trueNum = n; }

    /**
    Reset the square to initial settings to reset the board
    */
  public void reset() {
    isConst = false;
    text.setFill(Color.BLACK);
    text.setVisible(true);
    num = 0;
    trueNum = 0;
  }//reset

  /**
   * Solves the square and set it up to be displayed
   */
  public void solve() {
      setNum(trueNum);
      text.setVisible(true);
      isConst = true;
  }//solve

  /**
   * Sets the fill of the square based on if the correct number is inserted
   */
  public void check() {
      if (trueNum == num) square.setFill(Color.web("#c2f7ad"));
      else square.setFill(Color.web("#f78686"));
  }//check

  /**
   * Resets the color of the square to it's default
   */
  public void uncheck() {
      if (square.getFill() == Color.TRANSPARENT) return;
      square.setFill(Color.TRANSPARENT);
  }//uncheck

}// GridSquare


