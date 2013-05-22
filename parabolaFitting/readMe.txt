Rahul Khanna
rk2658

How to run and compile the code:
When in the directory that has the needed files, making sure that the input is in the correct format, type these commands.
javac *.java
java Tester input.txt

Format of input.txt:
Follow this format exactly, otherwise you will get an ArrayIndexOutOfBounds error
m
C
D
E

Solver:
Creates the perturbed data point, sets up the right hand and left hand side and then does forward and backward elimination to solve.

Tester:
Just a Tester class for the Solver class. Takes in the text file from the command line and then creates a solver object and calls the appropriate functions to preform the multiplications.

Matrix:
A matrix class that I created, with many helpful functions.

Description of Solution:
I created a separate Matrix class with a bunch of useful functions. I then just scanned the input in and followed the instructions of the professor. The output, the values of C,D,E, is then printed out onto the screen.

