package labwork4;

import java.awt.*;
/**
 * Created by alexon
 */
public class Glyph {
    private int x;
    private int y;
    private GlyphType type;

    public Glyph(int x, int y, GlyphType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw(Graphics g) {
        type.draw(g, x, y);
    }
}
