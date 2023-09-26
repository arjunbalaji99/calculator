package translator;

import engine.Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Translator {
    public static Engine engine = new Engine();

    public static String[] singleNumberOperators = [];

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter an expression to evaluate: ");
        String input = in.nextLine();
        if (input.isEmpty()) {
            input = "3 ADD 4 MULT 2 DIV 3 SUB 3 MULT 4";
        }

        System.out.println(translate(input));
    }

    public static String translate(String input) {
        String[] tokens = input.split(" ");
        if (tokens.length <= 1) return input;
        ArrayList<String> statements = splitStatements(tokens)
        String highestPriorityString = highestPriorityOverall(statements);
        double val = engine.evaluate(highestPriorityString);
        return translate(input.replace(highestPriorityString, Double.toString(val)));
    }

    public static ArrayList<String> splitStatements(String[] tokens) {
        for (String token : tokens) {
            if (!isNumeric(token)) {

            }
        }
    }

    public static boolean isNumeric(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static String highestPriority(String statement1, String statement2) {
        String[] tokens1 = statement1.split(" "); 
        String operator1 = tokens1[1];

        String[] tokens2 = statement2.split(" "); 
        String operator2 = tokens2[1];

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
                return 0;
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
