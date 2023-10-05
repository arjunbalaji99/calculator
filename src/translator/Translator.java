package translator;

import engine.Engine;

import CalculatorExceptions.CalculatorException;

import java.util.*;

public class Translator {
    public static Engine engine = new Engine();

    public static ArrayList<String> twoNumberOperators = new ArrayList<>(Arrays.asList("+", "-", "*", "/", "%", "^"));

    public static void main(String[] args) {
        // Scanner in = new Scanner(System.in);
        // System.out.print("Enter an expression to evaluate: ");
        // String input = in.nextLine();
        // if (input.isEmpty()) {
        // input = "( ( ( ( 4 ) * 4 ) ) )";
        // }
        String input = "4 * 3";
        System.out.println(calculate(input));
    }
    
    public static boolean findErrors(String input) throws CalculatorException {
        String[] tokens = input.split(" ");
        
        // parentheses failure
        int numPars = 0;
        for (String token : tokens) {
            if (token.equals("(")) numPars++;
            else if (token.equals(")")) numPars--;
            if (numPars < 0) throw new CalculatorException();
        }
        if (numPars > 0) return true;
        
        // multiple two number operators in a row
        for (int i = 1; i < tokens.length; i++) {
            if (twoNumberOperators.contains(tokens[i]) && twoNumberOperators.contains(tokens[i - 1])) return true;
        }
        return false;
    }

    public static String calculate(String input) throws CalculatorException {
        findErrors(input);
        input = lookForParentheses(input);
        String[] tokens = input.split(" ");
        if (tokens.length <= 1) {
            return input;
        }
        ArrayList<String> statements = splitStatements(tokens);
        String highestPriorityString = highestPriorityOverall(statements);
        System.out.println(highestPriorityString);
        double val = engine.evaluate(highestPriorityString);
        return calculate(input.replace(highestPriorityString, Double.toString(val)));
    }

    public static String lookForParentheses(String input) {
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
                String val = calculate(parenthesesExpression);
                parenthesesExpression = "( " + parenthesesExpression + ") ";
                input = input.replace(parenthesesExpression, val);
            }
        }
        return input;
    }

    public static ArrayList<String> splitStatements(String[] tokens) {
        ArrayList<String> statements = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) {
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

    public static String highestPriorityOverall(List<String> statements) {
        String highestPriorityString = statements.get(0);
        for (int i = 1; i < statements.size(); i++) {
            highestPriorityString = highestPriority(highestPriorityString, statements.get(i));
        }
        return highestPriorityString;
    }
}
