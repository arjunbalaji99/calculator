package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomButton extends JButton {

    // Stores current click state, for styling
    public static final int BUTTON_WIDTH = 90;
    public static final int BUTTON_HEIGHT = 60;
    private boolean clicked;

    public CustomButton(String text) {
        super(text);

        setBackground(new Color(52, 59, 70));
        setForeground(Color.WHITE);
        setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        setBorder(new RoundedBorder(15, new Color(220, 224, 230)));


        // lower padding
        setMargin(new Insets(0, 0, 0, 0));

        // text size set
        setFont(new Font("Arial", Font.BOLD, 15));

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                repaint();
            }
        });
    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
    }
}