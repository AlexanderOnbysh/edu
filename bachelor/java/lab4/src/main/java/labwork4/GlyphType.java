package labwork4;

import java.awt.*;
/**
 * Created by alexon
 */
public class GlyphType {
    private char glyphName;
    private Color color;

    public GlyphType(char glyphName, Color color) {
        this.glyphName = glyphName;
        this.color = color;
    }

    public void draw(Graphics g, int x, int y) {
        g.setColor(color);
        g.drawString(String.valueOf(glyphName), x, y);
    }
}
