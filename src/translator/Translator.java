package translator;

import engine.Engine;

import java.util.*;

public class Translator {
    public static Engine engine = new Engine();

    public static ArrayList<String> twoNumberOperators = new ArrayList<>(Arrays.asList("+", "-", "*", "/", "%", "^"));

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
//        System.out.print("Enter an expression to evaluate: ");
//        String input = in.nextLine();
//        if (input.isEmpty()) {
//            input = "( ( ( ( 4 ) * 4 ) ) )";
//        }
        String input = "4 * 3";
        if (findErrors(input)) System.out.println("You suck and gave me a bad input");
        else System.out.println(translate(input));
    }

    /**
     * Converts a String from the UI into an expression that can be evaluated
     * Adds in whitespace as well for tokenization
     * @param exp String directly from the UI, in readable format: e.g. 4+2-3, 5*7/(2+5) (no whitespace)
     * @return Translator String formatted, e.g. 4 + 2 - 3, 5 * 7 / ( 2 + 5 )
     */
    public static String readDisplayExpr(String exp) {
        return "bad";
    }

    /**
     * NOTE: "token" refers only to numeric tokens for this method.
     * Examples:
     * <ul>
     * <li>.2314000</li>
     * <li>-9.283</li>
     * <li>6.718</li>
     * <li>0002006</li>
     * <li>-.823</li>
     * </ul>
     *
     * Takes an expression such as 45.3+92.6/3 and a current index (e.g. 0) and returns the index
     * of the last character that is part of the current token. In this case, 45.3 is the first token,
     * so this method is expected to return 3 (index of "3")
     * @param exp untokenized String expression
     * @param start index inside that String to start at (finds the token that exp[currIdx] is a part of)
     * @return int representing the index of the first char NOT in the curr token, returns -1 if provided index was non-numeric.
     */
    private static int endOfNumericToken(String exp, int start) {

        // Delete . and - from this as we parse
        HashSet<String> validChars = new HashSet<>(Arrays.asList(new String[] {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "-"
        }));

        // First char could be neg sign
        if (!validChars.contains(String.valueOf(exp.charAt(start)))) {
            return -1;
        }

        // If the first char is - or ., then we expect a numeric value immediately after
        if (String.valueOf(exp.charAt(start)).equals("-") || String.valueOf(exp.charAt(start)).equals(".")) {
            validChars.remove(String.valueOf(exp.charAt(start)));  // remove once used

            // If next char is NaN or doesn't exist, then this is not a valid numeric token
            if (start + 1 >= exp.length() || !isNumeric(String.valueOf(exp.charAt(start+1)))) {
                return -1;
            }
        }

        validChars.remove("-"); // no negative sign allowed past first char
        int currIndex = start + 1;

        // Go until end, return exp.length() if the number extends all the way to the end
        while (currIndex < exp.length()) {

            String currChar = String.valueOf(exp.charAt(currIndex));

            if (!validChars.contains(currChar)) {
                // Return curr index because it is the first invalid char
                return currIndex;
            }

            if (currChar.equals(".")) {
                // Look at the next char - if its not numeric, then this char is invalid
                if (currIndex + 1 >= exp.length() || isNumeric(String.valueOf(exp.charAt(currIndex)))) {
                    // Return curr index because it is the first invalid char
                    return currIndex;
                }
                validChars.remove(currChar); // remove decimal point once its used
            }

            currIndex++;
        }
        return currIndex;
    }

    public static boolean findErrors(String input) {
        // errors to find - two operators in a row, closed par without an open par, operator without a number/numbers to corroborate
        String[] tokens = input.split(" ");
        // closed par without an open par
        int numPars = 0;
        for (String token : tokens) {
            if (token.equals("(")) numPars++;
            else if (token.equals(")")) numPars--;
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
                } else if (!tokens[i].equals(")") && !tokens[i].equals("(")) {
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
        System.out.println(highestPriorityString);
        double val = engine.evaluate(highestPriorityString);
        return translate(input.replace(highestPriorityString, Double.toString(val)));
    }

    public static String lookForParentheses(String input) {
        String[] tokens = input.split(" ");
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("(")) {
                String paranthesesExpression = "";
                i++;
                int numPars = 0;
                while (!tokens[i].equals(")") || numPars > 0) {
                    paranthesesExpression += tokens[i] + " ";
                    if (tokens[i].equals("(")) numPars++;
                    else if (tokens[i].equals(")")) numPars--;
                    i++;
                }
                String val = translate(paranthesesExpression);
                paranthesesExpression = "( " + paranthesesExpression + ") ";
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

