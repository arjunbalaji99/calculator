package engine;

import java.util.ArrayList;
import java.util.Arrays;

public class Engine {

    double ANS = 0;

    /**
     * Stores the current variables A (0) -> Z (25)
     */
    double[] VARS = new double[26];

    /**
     * Stores a history of the String expressions that have been given to this instance and evaluated.
     */
    ArrayList<String> HIST = new ArrayList<>();

    String CURR_EXP =

    /**
     * Creates an Engine instance with no values loaded in memory (all 0)
     */
    public Engine() {
        Arrays.fill(VARS, 0);
    }

    /**
     * Create an Engine instance with initially set variables
     * @param initVars A double array of 26
     */
    public Engine(double[] initVars) throws IllegalArgumentException {
        if (initVars.length != 26) {
            throw new IllegalArgumentException("initVars must have length 26 to cover each capital letter. Given length: " + initVars.length);
        }
        this.VARS = initVars;
    }

    /**
     * Store a value in the engine's memory - TODO: ensure it gets saved to disk so it transcends engine instances
     * @param varName char A-Z
     * @param value float value to store in that variable
     * @return the value that was stored (same as parameter `value`)
     */
    public float store(char varName, float value) {
        if (Character.isLowerCase(varName)) {
            throw new IllegalArgumentException("varName for store must be a capital letter A-Z. Provided: " + varName);
        }

        int memIndex = (int) varName - 65;
        this.VARS[memIndex] = value;
        return value;

    }

}
