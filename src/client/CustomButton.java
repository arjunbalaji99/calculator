package client;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

public class CustomButton extends JButton {

    // Stores current click state, for styling
    private boolean clicked;

    public CustomButton(String text) {
        super(text);

        setBackground(new Color(52, 59, 70));
        setForeground(Color.WHITE);
        setBorder(new RoundedBorder(15, Color.BLACK));

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clicked = !clicked;
                repaint();
            }
        });
    }

    public CustomButton(String text, DisplayPrinter runner) {
        super(text);
        setForeground(Color.WHITE);
        setBorder(new RoundedBorder(15, Color.BLACK));

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clicked = !clicked;
                runner.execCommand();
                repaint();
            }
        });

    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
    }
}