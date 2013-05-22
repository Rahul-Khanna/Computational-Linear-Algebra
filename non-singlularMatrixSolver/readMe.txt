Rahul Khanna
rk2658

How to run and compile the code:
When in the directory that has the needed files, making sure that the input and output files are in the same directory, type these commands:
javac *.java
java Tester input.txt output.txt

Format of input.txt:
Follow this format exactly, otherwise you will get an ArrayIndexOutOfBounds error
n
k
[coefficient matrix]- each entry separated by a space and each row on its own line
[b vector]- one entry per line

Ex:
4
5
1 2 3 0
3 2 1 3
1 2 6 7
0 1 6 7
1
2
3
4

Solver:
Scans the input file for the dimension, the number of diagonals, the coefficient matrix and the b vector. Creates a 2D array for the coefficient matrix and puts the b vector in an array. Goes through the steps of elimination and if elimination is not possible tells the user that the matrix is singular. It then solves the equation using back- substitution. It then prints out the solution to the equation in the output file, where there is one solution per line. 

Tester:
Just a Tester class for the Solver class. Takes in the two text files from the command line and then creates a solver object and calls the appropriate functions to preform the multiplications.

Description of Solution:
To replicate a matrix I used a 2D array. I then came up with an algorithm on how to eliminate the matrix using 3 for loops, by just doing examples by hand. I also made use of the structure of the matrix by not having to go through all n*n entries, hence the use of the counter and the kIndex. I also made sure that elimination was possible by checking the pivots and seeing if I could row swap.  I then used two for loops to solve for x, and the printed the resulting x vector onto the output file.