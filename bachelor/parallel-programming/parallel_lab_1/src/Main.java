public class Main {

    private static int SIZE = 5;
    private static int THREADS = SIZE;

    public static void main(String... args) throws InterruptedException {
        System.out.println("Matrix");
        Matrix matrix = new Matrix(SIZE, SIZE);
        matrix.fill();
        matrix.print();

        CustomThread[] threadArray = new CustomThread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threadArray[i] = new CustomThread(matrix, i);
            threadArray[i].start();
        }

        for (int i = 0; i < THREADS; i++) {
            threadArray[i].join();
        }

        for (int i = 0; i < THREADS; i++) {
            matrix.getMatrix()[i][i] = threadArray[i].getResult();
        }

        System.out.println("Result");
        matrix.print();
    }
}
