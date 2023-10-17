package client;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Thingy2 {
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
    private JButton button_logx;
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
        JFrame frame = new JFrame("Thingy2");
        frame.setContentPane(new Thingy2().mainContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        button_1 = new CustomButton("1");
        button_2 = new CustomButton("2");
        button_3 = new CustomButton("3");
        button_4 = new CustomButton("4");
        button_5 = new CustomButton("5");
        button_6 = new CustomButton("6");
        button_7 = new CustomButton("7");
        button_8 = new CustomButton("8");
        button_9 = new CustomButton("9");
        button_0 = new CustomButton("0");
        button_dot = new CustomButton(".");
        button_equals = new CustomButton("=");
        button_negative = new CustomButton("-");
        button_clear = new CustomButton("C");
        button_clspar = new CustomButton(")");
        button_opnpar = new CustomButton("(");
        button_plus = new CustomButton("+");
        button_minus = new CustomButton("-");
        button_times = new CustomButton("*");
        button_divide = new CustomButton("/");
        button_abs = new CustomButton("abs");
        button_log10 = new CustomButton("log10");
        button_ln = new CustomButton("ln");
        button_logx = new CustomButton("logx");
        button_reciprocal = new CustomButton("1/x");
        button_power = new CustomButton("^");
        button_mod = new CustomButton("%");
        button_pi = new CustomButton("pi");
        button_square = new CustomButton("^2");
        button_sqrt = new CustomButton("sqrt");
        button_xroot = new CustomButton("xroot");
        button_backspace = new CustomButton("<-");
        button_sin = new CustomButton("sin");
        button_cos = new CustomButton("cos");
        button_tan = new CustomButton("tan");
        button_sec = new CustomButton("sec");
        button_csc = new CustomButton("csc");
        button_cot = new CustomButton("cot");
        button_arcsin = new CustomButton("arcsin");
        button_arccos = new CustomButton("arccos");
        button_arctan = new CustomButton("arctan");
        button_arcsec = new CustomButton("arcsec");
        button_arccsc = new CustomButton("arccsc");
        button_arccot = new CustomButton("arccot");

        history = new JPanel();
        history.setBorder(new EmptyBorder(0, 0, 0, 0));

        buttonContainer = new JPanel();
        buttonContainer.setBackground(new Color(0, 0, 0, 0));
    }
}
