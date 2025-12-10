package MatrixProcessing;

import java.util.Scanner;

/**
 * Клас для представлення матриці.
 * Відповідає за зберігання даних, розмірів та базовий ввід/вивід.
 */
public class Matrix {
    private final double[][] data;
    private final int rows;
    private final int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }

    // Геттери та Сеттери
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double get(int r, int c) {
        return data[r][c];
    }

    public void set(int r, int c, double val) {
        data[r][c] = val;
    }

    /**
     * Повертає копію внутрішнього масиву даних.
     * Використовується класом MatrixProcessing для математичних операцій
     * з урахуванням обмежень приватного доступу.
     */
    public double[][] getDataArray() {
        double[][] copy = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            // Копіюємо кожен рядок
            System.arraycopy(data[i], 0, copy[i], 0, cols);
        }
        return copy;
    }

    // Статичний метод для зчитування матриці з консолі
    public static Matrix readMatrix(Scanner scanner) {
        System.out.print("Enter size of matrix: ");
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();

        Matrix matrix = new Matrix(rows, cols);
        System.out.println("Enter matrix:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (scanner.hasNextDouble()) {
                    matrix.set(i, j, scanner.nextDouble());
                } else {
                    System.out.println("Error reading matrix element. Using 0.0.");
                    matrix.set(i, j, 0.0);
                    scanner.next(); // Очистити невірний ввід
                }
            }
        }
        return matrix;
    }

    // Метод для виведення матриці
    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double value = data[i][j];
                // Виводимо як ціле, якщо число близьке до цілого
                if (Math.abs(value - Math.round(value)) < 1e-9) {
                    System.out.printf("%d ", (int) Math.round(value));
                } else {
                    // Форматуємо до двох знаків після коми
                    System.out.printf("%.2f ", value);
                }
            }
            System.out.println();
        }
    }
}