
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class GridSquare extends StackPane {

  private int num;
  private Rectangle square = new Rectangle(50, 50);
  private Text text = new Text(String.valueOf(num));
  private boolean isConst = false;

  public GridSquare() {
    super();
    square.setFill(Color.TRANSPARENT);
    square.setStroke(Color.BLACK);
    square.setStrokeWidth(1.0);

    this.getChildren().add(square);
    this.getChildren().add(text);

  }// GridSquare

  public int getNum() {
    return num;
  }

  public void setNum(int n) {
    if (isConst)
      return;

    num = n;

    text.setText(String.valueOf(n));
  }

  public void setConst(boolean val) {
    isConst = val;
  }
}// GridSquare
