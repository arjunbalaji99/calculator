import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Translator {
    // current operations : ADD, SUBTRACT, MULTIPLY, DIVIDE
    public static void main(String[] args) {
        String input = "3 ADD 4 MULTIPLY 2 DIVIDE 3 SUBTRACT 3 MULTIPLY 4";
        while (true) {
            String[] tokens = input.split(" "); 
            if (tokens.length <= 1) break;
            ArrayList<String> statements = new ArrayList<>();
            for (int i = 1; i < tokens.length; i+=2) {
                statements.add(tokens[i - 1] + " " + tokens[i] + " " + tokens[i + 1]);
            }
            String highestPriorityString = highestPriorityOverall(statements);
            System.out.println(highestPriorityString);
            // can now pass that to engine and get a value, in this example 8
            Scanner in = new Scanner(System.in);
            System.out.print("Enter the value of the expression: ");
            double val = in.nextDouble();
            input = input.replace(highestPriorityString, Double.toString(val));
        } 
        System.out.println(input);
    }

    public static boolean isOperator(String str) {
        return str.contains("ADD") || str.contains("SUBTRACT") || str.contains("MULTIPLY") || str.contains("DIVIDE");
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
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
            case "SUBTRACT":
                return 1;
            case "MULTIPLY":
            case "DIVIDE":
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
