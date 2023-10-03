package translator;

import engine.Engine;

import java.util.*;

public class Translator {
    public static Engine engine = new Engine();

    public static ArrayList<String> twoNumberOperators = new ArrayList<>(Arrays.asList("ADD", "SUB", "MULT", "DIV", "MOD", "EXP", "LOG"));

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter an expression to evaluate: ");
        String input = in.nextLine();
        if (input.isEmpty()) {
            input = "OPNPAR OPNPAR OPNPAR OPNPAR 4 CLSPAR MULT 4 CLSPAR CLSPAR CLSPAR";
        }
        if (findErrors(input)) System.out.println("You suck and gave me a bad input");
        else System.out.println(translate(input));
    }

    public static boolean findErrors(String input) {
        // errors to find - two operators in a row, closed par without an open par, operator without a number/numbers to corroborate
        String[] tokens = input.split(" ");
        // closed par without an open par
        int numPars = 0;
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("OPNPAR")) numPars++;
            else if (tokens[i].equals("CLSPAR")) numPars--;
            if (numPars < 0) return true;
        }
        // two operators in a row, or operator without a number to corroborate
        for (int i = 0; i < tokens.length; i++) {
            // if its an operator
            if (!isNumeric(tokens[i])) {
                // if it requires two numbers
                if (twoNumberOperators.contains(tokens[i])) {
                    if (i == tokens.length - 1 || i == 0) return true;
                    else if (twoNumberOperators.contains(tokens[i - 1]) || twoNumberOperators.contains(tokens[i + 1])) return true;
                } else if (!tokens[i].equals("CLSPAR") && !tokens[i].equals("OPNPAR")) {
                    if (i == tokens.length - 1) return true;
                    else if (twoNumberOperators.contains(tokens[i + 1])) return true;
                }
            }
        }
        return false;
    }

    public static String translate(String input) {
        input = lookForParentheses(input);
        String[] tokens = input.split(" ");
        if (tokens.length <= 1) return input;
        ArrayList<String> statements = splitStatements(tokens);
        String highestPriorityString = highestPriorityOverall(statements);
        double val = engine.evaluate(highestPriorityString);
        return translate(input.replace(highestPriorityString, Double.toString(val)));
    }

    public static String lookForParentheses(String input) {
        String[] tokens = input.split(" ");
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("OPNPAR")) {
                String paranthesesExpression = "";
                i++;
                int numPars = 0;
                while (!tokens[i].equals("CLSPAR") || numPars > 0) {
                    paranthesesExpression += tokens[i] + " ";
                    if (tokens[i].equals("OPNPAR")) numPars++;
                    else if (tokens[i].equals("CLSPAR")) numPars--;
                    i++;
                }
                String val = translate(paranthesesExpression);
                paranthesesExpression = "OPNPAR " + paranthesesExpression + "CLSPAR ";
                input = input.replace(paranthesesExpression, val);
            }
        }
        return input;
    }

    public static ArrayList<String> splitStatements(String[] tokens) {
        ArrayList<String> statements = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) {
            // means it is an operator
            if (!isNumeric(tokens[i])) {
                if (twoNumberOperators.contains(tokens[i])) statements.add(tokens[i - 1] + " " + tokens[i] + " " + tokens[i + 1]);
                else statements.add(tokens[i] + " " + tokens[i + 1]);
            }
        }
        return statements;
    }

    public static boolean isNumeric(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getOperator(String statement) {
        String[] tokens = statement.split(" ");
        String operator = "";
        if (tokens.length == 2) return tokens[0];
        else return tokens[1];
    }

    public static String highestPriority(String statement1, String statement2) {
        String operator1 = getOperator(statement1), operator2 = getOperator(statement2);
        if (hasHigherPrecedence(operator2, operator1)) return statement2;
        else return statement1;
    }

    private static boolean hasHigherPrecedence(String operator1, String operator2) {
        int precedence1 = getOperatorPrecedence(operator1);
        int precedence2 = getOperatorPrecedence(operator2);
        return precedence1 > precedence2;
    }

    // Get the precedence level of an operator
    private static int getOperatorPrecedence(String operator) {
        switch (operator) {
            case "ADD":
            case "SUB":
                return 1;
            case "MULT":
            case "DIV":
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
