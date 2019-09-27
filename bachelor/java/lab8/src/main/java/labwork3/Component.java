package labwork3;

public interface Component extends Prototype {

    void addChild(Component component);
    void removeChild(Component component);
    int getX();
    int getY();
    int getHeight();
    int getWidth();
}
