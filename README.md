# infix-postfix-calculator
A CLI driven program that takes in an expression in either infix or postfix notation and calculates the mathematical result.


# Requirements for infix-postfix-calculator:

1. Accepts, by default, an arithmetic expression in infix notation using +, -, *, /, and % operators, along with ( and ) (open- and close-parentheses) for overriding order of operations, as command-line arguments.
2. If the first argument is -p or --postfix, accepts an arithmetic expression in postfix notation (supporting the same operators indicated above for infix notation, but recall that parentheses are not necessary in postfix notation, and are in fact invalid, as the order of the tokens alone determines the order of operations) as command-line arguments.
3. If the expression is valid, evaluates and prints the solution.
4. If the expression is valid, but results in division by zero (or similar), prints "NaN" (not a number) or "Inf" (infinity).
5. Exits with a status code of 0 on success, 1 if no arguments are provided, or 2 if the expression is invalid.
6. Individual tokens are to be whitespace-delimited on the command line.

P.S. It would be wrong of me not to include my favorite gif in this README, mostly because I think it pretty accurately represents me as a software developer. 

![Alt Text](https://media.giphy.com/media/o0vwzuFwCGAFO/giphy.gif)
