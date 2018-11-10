// ldelamotte17@georgefox.edu
// Lab 1
// 2018-11-10

import java.util.Arrays;
import java.util.Stack;
import java.util.ArrayList;

/**
 * Takes in a mathematical expression in infix format,
 * converts it to postfix, and then evaluates it.
 */
public class StackCalc {


    /**
     * Converts a mathematical expression to its postfix format.
     *
     * @param expression the given mathematical expression.
     * @return the given mathematical expression is postfix format.
     */
    private static ArrayList<String>
    convertFromInfixToPostfix(ArrayList<String> expression)
            throws IllegalArgumentException {

        ArrayList<String> postfix = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (String token : expression) {

            // if the token is a closing parenthesis, the stack is popped
            // and added to the postfix until another open parenthesis is found
            if (isClosingParenthesis(token)) {
                while (!stack.isEmpty() && !isOpeningParenthesis(stack.peek())) {
                    postfix.add(stack.peek());
                    stack.pop();
                }
                stack.pop();
            }

            // if the token is an open parenthesis it is added to stack
            else if (isOpeningParenthesis(token)) {
                stack.push(token);
            }

            // if the token is an operator, its precedence is compared to
            // the one currently on the stack if applicable and then
            // handled accordingly
            else if (isOperator(token)) {
                while (!stack.isEmpty() && !isOpeningParenthesis(stack.peek())
                        && (precedence(stack.peek()) >= precedence(token))) {
                    postfix.add(stack.peek());
                    stack.pop();
                }
                stack.push(token);
            }

            // if the token is an operand it is added to postfix
            else if (!isOperator(token)) {
                postfix.add(token);
            }

        }

        // after iteration through the expression is complete, all
        // tokens still in the stack are added to the postfix
        while (!stack.isEmpty()) {
            postfix.add(stack.peek());
            stack.pop();
        }

        return postfix;

    }


    /**
     * Evaluates the mathematical result of an expression in postfix format.
     *
     * @param postfix a mathematical expression in postfix format.
     * @return the mathematical result of an expression in postfix format.
     * @throws IllegalArgumentException if parenthesis are included in
     *                                  the postfix expression.
     */
    private static double evaluatePostfix(ArrayList<String> postfix) throws
            IllegalArgumentException {

        Stack<String> stack = new Stack<>();
        double firstOperand;
        double secondOperand;
        double result;

        // iterates through the postfix expression one by one
        for (String token : postfix) {

            // checks to see if parenthesis are in the postfix
            // expression and throws an error if so
            if (isOpeningParenthesis(token)
                    || isClosingParenthesis(token)) {
                throw new IllegalArgumentException();
            }

            // if the character at hand is an operator, two operands are
            // popped from the stack and evaluated via the given operator
            else if (isOperator(token)) {
                secondOperand = Double.parseDouble(stack.pop());
                firstOperand = Double.parseDouble(stack.pop());
                result = performOperation(token,
                        firstOperand, secondOperand);
                stack.push(result + "");
            }

            // if the character at hand is an operand, it is pushed to the stack
            else {
                stack.push(token);
            }

        }

        // returns the final result of the postfix expression
        return Double.parseDouble(stack.pop());

    }


    /**
     * Determines the level of precedence of a given operator.
     *
     * @param operator the given operator.
     * @return the operator's level of precedence.
     */
    private static int precedence(String operator) {

        int precedence = -1;

        if (operator.equals("+") || operator.equals("-")) {
            precedence = 1;
        }

        else if (operator.equals("*") || operator.equals("/") || operator.equals("%")) {
            precedence = 2;
        }

        return precedence;

    }


    /**
     * Determines whether or not the given token is an operator.
     *
     * @param operator the token to be examined.
     * @return whether or not the given token is an operator.
     */
    private static boolean isOperator(String operator) {

        boolean isOperator = false;

        if (operator.equals("+") || operator.equals("-") || operator.equals("*")
                || operator.equals("/") || operator.equals("%")) {
            isOperator = true;
        }

        return isOperator;

    }


    /**
     * Determines whether or not the given token is an opening parenthesis.
     *
     * @param character the token to be examined.
     * @return whether or not the given token is an operator.
     */
    private static boolean isOpeningParenthesis(String character) {

        boolean isOpeningParenthesis = false;

        if (character.equals("(") || character.equals("[") || character.equals("{")) {
            isOpeningParenthesis = true;
        }

        return isOpeningParenthesis;

    }


    /**
     * Determines whether or not the given token is a closing parenthesis.
     *
     * @param character the token to be examined.
     * @return whether or not the given token is an operator.
     */
    private static boolean isClosingParenthesis(String character) {

        boolean isClosingParenthesis = false;

        if (character.equals(")") || character.equals("]") || character.equals("}")) {
            isClosingParenthesis = true;
        }

        return isClosingParenthesis;

    }


    /**
     * Performs a mathematical operation given two digits and an operator.
     *
     * @param operator the operator of the expression evaluated.
     * @param firstOperand the first operand of the expression evaluated.
     * @param secondOperand the second operand of the expression evaluated.
     * @return the result of the mathematical operation.
     * @throws RuntimeException if any kind of mathematical error occurs.
     */
    private static double performOperation(String operator,
                                        double firstOperand,
                                           double secondOperand)
            throws RuntimeException {

        double result = -1;

        if (operator.equals("+")) {
            result = firstOperand + secondOperand;
        }

        else if (operator.equals("-")) {
            result = firstOperand - secondOperand;
        }

        else if (operator.equals("*")) {
            result = firstOperand * secondOperand;
        }

        else if (operator.equals("/")) {
            result = firstOperand / secondOperand;
        }

        else if (operator.equals("%")) {
            result = firstOperand % secondOperand;
        }

        else {
            throw new RuntimeException("Unsupported operator.");
        }

        return result;

    }


    /**
     * Takes in a mathematical expression either in infix or postfix format
     * (which is denoted by a -p or --postfix), evaluates the expression,
     * and prints the result.
     * .
     * @param args a mathematical expression either in infix or postfix
     *             format (which is denoted by a -p or --postfix).
     */
    public static void main(String[] args) {

        int exitStatusCode = 0;
        ArrayList<String> postfixExpression;
        double result = 0;
        ArrayList<String> initialExpression;

        try {

            initialExpression = new ArrayList<>(Arrays.asList(args));

            // if arguments are already in postfix format, it is simply evaluated
            if (initialExpression.get(0).equals(("-p"))
                    || initialExpression.get(0).equals(("--postfix"))) {
                // removes the String that denotes postfix
                initialExpression.remove(0);
                result = evaluatePostfix(initialExpression);
            }

            // if arguments are not already in postfix format, they
            // are first converted to postfix and then evaluated
            else {
                    postfixExpression =
                            convertFromInfixToPostfix(initialExpression);
                    result = evaluatePostfix(postfixExpression);
            }

            // checks if result is an infinite number or not a number at all
            if (Double.isInfinite(result) || Double.isNaN(result)) {
                System.out.print("NaN");
            }

            // if no arguments were given, status code is set to 1
            else if (initialExpression.size() < 1) {
                exitStatusCode = 1;
            }

            // if arguments were given properly and no errors
            // were thrown, the mathematical result is printed
            else {
                System.out.println(result);
            }
        }

        // all exceptions are caught
        catch (Exception e) {
            exitStatusCode = 2;
        }

        // status code is printed
        System.exit(exitStatusCode);

    }

}
