JC = javac
JFLAGS = -g --module-path /Library/Java/JavaVirtualMachines/JavaFX/lib --add-modules javafx.controls 
RUN = java --module-path /Library/Java/JavaVirtualMachines/JavaFX/lib --add-modules javafx.controls 
CLASSES = $(wildcard *.java)
MAIN = Sudoku

.SUFFIXES: .java .class

.java.class: 
	$(JC) $(JFLAGS) $*.java

default: classes 

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(RUN) $(MAIN)

clean:
	$(RM) *.class 
