import client.RoundedBorder;

import javax.swing.*;
import java.awt.*;

public class TestButtons {
    // Main driver method
    public static void main(String[] args)
    {
        JFrame frame = new JFrame(); // creating instance of JFrame

        JButton button = new JButton("TEST!!!!!!"); // creating instance of
        button.setBackground(new Color(34, 37, 44));
        button.setBounds(150, 200, 220,50); // x axis, y axis, width, height
        button.setContentAreaFilled(false);
        button.setBorder(new RoundedBorder(20, Color.BLACK));

        frame.add(button); // adding button in JFrame
        frame.setSize(500, 600); // 400 width and 500 height
        frame.setLayout(null); // using no layout managers
        frame.setVisible(true); // making the frame visible
    }
}
