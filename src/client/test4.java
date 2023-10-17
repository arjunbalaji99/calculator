package client;

import javax.swing.*;

public class test4 {
    private JButton button1;
    private JTextPane textPane1;
    private JPanel ug;

    public static void main(String[] args) {
        JFrame frame = new JFrame("test4");
        frame.setContentPane(new test4().ug);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
