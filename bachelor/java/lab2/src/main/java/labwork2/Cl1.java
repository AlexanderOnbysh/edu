package labwork2;

/**
 * Class, called labwork2.Cl1, implements the interface called labwork2.If1
 * and overrides each method of it, that print out current class name,
 * and current method name, that was called.
 *
 * @author Alexander Onbysh
 * @version 0.1
 * @since 2017.10.25
 */

public class Cl1 implements If1 {
    Cl3 values[];

    public void meth1() {
        System.out.println("Class: labwork2.Cl1\tMethod: meth1");
    }

    public void meth2() {
        System.out.println("Class: labwork2.Cl1\tMethod: meth2");
    }
}
