public class CustomThread extends Thread {
    private Matrix matrix;

    private int index;

    private double result = 0;

    CustomThread(Matrix matrix, int index) {
        this.matrix = matrix;
        this.index = index;
    }

    double getResult() {
        return result;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.matrix.getMatrix()[0].length; i++) {
            this.result += this.matrix.getMatrix()[i][this.index] + this.matrix.getMatrix()[this.index][i];
        }
        this.result -= this.matrix.getMatrix()[this.index][this.index];
    }
}
