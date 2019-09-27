package labwork3;
/**
 * Class Main - starting point for project.
 * Represent usage of implemented
 * classes and methods.
 *
 * @author Alexander Onbysh
 * @version 0.1
 * @since 25.10.17
 */

class main {
    public static void main(String[] args) {
        Component group1 = new PrimitiveGroup();
        group1.addChild(new GraphicalPrimitive(0, 0, 1, 1));

        Component group2 = new PrimitiveGroup();
        group2.addChild(new GraphicalPrimitive(8, 8, 1, 1));
        Component group3 = new PrimitiveGroup();
        group3.addChild(new GraphicalPrimitive(7, 7, 10, 10));
        group3.addChild(new GraphicalPrimitive(1, 2, 1, 11));
        group3.addChild(new GraphicalPrimitive(1, 10, 8, 11));

        group2.addChild(group3);
        group1.addChild(group2);

        System.out.println("X: " + group1.getX());
        System.out.println("Y: " + group1.getY());
        System.out.println("Width: " + group1.getWidth());
        System.out.println("Height: " + group1.getHeight());

        Component t = group1.clone(1);
        System.out.println("X: " + t.getX());
        System.out.println("Y: " + t.getY());
        System.out.println("Width: " + t.getWidth());
        System.out.println("Height: " + t.getHeight());

    }
}