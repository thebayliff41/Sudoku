
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.paint.Color;

public final class Coloring {
    public static Color currentHighlightColor = readCurrentColorFromFile("./Sudoku.css");
    public static Color currentNumberColor = Color.GRAY;
    public static Color defaultHighlightColor = Color.GRAY;

    private Coloring() {
        currentHighlightColor = null;
        currentNumberColor = null;
    }

    /**
     * Converts a Color object to it's hex value, the Color class
     * doesn't have a similar method
     *
     * @param c the color to convert to hex
     *
     * @return the hex value as a String
     */
    public static String convertColorToHex(Color c) {
        String hex1;
        String hex2;
        hex1 = Integer.toHexString(c.hashCode()).toUpperCase();

        switch (hex1.length()) { //Getting from color to hex obtained from stack overflow
            case 2:
                hex2 = "000000";
                break;
            case 3:
                hex2 = String.format("00000%s", hex1.substring(0,1));
                break;
            case 4:
                hex2 = String.format("0000%s", hex1.substring(0,2));
                break;
            case 5:
                hex2 = String.format("000%s", hex1.substring(0,3));
                break;
            case 6:
                hex2 = String.format("00%s", hex1.substring(0,4));
                break;
            case 7:
                hex2 = String.format("0%s", hex1.substring(0,5));
                break;
            default:
                hex2 = hex1.substring(0, 6);
        }//switch

       return "#" + hex2;
    }//convertColorToHex

    /**
     * Reads the value of the current highlight color from the given
     * filename
     *
     * @param filename the name of the css file to read from
     *
     * @return the Color value of the highlight color
     */
   public static Color readCurrentColorFromFile(String filename) {
        String color = "null";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String toFind = ":focused";
            String current;

            while ((current = reader.readLine()) != null) {
                if (current.contains(toFind)) {
                        current = reader.readLine();
                        color = current.substring(current.indexOf('#'), current.indexOf('#') + 7);
                        break;
                }//if
            }//while

            reader.close();
        } catch (IOException e) {
                e.printStackTrace();
        }//try-catch

        return Color.valueOf(color);

    }//readCurrentColorFromFile

    /**
      Changes the color of the highlight of the square when it is clicked.
      It modifies the "Sudoku.css" file

      @param rule the rule in the css file that we are replacing 
      @param toDel the rule to delete
      @param origin the original file to read from
      @param tempName the temporary name of the file
    */
    public static void changeLine(String rule, String toDel, String origin, String tempName) {
        try {
            File css = new File(origin);
            File temp = new File(tempName);

            BufferedReader reader = new BufferedReader(new FileReader(css));
            BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

            String cur;

            while ((cur = reader.readLine()) != null) {
                if (cur.contains(toDel)) {
                        writer.write(cur + "\n");
                        reader.readLine();
                        writer.write(rule + "\n");
                        continue;
                }//if
                writer.write(cur + "\n");
            }//while
            writer.close();
            reader.close();

            temp.renameTo(css);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//changeColor

    /**
     * Sets the color of the numbers throughout the entire board
     * @param board the game board
     * @param c the color to change to
     */
    public static void setTextColor(GridSquare[][] board, Color c) {
        for (GridSquare[] arr : board)
            for (GridSquare gs : arr) {
                if (gs.getTextColor() != currentNumberColor) continue;
                gs.setColor(c);
            }//for
    }//setTextColor

    /**
     * Decids if the given color requires a background text of black or white to be readable
     *
     * {@link} https://stackoverflow.com/questions/3942878/how-to-decide-font-color-in-white-or-black-depending-on-background-color/3943023#3943023
     *
     * @param c The color of the item
     */
    public static Color properBackground(Color c) {
        if ((c.getRed() * 255 * .299) + (c.getGreen() * 255 * .587) + (c.getBlue() * 255  * .114) > 186) return Color.BLACK;
        else return Color.WHITE;
    }//properBackground 

}//coloring
