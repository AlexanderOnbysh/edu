package labwork4;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class GlyphFactory {
    static Map<Character, GlyphType> glyphTypes = new HashMap<>();

    public static GlyphType getGlyphType(char glyphName, Color color) {
        GlyphType result = glyphTypes.get(glyphName);
        if (result == null) {
            result = new GlyphType(glyphName, color);
            glyphTypes.put(glyphName, result);
        }
        return result;
    }
}
