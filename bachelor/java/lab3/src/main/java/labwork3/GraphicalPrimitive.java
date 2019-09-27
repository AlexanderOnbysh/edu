package labwork3;

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
        System.out.println("GraphicalPrimitive: X = " + this.x);
        return this.x;
    }

    @Override
    public int getY() {
        System.out.println("GraphicalPrimitive: Y = " + this.y);
        return this.y;
    }

    @Override
    public int getHeight() {
        System.out.println("GraphicalPrimitive: H = " + this.h);
        return this.h;
    }

    @Override
    public int getWidth() {
        System.out.println("GraphicalPrimitive: W = " + this.w);
        return this.w;
    }
}
