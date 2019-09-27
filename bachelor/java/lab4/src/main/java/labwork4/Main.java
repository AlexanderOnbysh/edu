package labwork4;
/**
 * Class Main - starting point for project.
 * Represent usage of implemented
 * classes and methods.
 *
 * @author Alexander Onbysh
 * @version 0.1
 * @since 25.10.17
 */

import java.awt.*;

public class Main {
    static int CANVAS_SIZE = 500;
    static int TREES_TO_DRAW = 1000;
    static int TREE_TYPES = 2;

    public static void main(String[] args) {
        GlyphString glyphString = new GlyphString();
        GlyphString name = new GlyphString();
        for (int i = 0; i < Math.floor(TREES_TO_DRAW / TREE_TYPES); i++) {
            glyphString.addGlyph(random(0, CANVAS_SIZE), random(100, CANVAS_SIZE),(char) ('a' + Math.random() * ('z'-'a' + 1)), Color.RED);
            glyphString.addGlyph(random(0, CANVAS_SIZE), random(100, CANVAS_SIZE), (char) ('a' + Math.random() * ('z'-'a' + 1)), Color.BLUE);
        }

        glyphString.addGlyph(200, 50, 'A', Color.BLACK);
        glyphString.addGlyph(210, 50, 'l', Color.BLACK);
        glyphString.addGlyph(220, 50, 'e', Color.BLACK);
        glyphString.addGlyph(230, 50, 'x', Color.BLACK);
        glyphString.addGlyph(240, 50, 'a', Color.BLACK);
        glyphString.addGlyph(250, 50, 'n', Color.BLACK);
        glyphString.addGlyph(260, 50, 'd', Color.BLACK);
        glyphString.addGlyph(270, 50, 'e', Color.BLACK);
        glyphString.addGlyph(280, 50, 'r', Color.BLACK);

        glyphString.setSize(CANVAS_SIZE, CANVAS_SIZE);
        glyphString.setVisible(true);

        int t = GlyphFactory.glyphTypes.size();
        System.out.println(t);
    }

    private static int random(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}