package client;

import CalculatorExceptions.CalculatorException;
import translator.Translator;

import javax.swing.*;

public class DisplayPrinter {

    String text;
    JTextPane display;

    public DisplayPrinter(JTextPane display, String displayText) {
        this.text = displayText;
        this.display = display;
    }

    public void execCommand() {
        // Special cases depending on type
        switch (text) {
            case "=":
                execute();
                break;
            case "C":
                clear();
                break;
            case "del":
                del();
                break;
            default:
                print(this.text);
        }
    }

    private void print(String text) {
        String currText = display.getText();
        System.out.println("currText before add: " + currText);
        display.setText(currText + text);
        System.out.println("currText after add: " + display.getText());
    }

    private void execute() {
        String currText = display.getText();

        try {
            String firstSpaceRemoved = currText.charAt(0) == ' '?
                    currText.substring(1) : currText;
            firstSpaceRemoved = firstSpaceRemoved.replace("  ", " ");

            display.setText(Translator.calculate(firstSpaceRemoved.trim()));
        } catch (CalculatorException e) {
            clear();
            print(e.getDisplayMessage());
        }
    }

    private void clear() {
        display.setText("");
    }

    private void del() {
        // TODO
        String currText = display.getText();
        display.setText(currText.substring(0, currText.length() - 1));
    }
}
