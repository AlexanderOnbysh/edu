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

        Component group2 = new PrimitiveGroup();

        group1.addChild(group2);

        group1.addChild(new GraphicalPrimitive(7, 7, 10, 10));
        group1.addChild(new GraphicalPrimitive(5, 5, 10, 10));


        Component graphicalPrimitive1 = new GraphicalPrimitive(15, 40, 100, 20);

        graphicalPrimitive1.addChild(new GraphicalPrimitive(1, 1, 1, 1));
        group1.addChild(graphicalPrimitive1);
        group2.addChild(graphicalPrimitive1);

        group1.removeChild(graphicalPrimitive1);

        System.out.println("X: " + group1.getX());
        System.out.println("Y: " + group1.getY());
        System.out.println("Width: " + group1.getWidth());
        System.out.println("Height: " + group1.getHeight());
    }
}