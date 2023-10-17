package client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NewUI {
    public JPanel mainContainer;
    public JPanel buttonContainer;
    private JButton button_power;
    private JButton button_mod;
    private JButton button_cot;
    private JButton button_csc;
    private JButton button_sec;
    private JButton button_reciprocal;
    private JButton button_square;
    private JButton button_tan;
    private JButton button_cos;
    private JButton button_sin;
    private JButton button_sqrt;
    private JButton button_log10;
    private JButton button_arcsin;
    private JButton button_arccos;
    private JButton button_arctan;
    private JButton button_xroot;
    private JButton button_ln;
    private JButton button_arcsec;
    private JButton button_arccsc;
    private JButton button_arccot;
    private JButton button_backspace;
    private JButton button_pi;
    private JButton button_e;
    private JButton button_plus;
    private JButton button_minus;
    private JButton button_times;
    private JButton button_divide;
    private JButton button_opnpar;
    private JButton button_9;
    private JButton button_8;
    private JButton button_7;
    private JButton button_4;
    private JButton button_5;
    private JButton button_6;
    private JButton button_clspar;
    private JButton button_dot;
    private JButton button_3;
    private JButton button_equals;
    private JButton button_clear;
    private JButton button_negative;
    private JTextPane display;
    private JPanel history;
    private JPanel bodyContainer;
    private JButton button_0;
    private JButton button_1;
    private JButton button_2;
    private JButton button_abs;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setContentPane(new NewUI().mainContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        display = new JTextPane();
        button_1 = new CustomButton("1", new DisplayPrinter(display, "1"));
        button_2 = new CustomButton("2", new DisplayPrinter(display, "2"));
        button_3 = new CustomButton("3", new DisplayPrinter(display, "3"));
        button_4 = new CustomButton("4", new DisplayPrinter(display, "4"));
        button_5 = new CustomButton("5", new DisplayPrinter(display, "5"));
        button_6 = new CustomButton("6", new DisplayPrinter(display, "6"));
        button_7 = new CustomButton("7", new DisplayPrinter(display, "7"));
        button_8 = new CustomButton("8", new DisplayPrinter(display, "8"));
        button_9 = new CustomButton("9", new DisplayPrinter(display, "9"));
        button_0 = new CustomButton("0", new DisplayPrinter(display, "0"));
        button_pi = new CustomButton("π", new DisplayPrinter(display, "π"));
        button_e = new CustomButton("e", new DisplayPrinter(display, "e"));
        button_dot = new CustomButton(".", new DisplayPrinter(display, "."));
        button_equals = new CustomButton("=", new DisplayPrinter(display, "="));
        button_negative = new CustomButton("(—)", new DisplayPrinter(display, "—"));
        button_clear = new CustomButton("C", new DisplayPrinter(display, "C"));
        button_clspar = new CustomButton(")", new DisplayPrinter(display, " ) "));
        button_opnpar = new CustomButton("(", new DisplayPrinter(display, " ( "));
        button_plus = new CustomButton("+", new DisplayPrinter(display, " + "));
        button_minus = new CustomButton("-", new DisplayPrinter(display, " - "));
        button_times = new CustomButton("*", new DisplayPrinter(display, " * "));
        button_divide = new CustomButton("/", new DisplayPrinter(display, " / "));
        button_abs = new CustomButton("abs", new DisplayPrinter(display, " ) "));
        button_log10 = new CustomButton("log10", new DisplayPrinter(display, " log10 ( "));
        button_ln = new CustomButton("ln", new DisplayPrinter(display, " ln ( "));
        button_reciprocal = new CustomButton("1/x", new DisplayPrinter(display, " ^ ( —1 ) "));
        button_power = new CustomButton("^", new DisplayPrinter(display, " ^ "));
        button_mod = new CustomButton("%", new DisplayPrinter(display, " % "));
        button_square = new CustomButton("^2", new DisplayPrinter(display, " ^ 2 "));
        button_sqrt = new CustomButton("sqrt", new DisplayPrinter(display, " sqrt ( "));
        button_xroot = new CustomButton("xroot", new DisplayPrinter(display, " xroot ("));
        button_backspace = new CustomButton("Del", new DisplayPrinter(display, "del"));
        button_sin = new CustomButton("sin", new DisplayPrinter(display, " sin ( "));
        button_cos = new CustomButton("cos", new DisplayPrinter(display, " cos ( "));
        button_tan = new CustomButton("tan", new DisplayPrinter(display, " tan ( "));
        button_sec = new CustomButton("sec", new DisplayPrinter(display, " sec ( "));
        button_csc = new CustomButton("csc", new DisplayPrinter(display, " csc ( "));
        button_cot = new CustomButton("cot", new DisplayPrinter(display, " cot ( "));
        button_arcsin = new CustomButton("arcsin", new DisplayPrinter(display, " arcsin ( "));
        button_arccos = new CustomButton("arccos", new DisplayPrinter(display, " arccos ( "));
        button_arctan = new CustomButton("arctan", new DisplayPrinter(display, " arctan ( "));
        button_arcsec = new CustomButton("arcsec", new DisplayPrinter(display, " arcsec ( "));
        button_arccsc = new CustomButton("arccsc", new DisplayPrinter(display, " arccsc ( "));
        button_arccot = new CustomButton("arccot", new DisplayPrinter(display, " arccot ( "));

        history = new JPanel();
        history.setBorder(new EmptyBorder(0, 0, 0, 0));

        buttonContainer = new JPanel();
        buttonContainer.setBackground(new Color(0, 0, 0, 0));
    }
}

