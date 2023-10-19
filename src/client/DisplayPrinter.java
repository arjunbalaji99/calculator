package client;

import CalculatorExceptions.CalculatorException;
import translator.Translator;

import javax.swing.*;
import java.util.ArrayList;

public class DisplayPrinter {

    String text;
    JTextPane display;
    private static ArrayList<String> additionsHistory;

    public DisplayPrinter(JTextPane display, String displayText) {
        this.text = displayText;
        this.display = display;
        additionsHistory = new ArrayList<>();
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
                print(text);
        }
    }

    private void print(String text) {
        additionsHistory.add(text);
        String currText = display.getText();
        display.setText(currText + text);
    }

    private void execute() {
        String currText = display.getText();

        try {
            String firstSpaceRemoved = currText.charAt(0) == ' '?
                    currText.substring(1) : currText;
            firstSpaceRemoved = firstSpaceRemoved.replace("  ", " ");

            display.setText(Translator.calculate(firstSpaceRemoved.trim()));
            additionsHistory.clear();
        } catch (CalculatorException e) {
            clear();
            print(e.getDisplayMessage());
        }
    }

    private void clear() {
        display.setText("");
        additionsHistory.clear();
    }

    private void del() {
        System.out.println(additionsHistory);
        String currText = display.getText();
        if (currText.isEmpty()) display.setText(currText);
        else {
            if (additionsHistory.isEmpty()) {
                display.setText(currText.substring(0, currText.length() - 1));
                return;
            }
            String deleteExpression = additionsHistory.get(additionsHistory.size() - 1);
            int start = currText.lastIndexOf(deleteExpression);
            display.setText(currText.substring(0, start));
            additionsHistory.remove(additionsHistory.size() - 1);
        }
    }
}
