package MatrixProcessing;

import java.util.Scanner;

/**
 * Головний клас для обробки матричних операцій.
 * Містить меню та реалізацію всіх етапів практичної роботи.
 */
public class MatrixProcessing {

    private static final Scanner scanner = new Scanner(System.in);
    private static final double EPSILON = 1e-9; // Допуск для порівняння чисел з плаваючою комою

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                processChoice(choice);
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Очистити невірний ввід
                choice = -1;
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("1. Add matrices");
        System.out.println("2. Multiply matrix by a constant");
        System.out.println("3. Multiply matrices");
        System.out.println("4. Transpose matrix");
        System.out.println("5. Calculate a determinant");
        System.out.println("6. Inverse matrix");
        System.out.println("0. Exit");
        System.out.print("Your choice: > ");
    }

    private static void processChoice(int choice) {
        System.out.println("--------------------");
        try {
            switch (choice) {
                case 1:
                    handleMatrixAddition();
                    break;
                case 2:
                    handleScalarMultiplication();
                    break;
                case 3:
                    handleMatrixMultiplication();
                    break;
                case 4:
                    handleMatrixTransposition();
                    break;
                case 5:
                    handleDeterminantCalculation();
                    break;
                case 6:
                    handleInverseMatrix();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Unknown option. Try again.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during operation: " + e.getMessage());
        }
        System.out.println("--------------------");
    }

    // Додавання матриць
    private static void handleMatrixAddition() {
        System.out.print("Enter size of first matrix: ");
        int r1 = scanner.nextInt();
        int c1 = scanner.nextInt();
        Matrix a = readMatrixData(r1, c1);

        System.out.print("Enter size of second matrix: ");
        int r2 = scanner.nextInt();
        int c2 = scanner.nextInt();
        Matrix b = readMatrixData(r2, c2);

        if (a.getRows() != b.getRows() || a.getCols() != b.getCols()) {
            System.out.println("The operation cannot be performed.");
            return;
        }

        Matrix result = new Matrix(r1, c1);
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c1; j++) {
                result.set(i, j, a.get(i, j) + b.get(i, j));
            }
        }
        System.out.println("The result is:");
        result.print();
    }

    // Множення матриці на константу
    private static void handleScalarMultiplication() {
        Matrix a = Matrix.readMatrix(scanner);
        if (a == null) return;

        System.out.print("Enter constant: ");
        double constant = scanner.nextDouble();

        Matrix result = new Matrix(a.getRows(), a.getCols());
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getCols(); j++) {
                result.set(i, j, a.get(i, j) * constant);
            }
        }

        System.out.println("The result is:");
        result.print();
    }

    // Множення матриць
    private static void handleMatrixMultiplication() {
        System.out.print("Enter size of first matrix: ");
        int r1 = scanner.nextInt();
        int c1 = scanner.nextInt();
        Matrix a = readMatrixData(r1, c1);

        System.out.print("Enter size of second matrix: ");
        int r2 = scanner.nextInt();
        int c2 = scanner.nextInt();
        Matrix b = readMatrixData(r2, c2);

        if (a.getCols() != b.getRows()) {
            System.out.println("The operation cannot be performed.");
            return;
        }

        Matrix result = new Matrix(a.getRows(), b.getCols());
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < b.getCols(); j++) {
                double sum = 0;
                for (int k = 0; k < a.getCols(); k++) {
                    sum += a.get(i, k) * b.get(k, j);
                }
                result.set(i, j, sum);
            }
        }
        System.out.println("The result is:");
        result.print();
    }

    // Допоміжний метод для зчитування даних матриці після введення розмірів
    private static Matrix readMatrixData(int rows, int cols) {
        Matrix matrix = new Matrix(rows, cols);
        System.out.println("Enter matrix:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.set(i, j, scanner.nextDouble());
            }
        }
        return matrix;
    }

    // Транспонування матриці
    private static void handleMatrixTransposition() {
        System.out.println("1. Main diagonal");
        System.out.println("2. Side diagonal");
        System.out.println("3. Vertical line");
        System.out.println("4. Horizontal line");
        System.out.print("Your choice: > ");
        int type = scanner.nextInt();

        Matrix a = Matrix.readMatrix(scanner);

        Matrix result = switch (type) {
            case 1 -> transposeMainDiagonal(a);
            case 2 -> transposeSideDiagonal(a);
            case 3 -> transposeVertical(a);
            case 4 -> transposeHorizontal(a);
            default -> {
                System.out.println("Invalid transposition type.");
                yield null;
            }
        };

        if (result != null) {
            System.out.println("The result is:");
            result.print();
        }
    }

    // Транспонування відносно головної діагоналі
    private static Matrix transposeMainDiagonal(Matrix a) {
        Matrix result = new Matrix(a.getCols(), a.getRows());
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getCols(); j++) {
                result.set(j, i, a.get(i, j));
            }
        }
        return result;
    }

    // Транспонування відносно побічної діагоналі
    private static Matrix transposeSideDiagonal(Matrix a) {
        int r = a.getRows();
        int c = a.getCols();
        Matrix result = new Matrix(c, r);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                result.set(c - 1 - j, r - 1 - i, a.get(i, j));
            }
        }
        return result;
    }

    // Транспонування відносно вертикалі (горизонтальне відображення)
    private static Matrix transposeVertical(Matrix a) {
        int r = a.getRows();
        int c = a.getCols();
        Matrix result = new Matrix(r, c);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                result.set(i, c - 1 - j, a.get(i, j));
            }
        }
        return result;
    }

    // Транспонування відносно горизонталі (вертикальне відображення)
    private static Matrix transposeHorizontal(Matrix a) {
        int r = a.getRows();
        int c = a.getCols();
        Matrix result = new Matrix(r, c);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                result.set(r - 1 - i, j, a.get(i, j));
            }
        }
        return result;
    }

    // Обчислення визначника
    private static void handleDeterminantCalculation() {
        Matrix a = Matrix.readMatrix(scanner);
        if (a == null) return;

        if (a.getRows() != a.getCols()) {
            System.out.println("The operation cannot be performed. Matrix must be square.");
            return;
        }

        double det = determinant(a.getDataArray());
        System.out.println("The result is:");
        if (Math.abs(det - Math.round(det)) < EPSILON) {
            System.out.printf("%d\n", (int) Math.round(det));
        } else {
            System.out.printf("%.2f\n", det);
        }
    }

    /**
     * Рекурсивний метод для обчислення визначника.
     * @param matrix Двовимірний масив елементів матриці.
     * @return Значення визначника.
     */
    public static double determinant(double[][] matrix) {
        int size = matrix.length;
        if (size == 1) {
            return matrix[0][0];
        }
        if (size == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        double det = 0;
        // Розклад по першому рядку
        for (int j = 0; j < size; j++) {
            double sign = (j % 2 == 0) ? 1.0 : -1.0;
            double cofactor = matrix[0][j];
            double[][] submatrix = getSubmatrix(matrix, 0, j);
            det += sign * cofactor * determinant(submatrix);
        }
        return det;
    }

    // Створює субматрицю, видаляючи рядок `p` та стовпець `q`
    private static double[][] getSubmatrix(double[][] matrix, int p, int q) {
        int size = matrix.length;
        double[][] submatrix = new double[size - 1][size - 1];
        int i = 0, j = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row != p && col != q) {
                    submatrix[i][j++] = matrix[row][col];
                    if (j == size - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
        return submatrix;
    }

    // Зворотна матриця
    private static void handleInverseMatrix() {
        Matrix a = Matrix.readMatrix(scanner);
        if (a == null) return;

        if (a.getRows() != a.getCols()) {
            System.out.println("This matrix doesn't have an inverse. Matrix must be square.");
            return;
        }

        int r = a.getRows();

        double det = determinant(a.getDataArray());

        if (Math.abs(det) < EPSILON) {
            System.out.println("This matrix doesn't have an inverse.");
            return;
        }

        // 1. Знайти матрицю кофакторів
        Matrix cofactorMatrix = new Matrix(r, r);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < r; j++) {
                // ВИПРАВЛЕНО: Використовуємо getDataArray() для створення субматриці
                double[][] sub = getSubmatrix(a.getDataArray(), i, j);
                double minor = determinant(sub);

                double sign = ((i + j) % 2 == 0) ? 1.0 : -1.0;
                cofactorMatrix.set(i, j, sign * minor);
            }
        }

        // 2. Знайти приєднану (adjugate) матрицю (транспонована матриця кофакторів)
        Matrix adjugateMatrix = transposeMainDiagonal(cofactorMatrix);

        // 3. Зворотна матриця A^(-1) = (1/det) * Adjugate
        Matrix inverseMatrix = new Matrix(r, r);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < r; j++) {
                inverseMatrix.set(i, j, adjugateMatrix.get(i, j) / det);
            }
        }

        System.out.println("The result is:");
        inverseMatrix.print();
    }
}