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
        setBorder(new RoundedBorder(5));

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clicked = !clicked;
                repaint();
            }
        });
    }

    public CustomButton(String text, Method onClick) {
        super(text);
        setForeground(Color.WHITE);
        setBorder(new RoundedBorder(5));
    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
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
        g.setColor(new Color(0, 0, 0, 0));
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}