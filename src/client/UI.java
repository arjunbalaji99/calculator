package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import translator.Translator;

import javax.swing.*;

// video i am using - https://www.youtube.com/watch?v=5o3fMLPY7qY

public class UI {

    JFrame f;


    public UI() {

    }
    public static void main(String[] args) {

        JFrame f = new JFrame();//creating instance of JFrame

        JPanel p = new JPanel();
        p.setBackground(Color.RED);
        p.setBounds(0, 100, 300, 300);

        JLabel label1 = new JLabel("Test");
        label1.setText("Calculator");

        JButton submitExp = new JButton();
        submitExp.setText("Calculate");

        JTextArea input = new JTextArea();
        input.setColumns(20);
        input.setRows(5);

        JPanel answerPanel = new JPanel();
        answerPanel.setBackground(Color.WHITE);
        answerPanel.setBounds(0, 400, 300, 100);

        JLabel answerText = new JLabel();
        answerPanel.add(answerText);

        GridLayout g = new GridLayout();
        g.setRows(4);
        g.setColumns(4);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(g);
        buttonsPanel.setBackground(Color.GRAY);
        buttonsPanel.setBounds(400, 400, 400, 400);
        addNumberButtons(buttonsPanel, input);
        addOperationButtons(buttonsPanel, input);

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String inp = input.getText();
                try {

                    // if last char of input is a whitespace, remove it
                    inp = inp.trim();

                    double ans = Translator.Calculate(inp);
                    answerText.setText("Answer: " + ans);
                } catch (Exception e) {
                    answerText.setText("Error");
                }

            }
        };
        submitExp.addActionListener(actionListener);


        p.add(answerPanel);
        p.add(label1);
        p.add(input);
        p.add(buttonsPanel);

        p.add(submitExp);
        f.add(p);

        f.setSize(700,600);//400 width and 500 height
        f.setLocation(760, 320);
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
    }

    private static void addNumberButtons(JPanel container, JTextArea textarea) {
        for (int i = 0; i < 10; i++) {
            JButton temp = new JButton();
            temp.setText(i + "");

            int finalI = i;
            temp.addActionListener(
                actionEvent -> {
                    String current = textarea.getText();
                    String newText = current + finalI;
                    textarea.setText(newText);
                }
            );

            container.add(temp);
        }
    }

    private static void addOperationButtons(JPanel container, JTextArea textarea) {
        String[] ops = new String[] {"+", "-", "*", "/"};
        for (String op : ops) {
            JButton temp = new JButton();
            temp.setText(op);

            final String finalOp = op;
            temp.addActionListener(
                    actionEvent -> {
                        String current = textarea.getText();
                        String newText = current + finalOp + " ";
                        textarea.setText(newText);
                    }
            );

            container.add(temp);
        }
    }
}
