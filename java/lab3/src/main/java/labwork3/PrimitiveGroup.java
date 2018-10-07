package labwork3;
import java.util.ArrayList;
import java.lang.Math;

/**
 * Class Composite represents structure
 * of composition of multiple graphic
 * primitives.
 * It implements Component interface.
 *
 * @author Alexander Onbysh
 * @version 0.1
 * @since 25.10.17
 */

public class PrimitiveGroup implements Component {
    private ArrayList<Component> group = new ArrayList<>();

    @Override
    public void addChild(Component component) {
        System.out.println("PrimitiveGroup: add child");
        this.group.add(component);
    }

    @Override
    public void removeChild(Component component) {
        System.out.println("PrimitiveGroup: remove child");
        this.group.remove(component);
    }

    @Override
    public int getX() {
        System.out.println("PrimitiveGroup: calculate X");
        int minX = Integer.MAX_VALUE;
        for (Component component: this.group){
            minX = Math.min(minX, component.getX());
        }
        return minX;
    }

    @Override
    public int getY() {
        System.out.println("PrimitiveGroup: calculate Y");
        int minY = Integer.MAX_VALUE;
        for (Component component: this.group){
            minY = Math.min(minY, component.getY());
        }
        return minY;
    }

    @Override
    public int getHeight() {
        System.out.println("PrimitiveGroup: calculate height");
        int maxHeight = 0;
        for (Component component: this.group){
            maxHeight = Math.max(maxHeight, component.getHeight() + component.getY());
        }
        return maxHeight - this.getY();
    }

    @Override
    public int getWidth() {
        System.out.println("PrimitiveGroup: calculate width");
        int maxWidth = 0;
        for (Component component: this.group){
            maxWidth = Math.max(maxWidth, component.getWidth() + component.getX());
        }
        return maxWidth - this.getX();
    }
}
