package labwork2;

/**
 * Class, called labwork2.Cl3, implements the interface called labwork2.If3
 * and extends class called labwork2.Cl3
 * and overrides each method of it, that print out current class name,
 * and current method name, that was called.
 *
 * @author Alexander Onbysh
 * @version 0.1
 * @since 2017.10.25
 */
public class Cl3 extends Cl1 implements If3 {
    If2 values[];

    public void meth3() {
        System.out.println("Class: labwork2.Cl3\tMethod: meth3");
    }
}
