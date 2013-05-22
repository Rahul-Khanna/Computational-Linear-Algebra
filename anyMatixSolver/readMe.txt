Rahul Khanna
rk2658

How to run and compile the code:
When in the directory that has the needed files, making sure that the input and output files are in the same directory, type these commands:
javac *.java
java Tester input.txt 

Format of input.txt:
Follow this format exactly, otherwise you will get an ArrayIndexOutOfBounds error
m
n
[coefficient matrix]- each entry separated by a space and each row on its own line
[b vector]- one entry per line

Ex:
4
4
1 2 3 0
3 2 1 3
1 2 6 7
0 1 6 7
1
2
3
4

Solver:
Scans the input file and creates the necessary matrices. Does Forward Eliminations, then backwards eliminations, and then solves the system.

Tester:
Just a Tester class for the Solver class. Takes in the text file from the command line and then creates a solver object and calls the appropriate functions to preform the multiplications.

Matrix:
A matrix class that I created, with many helpful functions. 

Description of Solution:
I created a separate Matrix class with a bunch of useful functions. I then just scanned the input in and filled up an augmented matrix. After that I wrote code to do forward elimination and then backward elimination. The solve method first checked for no solution, then a unique one and last multiple solutions. The output is printed onto the screen, but could easily be put else where as it is String format.
