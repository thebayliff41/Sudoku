# Sudoku
A Sudoku game in Java Code

To compile:
javac -g --module-path [path-to-javafx/lib] --add-modules javafx.controls

To run:
java --module-path [path-to-javafx/lib] --add-modules javafx.controls

These are specified on my machine in the included makefile, you can edit this path to run on your local machine.

What I've learned from this project: 
The power of modulus!
Use an ArrayList!

SRT, don't let one class do to much, ex:
I had the Grid class in charge of CSS, but I refactored
that into a Coloring utility class
