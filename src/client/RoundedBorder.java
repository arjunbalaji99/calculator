package client;

import javax.swing.border.Border;
import java.awt.*;

/**
 * Rounded border class for buttons and history text panels
 */
public class RoundedBorder implements Border {

    private int radius;
    private Color color;


    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }


    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+5, this.radius+5, this.radius+6, this.radius+4);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(this.color);
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}