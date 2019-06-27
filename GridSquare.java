
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
  }// GridSquare

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
    Getter for the solution number of the square
    @return the solution number of the square
    */
  public int getTrue() { return trueNum; }

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
}// GridSquare


