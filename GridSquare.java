
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
  private int row;
  private int col;
  private int psuedo;

  public GridSquare(int num, int row, int col) {
    super();
    this.num = num;
    this.row = row;
    this.col = col;
    square.setFill(Color.TRANSPARENT);
    square.setStroke(Color.BLACK);
    text = new Text(String.valueOf(num));
    this.getChildren().addAll(square, text);
    psuedo = num;
  }// GridSquare

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  // public GridSquare(int num) {
  //   super();
  //   this.num = num;
  //   square.setFill(Color.TRANSPARENT);
  //   square.setStroke(Color.BLACK);
  //   this.getChildren().addAll(square, text);
  // }

  /**
   * Gets value of square
   * @return value of square
   */
  public int getNum() {
    return num;
  }//getNum

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

  public void psuedoset(int n) {
    psuedo = n;
  }

  public int getPsuedo() {
    return psuedo;
  }

  /**
   * Set if the user can change the value of the square
   * @param val if the user can change the value (True means they can't)
   */
  public void setConst(boolean val) {
    isConst = val;
  }//setConst

}// GridSquare


