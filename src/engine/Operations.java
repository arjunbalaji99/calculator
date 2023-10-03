package engine;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

class InvalidOperationError {

}

/**
 * __Included operations__
 * + - * /
 * 6 trig functions, 6 inverse trig
 * abs, log, ln, exp, sqrt, mod
 */
public class Operations {

    /* Basic operations */
    public static double ADD(double x, double y) {
        return x + y;
    }

    public static double SUBTRACT(double x, double y) {
        return x - y;
    }

    public static double MULTIPLY(double x, double y) {
        return x * y;
    }

    public static double DIVIDE(double x, double y) {
        return x / y;
    }

    /* Scientific operations */
    public static double SQRT(double x) {
        return Math.sqrt(x);
    }

    public static double SQUARE(double x) {
        return x * x;
    }

    public static double CBRT(double x) {
        return Math.cbrt(x);
    }

    public static double CUBE(double x) {
        return x * x * x;
    }

    public static double MOD(double num, double modBy) {
        return num % modBy;
    }

    public static double ABS(double x) {
        return Math.abs(x);
    }

    public static double LOG10(double x) {
        return Math.log10(x);
    }

    public static double LN(double x) {
        return Math.log(x);
    }

    public static double LOG(double base, double num) {
        return Math.log(num) / Math.log(base);
    }

    public static double EXP(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    /* Trig functions */
    public static double SIN(double x) {
        return Math.sin(x);
    }

    public static double COS(double x) {
        return Math.cos(x);
    }

    public static double TAN(double x) {
        return Math.tan(x);
    }

    public static double SEC(double x) {
        return 1.0 / Math.cos(x);
    }

    public static double CSC(double x) {
        return 1.0 / Math.sin(x);
    }

    public static double COT(double x) {
        return 1.0 / Math.tan(x);
    }

    public static double ARCSIN(double x) {
        return Math.asin(x);
    }

    public static double ARCCOS(double x) {
        return Math.acos(x);
    }

    public static double ARCTAN(double x) {
        return Math.atan(x);
    }

    public static double ARCSEC(double x) {
        return Math.acos(1.0 / x);
    }

    public static double ARCCSC(double x) {
        return Math.asin(1.0 / x);
    }

    public static double ARCCOT(double x) { return Math.atan(1.0 / x); }

    /**
     *
     * @param opName any exact string representing an operation like "+", "-", "sin", "log10"
     *
     * @return A double result of the operation
     */
    public static double getResult(String opName, double[] operands) throws IllegalArgumentException {

        try {
            switch (opName) {

                case "+":
                    return ADD(operands[0], operands[1]);

                case "-":
                    return SUBTRACT(operands[0], operands[1]);

                case "*":
                    return MULTIPLY(operands[0], operands[1]);

                case "/":
                    return DIVIDE(operands[0], operands[1]);

                case "sqrt":
                    return SQRT(operands[0]);

                case "cbrt":
                    return CBRT(operands[0]);

                case "%":
                    return MOD(operands[0], operands[1]);

                case "log10":
                    return LOG10(operands[0]);

                case "ln":
                    return LN(operands[0]);

                case "log":
                    return LOG(operands[0], operands[1]);

                case "abs":
                    return ABS(operands[0]);

                case "^":
                    return EXP(operands[0], operands[1]);

                case "sin":
                    return SIN(operands[0]);

                case "cos":
                    return COS(operands[0]);

                case "tan":
                    return TAN(operands[0]);

                case "sec":
                    return SEC(operands[0]);

                case "csc":
                    return CSC(operands[0]);

                case "cot":
                    return COT(operands[0]);

                case "arcsin":
                    return ARCSIN(operands[0]);

                case "arccos":
                    return ARCCOS(operands[0]);

                case "arctan":
                    return ARCTAN(operands[0]);

                case "arcsec":
                    return ARCSEC(operands[0]);

                case "arccsc":
                    return ARCCSC(operands[0]);

                case "arccot":
                    return ARCCOT(operands[0]);

                default:
                    throw new IllegalArgumentException("Invalid operation requested for " + opName + " with operands " + Arrays.toString(operands));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid operands provided for operation " + opName + ". (" + operands.length + " were given)");
        }
    }

    public HashMap<String[], Integer> OperationOrder = new HashMap<String[], Integer>() {{
        put(new String[] { "EXP", "LOG", "LN", "LOG10" }, 2);
        put(new String[] { "MULT", "DIV" }, 1);
        put(new String[] { "ADD", "SUB" }, 0);
    }};
}