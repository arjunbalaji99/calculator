package client;

import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;

import CalculatorExceptions.CalculatorException;
import engine.Engine;
import translator.Translator;

import javax.swing.*;

// video i am using - https://www.youtube.com/watch?v=5o3fMLPY7qY

public class UI {

    JFrame f;

    /**
     * Stores the current state of the display (equation or answer) which will be
     * sent to the Translator for evaluation
     */
    String currExp;

    /**
     * Stores Components of the UI as values of name keys. e.g. an entry might be
     * "ButtonsPanel" : &lt;JPanel&gt;
     */
    HashMap<String, JComponent> refs;

    /**
     * Holds the Engine instance which handles history, variables, etc.
     */
    Engine engine;

    ArrayList<String> additionsHistory;

    ArrayList<ArrayList<String>> history;

    public UI() {
        f = new JFrame(); // creating instance of JFrame
        currExp = "";
        refs = new HashMap<>();
        engine = new Engine();
        additionsHistory = new ArrayList<>();
        history = new ArrayList<>();
    }

    /**
     * Initialize all components of the instance
     */
    public void mount() {

        // Call all the initializer functions
        initializePanels();
        initButtons();
        initViewPanel();

        f.setContentPane(refs.get("MainPanel"));
        f.pack();

        // f.setSize(500, 700);
        f.setLocation(100, 200);
        f.setLayout(null);
        f.setVisible(true);
    }

    /**
     * Adds the base containers (overall mainContainer, view panel, buttons panel) to
     * the main JFrame.
     */

    private void initializePanels() {
        // Calculator Body
        JPanel container = new JPanel();
        container.setBounds(0, 0, 300, 200);
        container.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Panel that contains the display (shows equation/answers)
        JPanel view = new JPanel();
        view.setPreferredSize(new Dimension(300, 250));
        c.fill = GridBagConstraints.VERTICAL;

        // Panel containing the controls
        JPanel buttons = new JPanel();

        // Mount on UI
        container.add(view);
        container.add(buttons);

        // Add refs to instance collection
        refs.put("MainPanel", container);
        refs.put("ButtonsPanel", buttons);
        refs.put("ViewPanel", view);
    }

    /**
     * Initializes the view panel (display at the top of the calculator)
     * initializePanels() must have already been called
     */
    private void initViewPanel() {
        JTextArea display = new JTextArea();
        display.setText("0");
        display.setEditable(false);
        display.setLineWrap(true); // Enable text wrapping
        display.setWrapStyleWord(true); // Wrap on word boundaries
        display.setFont(new Font(display.getFont().getName(), Font.PLAIN, 40));
        display.setSize(new Dimension(300, 100));
        refs.put("DisplayText", display);
        refs.get("ViewPanel").add(display);
    }

    /**
     * Initializes the control buttons on the buttons panel.
     * initializePanels() must have already been called.
     */
    private void initButtons() {
        JPanel buttonPanel = (JPanel) refs.get("ButtonsPanel");

        // Grid to arrange buttons on
        GridLayout g = new GridLayout();
        g.setColumns(6);
        g.setRows(8);

        buttonPanel.setLayout(g);

        // Add the buttons
        addNumberButtons(buttonPanel);
        addOperationButtons(buttonPanel);
    }

    private void addNumberButtons(JPanel container) {
        for (int i = 0; i < 10; i++) {
            JButton temp = new JButton();
            temp.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            temp.setBackground(Color.WHITE);
            temp.setSize(150, 200);
            temp.setPreferredSize(new Dimension(50, 50));
            refs.put("Button_" + i, temp);
            temp.setText(i + "");

            int finalI = i;
            temp.addActionListener(actionEvent -> {
                updateExpr(finalI + "");
            });

            container.add(temp);
        }
    }

    private void addOperationButtons(JPanel container) {
        String[] ops = new String[] {
                " + ", " - ", " * ", " / ", ".", "=", " ( ", " ) ", "C", "Del",
                " log10 ( ", " ln ( ", "log", " abs ( ", " ^ ( ", " % ", "[—]",
                " ^ 2 ", " sqrt ( ", " ^ 3 ", " cbrt ( ", "ans",
                " sin ( ", " cos ( ", " tan ( ", " sec ( ", " csc ( ", " cot ( ",
                " arcsin ( ", " arccos ( ", " arctan ( ", " arcsec ( ", " arccsc ( ", " arccot ( ",
        };
        for (String op : ops) {
            JButton temp = new JButton();
            temp.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            temp.setBackground(Color.WHITE);
            temp.setSize(150, 200);
            temp.setPreferredSize(new Dimension(50, 50));

            refs.put("Button_" + op, temp);
            temp.setText(removeTokenizers(op));

            temp.addActionListener(actionEvent -> {
                updateExpr(op);
            });

            container.add(temp);
        }
    }

    /**
     * Removes helper characters from an operation text, such as " sqrt ( " ->
     * "sqrt"
     * Used for display on buttons.
     * Removed chars: " ", "(", ")"
     * 
     * @param originalText Original operation such as " ^ 2 " or " sin ( "
     * @return trimmed and removed chars, such as "^2" or "sin"
     */
    private String removeTokenizers(String originalText) {

        // If it's the parenthesis buttons, don't remove them
        if (originalText.equals(" ( ") || originalText.equals(" ) ")) {
            return originalText.trim();
        }

        return originalText
                .trim()
                .replace(" ", "")
                .replace("(", "")
                .replace(")", "");
    }

    private void updateExpr(String inputChar) {
        try {

            switch (inputChar) {
                case "=":
                    add();
                    break;
                case "C":
                    currExp = "";
                    break;
                case "Del":
                    del();
                    break;
                case "ans":
                    currExp += engine.getAns();
                    break;
                case "log":
                    currExp = "haha this doesnt work";
                    break;
                case "[—]":
                    currExp += "—";
                    break;
                default:
                    currExp += inputChar;
                    additionsHistory.add(inputChar.trim());
            }
        } catch (CalculatorException e) {
            currExp = "";
            ((JTextArea) refs.get("DisplayText")).setText(e.getType());
            return;
        }
        currExp = trimExpression(currExp);
        ((JTextArea) refs.get("DisplayText")).setText(Translator.prettifyExpression(currExp));
    }

    private void add() throws CalculatorException {
        ArrayList<String> historyAddition = new ArrayList<>();
        historyAddition.add(currExp);
        currExp = getResult();
        engine.storeAns(currExp);
        historyAddition.add(currExp);
        history.add(historyAddition);
        additionsHistory.clear();
    }

    private String trimExpression(String currExp) {
        // Due to padding before and after ops, double spaces exist - remove them.
        if (!currExp.isEmpty()) {
            currExp = currExp.replace("  ", " ");
            if (currExp.charAt(currExp.length() - 1) == ' ') {
                if (isNumeric(currExp.substring(currExp.length() - 3, currExp.length() - 1)))
                    currExp = currExp.substring(0, currExp.length() - 1);
            }
        }
        return currExp;
    }

    private void del() {
        if (!currExp.isEmpty()) {
            if (additionsHistory.isEmpty()) {
                currExp = currExp.substring(0, currExp.length() - 1);
            }
            else {
                String deleteExpression = additionsHistory.get(additionsHistory.size() - 1);
                int start = currExp.lastIndexOf(deleteExpression);
                currExp = currExp.substring(0, start);
                additionsHistory.remove(additionsHistory.size() - 1);
            }
        }
    }

    private boolean isNumeric(String token) {
        try {
            Double.parseDouble(token.replace('—', '-'));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Evaluates currExp and returns the answer/error
     */
    private String getResult() throws CalculatorException {
        String firstSpaceRemoved = currExp.charAt(0) == ' '?
                currExp.substring(1) : currExp;

        return Translator.calculate(firstSpaceRemoved).trim();
    }
}
