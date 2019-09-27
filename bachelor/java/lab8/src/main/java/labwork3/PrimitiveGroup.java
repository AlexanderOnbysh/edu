package labwork3;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.ArrayList;

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
        this.group.remove(component);
    }

    @Override
    public int getX() {
        int minX = Integer.MAX_VALUE;
        for (Component component : this.group) {
            minX = Math.min(minX, component.getX());
        }
        return minX;
    }

    @Override
    public int getY() {
        int minY = Integer.MAX_VALUE;
        for (Component component : this.group) {
            minY = Math.min(minY, component.getY());
        }
        return minY;
    }

    @Override
    public int getHeight() {
        int maxHeight = 0;
        for (Component component : this.group) {
            maxHeight = Math.max(maxHeight, component.getHeight() + component.getY());
        }
        return maxHeight - this.getY();
    }

    @Override
    public int getWidth() {
        int maxWidth = 0;
        for (Component component : this.group) {
            maxWidth = Math.max(maxWidth, component.getWidth() + component.getX());
        }
        return maxWidth - this.getX();
    }

    @Override
    public Component clone(int n) {
        if (n < 0) {
            throw new ValueException("Depth should be no less that 0");
        }
        if (n == 0) {
            return new PrimitiveGroup();
        }
        String result = "";
        for (int i = 0; i < n; i += 1) {
            result += "*";
        }
        System.out.println(result + "Copy group");
        PrimitiveGroup new_group = new PrimitiveGroup();
        for (Component value : this.group) {
            new_group.addChild(value.clone(n - 1));
        }
        return new_group;
    }
}
