package engine;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * __Included operations__
 * + - * /
 * 6 trig functions, 6 inverse trig
 * abs, log, ln, exp, sqrt, mod
 * pi, e,
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

    public static double ARCCOT(double x) {
        return Math.atan(1.0 / x);
    }


    /**
     *
     * @param opName any exact string in the following list:
     *               ADD, SUB, MULT, DIV, MOD, ABS, LOG10, LN, LOG,
     *               EXP, SQUARE, SQRT, CUBE, CBRT,
     *               SIN, COS, TAN, SEC, CSC, COT,
     *               ARCSIN, ARCCOS, ARCTAN, ARCSEC, ARCCSC, ARCCOT,
     *
     * @return A double result of the operation
     */
    public static double getResult(String opName, double[] operands) {
        switch (opName) {

            case "ADD":
                return ADD(operands[0], operands[1]);

            case "SUB":
                return SUBTRACT(operands[0], operands[1]);

            case "MULT":
                return MULTIPLY(operands[0], operands[1]);

            case "DIV":
                return DIVIDE(operands[0], operands[1]);


        }
    }
}