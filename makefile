JC = javac
JFLAGS = -g
CLASSES = $(wildcard *.java)
MAIN = Sudoku

.SUFFIXES: .java .class

.java.class: 
	$(JC) $(JFLAGS) $*.java

default: classes 

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	java $(MAIN)

clean:
	$(RM) *.class 