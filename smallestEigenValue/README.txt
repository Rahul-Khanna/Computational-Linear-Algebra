Rahul Khanna
rk2658

How to run and compile the code:
When in the directory that has the needed files type these commands:
javac *.java
java Tester

Solver:
Creates the needed Matrices and then creates the inverse and then using the power method on the inverse finds the largest eigen value for the inverse, and therefore finds the smallest eigenvalue for the original matrix.

Tester:
Just a tester class to make sure everything works.

Matrix:
A matrix class I created with useful functions.

Description Of Solution:
The solver class first creates the needed matrices and then creates the random vector. It then proceeds with forward and backward elimination, somewhat taking advantage of the fact that its a diagonal matrix, but since we must multiply everything in the identity matrix, it can't take full advantage of it. Also I didn't append the matrices, just so I wouldn't have to split everything up again. After finding the inverse, I used the power method to find the largest eigenvalue for the inverse Matrix, and then therefore I found the smallest eigenvalue for the actual one.

