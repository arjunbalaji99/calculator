package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.util.HashMap;

import translator.Translator;

import javax.swing.*;
import javax.swing.border.Border;

// video i am using - https://www.youtube.com/watch?v=5o3fMLPY7qY

public class UI {

    JFrame f;

    /**
     * Stores the current state of the display (equation or answer) which will be sent to the Translator for evaluation
     */
    String currExp;

    /**
     * Stores Components of the UI as values of name keys. e.g. an entry might be "ButtonsPanel" : &lt;JPanel&gt;
     */
    HashMap<String, JComponent> refs;

    /**
     * Run this with no args to generate the calculator UI.
     * @param args idk
     */
    public static void main(String[] args) {
        UI ui = new UI();
        ui.mount();

    }

    public UI() {
        f = new JFrame(); //creating instance of JFrame
        currExp = "";
        refs = new HashMap<>();
    }

    /**
     * Initialize all components of the instance
     */
    private void mount() {

        // Call all the initializer functions
        initializePanels();
        initButtons();
        initViewPanel();

        f.add(refs.get("MainPanel"));

        f.setSize(500,700);
        f.setLocation(200, 300);
        f.setLayout(null);
        f.setVisible(true);
    }

    /**
     * Adds the base containers (overall container, view panel, buttons panel) to the main JFrame.
     */

    private void initializePanels() {
        // Calculator Body
        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        container.setBounds(0, 0, 500, 700);

        // Panel that contains the display (shows equation/answers)
        JPanel view = new JPanel();
        view.setBackground(Color.LIGHT_GRAY);
        view.setBounds(0, 0, 500, 250);

        // Panel containing the controls
        JPanel buttons = new JPanel();
        buttons.setBounds(0, 250, 500, 250);

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
        JLabel display = new JLabel();
        display.setText("0");
        display.setBackground(Color.WHITE);
        display.setBounds(10, 10, 480, 230);
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
        g.setRows(5);
        g.setColumns(4);

        buttonPanel.setLayout(g);

        // Add the buttons
        addNumberButtons(buttonPanel);
        addOperationButtons(buttonPanel);
    }

    private void addNumberButtons(JPanel container) {
        for (int i = 0; i < 10; i++) {
            JButton temp = new JButton();
            refs.put("Button_" + i, temp);
            temp.setText(i + "");

            int finalI = i;
            temp.addActionListener(actionEvent -> { updateExpr(finalI + ""); });

            container.add(temp);
        }
    }

    private void addOperationButtons(JPanel container) {
        String[] ops = new String[] {"+", "-", "*", "/", ".", "=", "(", ")", "C"};
        for (String op : ops) {
            JButton temp = new JButton();
            refs.put("Button_" + op, temp);
            temp.setText(op);

            temp.addActionListener(actionEvent -> { updateExpr(op); });

            container.add(temp);
        }
    }

    private void updateExpr(String inputChar) {
        currExp = inputChar.equals("=")? getResult() : currExp + inputChar;
        ((JLabel) refs.get("DisplayText")).setText(currExp);
    }

    /**
     * Evaluates currExp and returns the answer/error
     */
    private String getResult() {
        return "Coming Soon";
    }
}
