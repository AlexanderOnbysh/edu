import java.util.Random;

class Matrix {
    private int rows, columns;
    private double[][] matrix;

    Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new double[rows][columns];
    }

    void fill() {
        final Random random = new Random();
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                this.matrix[i][j] = random.nextDouble();
            }
        }
    }

    void print() {
        for (double[] aMatrix : this.matrix) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                System.out.format("%5f ", aMatrix[j]);
            }
            System.out.println();
        }
    }

    int getRows() {
        return this.rows;
    }

    int getColumns() {
        return this.columns;
    }

    double[][] getMatrix() {
        return matrix;
    }
}
