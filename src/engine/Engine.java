package engine;

import CalculatorExceptions.CalculatorException;

import java.util.ArrayList;
import java.util.Arrays;

public class Engine {

    String ANS = "";

    /**
     * Stores the current variables A (0) -> Z (25)
     */
    double[] VARS = new double[26];

    /**
     * Stores a history of the String expressions that have been given to this
     * instance and evaluated.
     */
    ArrayList<String> HIST = new ArrayList<>();

    String CURR_EXP;

    /**
     * Creates an Engine instance with no values loaded in memory (all 0)
     */
    public Engine() {
        Arrays.fill(VARS, 0);
    }

    /**
     * Create an Engine instance with initially set variables
     * 
     * @param initVars A double array of 26
     */
    public Engine(double[] initVars) throws IllegalArgumentException {
        if (initVars.length != 26) {
            throw new IllegalArgumentException(
                    "initVars must have length 26 to cover each capital letter. Given length: " + initVars.length);
        }
        this.VARS = initVars;
    }

    public void addHistory(String result) {
        HIST.add(result);
    }

    public void storeAns(String result) {
        ANS = result;
    }

    public String getAns() {
        return ANS;
    }

    /**
     * Store a value in the engine's memory - ensure it gets saved to disk so it
     * transcends engine instances
     * 
     * @param varName char A-Z
     * @param value   double value to store in that variable
     * @return the value that was stored (same as parameter `value`)
     */
    public double store(char varName, double value) {
        if (Character.isLowerCase(varName)) {
            throw new IllegalArgumentException("varName for store must be a capital letter A-Z. Provided: " + varName);
        }

        int memIndex = (int) varName - 65;
        this.VARS[memIndex] = value;
        return value;
    }

    /**
     * Recall a variable from the engine memory
     * 
     * @param varName char A-Z, must be capitalized
     * @return double value stored at that variable name
     */
    public double recall(char varName) {
        if (Character.isLowerCase(varName)) {
            throw new IllegalArgumentException("varName to recall must be a capital letter A-Z. Provided: " + varName);
        }

        int memIndex = (int) varName - 65;
        return this.VARS[memIndex];
    }

    /**
     * Evaluate a String expression of the form `x (OPNAME) y` or `(OPNAME) x`. Only
     * one opname allowed.
     * 
     * @param expression String formatted as `x (OPNAME) y` or `(OPNAME) x`. One
     *                   whitespace between tokens.
     * @return the result of the operation.
     */
    public static double evaluate(String expression) throws CalculatorException {
        // superior functionality
//        if (expression.equals("9 + 10")) return 21.0;

        String[] tokens = expression.split(" ");

        if (tokens.length == 2) {
            // (OPNAME) x format
            return Operations.getResult(tokens[0], new double[] { Double.parseDouble(tokens[1]) });

        } else if (tokens.length == 3) {
            // x (OPNAME) y format
            return Operations.getResult(
                    tokens[1],
                    new double[] { Double.parseDouble(tokens[0]),
                            Double.parseDouble(tokens[2])});
        }

        throw new IllegalArgumentException(
                "Invalid number of tokens provided for expression. 2 or 3 required. Provided: " + tokens.length);
    }

}
