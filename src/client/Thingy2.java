package client;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Thingy2 {
    public JPanel testdfsd;
    public JPanel panel1;
    private JButton absButton;
    private JButton button3;
    private JButton modButton;
    private JButton cotButton;
    private JButton cscButton;
    private JButton secButton;
    private JButton a1XButton;
    private JButton a2Button2;
    private JButton tanButton;
    private JButton cosButton;
    private JButton sinButton;
    private JButton sqrtButton;
    private JButton log10Button;
    private JButton arcsinButton;
    private JButton arccosButton;
    private JButton arctanButton;
    private JButton xrootButton;
    private JButton lnButton;
    private JButton arcsecButton;
    private JButton arccscButton;
    private JButton arccotButton;
    private JButton cbrtButton;
    private JButton piButton;
    private JButton logxButton;
    private JButton cButton;
    private JButton button27;
    private JButton button28;
    private JButton button29;
    private JButton button30;
    private JButton a9Button;
    private JButton a8Button;
    private JButton a7Button;
    private JButton a4Button;
    private JButton a5Button;
    private JButton a6Button;
    private JButton button37;
    private JButton a1Button;
    private JButton a0Button;
    private JButton button40;
    private JButton a2Button;
    private JButton a3Button;
    private JButton button43;
    private JButton cButton1;
    private JButton button45;
    private JTextPane a26TextPane;
    private JPanel hist;
    private JPanel asdassdas;

    private void createUIComponents() {
        a0Button.setBorder(new RoundedBorder(5));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Thingy2");
        frame.setContentPane(
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

class RoundedBorder implements Border {

    private final int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}