package labwork3;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

/**
 * Class labwork3.GraphicalPrimitive.
 * Class that realize primitive of
 * graphical object
 *
 * @author Alexander Onbysh
 * @version 0.1
 * @since 25.10.17
 */
public class GraphicalPrimitive implements Component {

    private int x;
    private int y;
    private int h;
    private int w;

    GraphicalPrimitive(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void addChild(Component component) {
        System.out.println("You can not add GraphicalPrimitive to primitive");
    }

    public void removeChild(Component component) {
        System.out.println("You can not remove GraphicalPrimitive from primitive");
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getHeight() {
        return this.h;
    }

    @Override
    public int getWidth() {
        return this.w;
    }

    @Override
    public Component clone(int n) {
        if (n < 0){
            throw new ValueException("Depth should be no less that 0");
        }
        String result = "";
        for (int i = 0; i < n; i += 1) {
            result += "*";
        }
        System.out.println(result + "Clone " + this.x + " " + this.y + " " + this.w + " " + this.h);
        return new GraphicalPrimitive(this.x, this.y, this.w, this.h);
    }
}
