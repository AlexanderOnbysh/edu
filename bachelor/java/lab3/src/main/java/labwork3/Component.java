package labwork3;

/**
 * Interface labwork3.Component.
 * It's common access to handle
 * multiple objects as one object.
 *
 * @author Alexander Onbysh
 * @version 0.1
 * @since 25.10.17
 */

public interface Component {

    /**
     * Add component
     */
    void addChild(Component component);

    /**
     * Remove component
     */
    void removeChild(Component component);

    /**
     * Calculate bottom left coordinate X
     * of the component
     * @return (int)
     */
    int getX();

    /**
     * Calculate bottom left coordinate Y
     * of the component
     * @return (int)
     */
    int getY();

    /**
     * Calculate height from Y coordinate to the top
     * @return (int)
     */
    int getHeight();

    /**
     * Calculate width from X coordinate to the right side
     * @return (int)
     */
    int getWidth();
}
