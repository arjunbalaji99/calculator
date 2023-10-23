package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;

import CalculatorExceptions.CalculatorException;
import engine.Engine;
import translator.Translator;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

// video i am using - https://www.youtube.com/watch?v=5o3fMLPY7qY

public class UI {

    static final int HISTORY_PANEL_WIDTH = 500;

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

    GridBagLayout gbl;

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
        container.setBackground(new Color(52, 59, 70));
        GridBagConstraints c = new GridBagConstraints();

        // Panel that contains the display (shows equation/answers)
        JPanel view = new JPanel();
        view.setPreferredSize(new Dimension(300, 150));
        view.setBackground(new Color(26, 32, 42));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 10;
        container.add(view, c);

        // Panel containing the controls
        JPanel buttons = new JPanel();
        buttons.setBackground(new Color(52, 59, 70));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        container.add(buttons, c);

        // Add history panel - when expression is evaled, add a new JTextPane to this
        JPanel historyPanel = new JPanel();
        historyPanel.setBackground(new Color(65, 72, 83));
        historyPanel.setForeground(Color.WHITE);

        // History panel layout: Items should stack vertically, each with a fixed size, Scroll if overflowing.
        // Justify items to the top of the panel.
        this.gbl = new GridBagLayout();
        gbl.columnWidths = new int[] {0};
        gbl.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl.columnWeights = new double[] {0.0};
        gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        historyPanel.setLayout(gbl);
        historyPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        JScrollPane scrollPane = new JScrollPane(historyPanel);
        scrollPane.setPreferredSize(new Dimension(HISTORY_PANEL_WIDTH, 740));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 2;
        historyPanel.setPreferredSize(new Dimension(HISTORY_PANEL_WIDTH, 740));
        container.add(scrollPane, c);

        // Add refs to instance collection
        refs.put("MainPanel", container);
        refs.put("ButtonsPanel", buttons);
        refs.put("ViewPanel", view);
        refs.put("HistoryPanel", historyPanel);
        refs.put("HistoryScrollPane", scrollPane);
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

        // Make display fit to the width of its container (the view panel)
        display.setPreferredSize(new Dimension(CustomButton.BUTTON_WIDTH * 5 + 10, 150));

        display.setBackground(new Color(26, 32, 42));
        display.setForeground(Color.WHITE);
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
        g.setColumns(5);
        g.setRows(9);
        g.setHgap(5);
        g.setVgap(5);

        buttonPanel.setLayout(g);

        // Add the buttons
        addNumberButtons(buttonPanel);
        addOperationButtons(buttonPanel);
    }

    /**
     * Adds the number buttons to the buttons panel
     *
     * @param container The buttons panel
     */
    private void addNumberButtons(JPanel container) {
        for (int i = 0; i < 10; i++) {
            CustomButton temp = new CustomButton(i + "");
            refs.put("Button_" + i, temp);

            final int finalI = i;
            temp.addActionListener(actionEvent -> {
                updateExpr(finalI + "");
            });

            container.add(temp);
        }
    }

    private void addOperationButtons(JPanel container) {
        String[] ops = new String[] {
                " + ", " - ", ".", "Del", "=",
                " * ", " / ", " ( ", " ) ", "C",
                " ans ", " π ", "[—]", " ^ ( ", " sqrt ( ",
                " log10 (", " ln ( ", " ℯ ", " % ", " abs ( ",
                " sin ( ", "csc ( ", " arcsin ( ", " arccsc ( ", " ^ 2 ",
                " cos ( ", " sec ( ", " arccos ( ", " arcsec ( ", " cbrt ( ",
                " tan ( ", " cot ( ", " arctan ( ", " arccot ( ", " ^ 3 ",
        };

        for (String op : ops) {
            CustomButton temp = new CustomButton(removeTokenizers(op));

            refs.put("Button_" + op, temp);

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
            } catch (Exception e) {
                currExp = "";
                ((JTextArea) refs.get("DisplayText")).setText("Error");
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

            JTextPane historyItem = new JTextPane();

            // Center the text
            StyledDocument doc = historyItem.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);

            // Add to history panel
            // Manage layout settings to justify items to the top of the panel
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = history.size() - 1;
            c.ipady = 10;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.SOUTH;

            historyItem.setText(historyAddition.get(0) + " = " + historyAddition.get(1));
            historyItem.setEditable(false);
            historyItem.setBackground(new Color(45, 52, 64));
            historyItem.setForeground(Color.WHITE);
            historyItem.setBorder(new RoundedBorder(10, new Color(245, 252, 255, 50)));
            historyItem.setFont(new Font(historyItem.getFont().getName(), Font.PLAIN, 20));
            historyItem.setPreferredSize(new Dimension(HISTORY_PANEL_WIDTH, 50));

            // On click, set the display to the expression
            historyItem.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    currExp = historyAddition.get(0);
                    ((JTextArea) refs.get("DisplayText")).setText(Translator.prettifyExpression(currExp));
                }
            });

            // Add to history panel
            refs.get("HistoryPanel").add(historyItem, c);
            refs.get("HistoryPanel").revalidate();
            refs.get("HistoryPanel").repaint();

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
                } else {
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
            String firstSpaceRemoved = currExp.charAt(0) == ' ' ?
                    currExp.substring(1) : currExp;

            return Translator.calculate(firstSpaceRemoved).trim();
        }
    }
