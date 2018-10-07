package labwork4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexon
 */
public class GlyphString extends JFrame {
    private List<Glyph> trees = new ArrayList<>();

    public void addGlyph(int x, int y, char name, Color color) {
        GlyphType type = GlyphFactory.getGlyphType(name, color);
        Glyph tree = new Glyph(x, y, type);
        trees.add(tree);
    }

    @Override
    public void paint(Graphics graphics) {
        for (Glyph tree : trees) {
            tree.draw(graphics);
        }
    }
}
