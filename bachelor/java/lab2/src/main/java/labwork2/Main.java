package labwork2;

/**
 * Class labwork2.Main.
 * Entry point of running code.
 * Create three test cases for each implemented class
 * and call some methods of its.
 *
 * @author Alexander Onbysh
 * @version 0.1
 * @since 2017.10.25
 */

public class Main {
    public static void main(String[] args) {
        If2 test1 = new Cl1();
        Cl2 test2 = new Cl2();
        Cl3 test3 = new Cl3();

        test1.meth1();
        test1.meth2();

        test2.meth1();
        test2.meth2();
        test2.meth3();

        test3.meth1();
        test3.meth2();
        test3.meth3();
    }
}
