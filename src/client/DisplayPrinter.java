package client;

import CalculatorExceptions.CalculatorException;
import translator.Translator;

import javax.swing.*;
import java.util.ArrayList;

public class DisplayPrinter {

    String text;
    JTextPane display;
    ArrayList<String> additionsHistory;

    public DisplayPrinter(JTextPane display, String displayText) {
        this.text = displayText;
        this.display = display;
        this.additionsHistory = new ArrayList<>();
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
                additionsHistory.add(this.text);
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
        String currText = display.getText();
        if (currText.isEmpty()) display.setText(currText);
        else {
            if (additionsHistory.isEmpty()) display.setText(currText.substring(0, currText.length() - 1));
            String deleteExpression = additionsHistory.get(additionsHistory.size() - 1);
            int start = currText.lastIndexOf(deleteExpression);
            display.setText(currText.substring(0, start));
            additionsHistory.remove(additionsHistory.size() - 1);
        }
    }
}
