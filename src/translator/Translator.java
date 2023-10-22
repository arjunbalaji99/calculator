package translator;

import engine.Engine;

import CalculatorExceptions.CalculatorException;
import engine.Operations;

import java.util.*;

public class Translator {
    public static ArrayList<String> twoNumberOperators = new ArrayList<>(Arrays.asList("+", "-", "*", "/", "%", "^"));
    public static ArrayList<String> oneNumberOperators = new ArrayList<>(Arrays.asList("sqrt", "cbrt", "log10", "ln", "abs", "sin", "cos", "tan", "sec", "cot", "arcsin", "arccos", "arctan", "arcsec", "arccsc", "arccot"));

    /**
     * Checks for errors in the input
     * @param input the input string
     * @throws CalculatorException if there is an error
     */
    public static void findErrors(String input) throws CalculatorException {
        String[] tokens = input.split(" ");

        if (tokens.length < 1) return;

        // multiple decimals
        for (String token : tokens) {
            if (token.length() - token.replace(".", "").length() > 1) throw new CalculatorException("Syntax error: Decimal points");
        }

        // parentheses failure
        int numPars = 0;
        for (String token : tokens) {
            if (token.equals("(")) numPars++;
            else if (token.equals(")")) numPars--;
            if (numPars < 0) throw new CalculatorException("Syntax error: Unmatched parentheses");
        }
        if (numPars > 0) throw new CalculatorException("Syntax error: Unmatched parentheses");

        // starting with two number operator
        if (twoNumberOperators.contains(tokens[0])) throw new CalculatorException("Syntax error: Unparametered operator");
        // multiple two number operators in a row or two number operators with parantheses in front
        for (int i = 1; i < tokens.length; i++) {
            if (twoNumberOperators.contains(tokens[i]) && twoNumberOperators.contains(tokens[i - 1])) {
                throw new CalculatorException("syntax");
            }
            if (twoNumberOperators.contains(tokens[i]) && (tokens[i - 1].equals("("))) {
                throw new CalculatorException("Syntax Error: Unparametered operator");
            }
        }
    }

    /**
     * Adds multiplication signs to the input
     * @param input the input string
     * @return the revised input string
     */
    public static String addMultiplicationSigns(String input) {
        String[] tokens = input.split(" ");
        ArrayList<Integer> timesIndex = new ArrayList<>();
        for (int i = 0; i < tokens.length - 1; i++) {
            if (tokens[i].equals(")")) {
                if (isNumeric(tokens[i + 1]) || oneNumberOperators.contains(tokens[i + 1])) {
                    timesIndex.add(i);
                }
            }
            else if (isNumeric(tokens[i]) && oneNumberOperators.contains(tokens[i + 1])) timesIndex.add(i);
            else if (i != tokens.length - 1 && isNumeric(tokens[i]) && tokens[i + 1].equals("(")) timesIndex.add(i);
            else if (i != tokens.length - 1 && isNumeric(tokens[i]) && oneNumberOperators.contains(tokens[i + 1])) timesIndex.add(i);
        }
        String revisedInput = "";
        for (int i = 0; i < tokens.length; i++) {
            revisedInput += tokens[i] + " ";
            if (timesIndex.contains(i)) {
                revisedInput += "* ";
            }
        }
        return revisedInput;
    }

    /**
     * Calculates the input based on the order of operations
     * @param input the input string
     * @return the calculated value
     * @throws CalculatorException if there is an error
     */
    public static String calculate(String input) throws CalculatorException {
        input = addMultiplicationSigns(input);
        input = removeSpecialChars(input);
        findErrors(input);
        System.out.println(input);
        return calculateRecursive(input);
    }

    /**
     * Calculates the input recursively, going through the order of operations
     * @param input the current input string
     * @return the calculated value
     * @throws CalculatorException if there is an error
     */
    public static String calculateRecursive(String input) throws CalculatorException {
        input = lookForParentheses(input);
        String[] tokens = input.split(" ");
        if (tokens.length <= 1) {
            return input;
        }
        ArrayList<String> statements = splitStatements(tokens);
        String highestPriorityString = highestPriorityOverall(statements);
        double val = Engine.evaluate(highestPriorityString);
        return calculateRecursive(input.replace(highestPriorityString, Double.toString(val)));
    }

    /**
     * Looks for parentheses in the input and calculates the value inside
     * @param input the input string
     * @return the revised input string
     * @throws CalculatorException if there is an error
     */
    public static String lookForParentheses(String input) throws CalculatorException {
        String[] tokens = input.split(" ");
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("(")) {
                String parenthesesExpression = "";
                i++;
                int numPars = 0;
                while (!tokens[i].equals(")") || numPars > 0) {
                    parenthesesExpression += tokens[i] + " ";
                    if (tokens[i].equals("("))
                        numPars++;
                    else if (tokens[i].equals(")"))
                        numPars--;
                    i++;
                }
                String val = calculateRecursive(parenthesesExpression);
                parenthesesExpression = "( " + parenthesesExpression + ") ";
                input = input.replace(parenthesesExpression, val);
            }
        }
        return input;
    }

    /**
     * Splits the input into statements
     * @param tokens the input string
     * @return ArrayList of statements (strings)
     */
    public static ArrayList<String> splitStatements(String[] tokens) {
        ArrayList<String> statements = new ArrayList<>();
        for (int i = 0; i < tokens.length - 1; i++) {
            // means it is an operator
            if (!isNumeric(tokens[i])) {
                if (twoNumberOperators.contains(tokens[i]))
                    statements.add(tokens[i - 1] + " " + tokens[i] + " " + tokens[i + 1]);
                else
                    statements.add(tokens[i] + " " + tokens[i + 1]);
            }
        }
        return statements;
    }

    /**
     * Removes special characters from the input
     * @param input the input string
     * @return the revised input string
     */
    public static String removeSpecialChars(String input) {
        // replace negative signs
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '—') {
                if (i > 0 && isNumeric(input.substring(i - 2, i - 1))) {
                    input = input.substring(0, i) + " - " + input.substring(i + 1);
                }
                else {
                    input = input.substring(0, i) + "-1 * " + input.substring(i + 1);
                }
            }
        }

        // replace e and pi with respective numbers
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 'π') {
                // if it is not the first item
                if (i != 0) {
                    if (!twoNumberOperators.contains(input.substring(i - 2, i - 1))) {
                        input = input.substring(0, i) + "* π" + input.substring(i + 1);
                    }
                    i += 2;
                }
                if (i <= input.length() - 3) {
                    if (!twoNumberOperators.contains(input.substring(i + 2, i + 3))) {
                        input = input.substring(0, i) + "π *" + input.substring(i + 1);
                    }
                    i += 2;
                }
            }
            else if (input.charAt(i) == 'e') {
                // if it is not the first item
                if (i != 0) {
                    if (!twoNumberOperators.contains(input.substring(i - 2, i - 1))) {
                        input = input.substring(0, i) + "* e" + input.substring(i + 1);
                    }
                    i += 2;
                }
                if (i <= input.length() - 3) {
                    if (!twoNumberOperators.contains(input.substring(i + 2, i + 3))) {
                        input = input.substring(0, i) + "e *" + input.substring(i + 1);
                    }
                    i += 2;
                }
            }
        }
        input = input.replace("π", "3.141592653589793238");
        input = input.replace("e", "2.718281828459045235");
        return input;
    }

    /**
     * Prettifies the expression by adding/removing spaces where necessary
     * @param currExp the current expression
     * @return the prettified expression
     */
    public static String prettifyExpression(String currExp) {
        currExp = currExp.replace(" ", "");
        for (int i = 1; i < currExp.length(); i++) {
            if (twoNumberOperators.contains(currExp.substring(i, i + 1))) {
                currExp = currExp.substring(0, i) + " " + currExp.charAt(i) + " " + currExp.substring(i + 1);
                i+=2;
            }
        }
        return currExp;
    }

    public static boolean isNumeric(String token) {
        try {
            Double.parseDouble(token.replace('—', '-'));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getOperator(String statement) {
        String[] tokens = statement.split(" ");
        if (tokens.length == 2)
            return tokens[0];
        else
            return tokens[1];
    }

    public static String highestPriority(String statement1, String statement2) {
        String operator1 = getOperator(statement1), operator2 = getOperator(statement2);
        if (hasHigherPrecedence(operator2, operator1))
            return statement2;
        else
            return statement1;
    }

    private static boolean hasHigherPrecedence(String operator1, String operator2) {
        int precedence1 = getOperatorPrecedence(operator1);
        int precedence2 = getOperatorPrecedence(operator2);
        return precedence1 > precedence2;
    }

    // Get the precedence level of an operator
    private static int getOperatorPrecedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 3;
        }
    }

    /**
     * Finds the highest priority statement in a list of statements
     * @param statements the list of statements
     * @return the highest priority statement
     */
    public static String highestPriorityOverall(List<String> statements) {
        String highestPriorityString = statements.get(0);
        for (int i = 1; i < statements.size(); i++) {
            highestPriorityString = highestPriority(highestPriorityString, statements.get(i));
        }
        return highestPriorityString;
    }
}
