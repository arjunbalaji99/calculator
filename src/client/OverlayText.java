package client;

import javax.swing.border.Border;
import java.awt.*;

public class OverlayText implements Border {
    private final Color color;

    public OverlayText(Color color) {
        this.color = color;
    }


    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(this.color);
        g.drawString("TEST", 20, 20);
    }
}