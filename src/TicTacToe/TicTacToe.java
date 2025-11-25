package TicTacToe;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Головний клас для запуску гри "Хрестики-нулики".
 */
public class TicTacToe {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Створюємо нове ігрове поле
        Board board = new Board();

        char currentPlayer = 'X'; // X завжди починає

        board.printGrid(); // Виводимо порожнє поле на початку

        // Ігровий цикл
        while (board.analyzeGameState().equals(Board.GAME_NOT_FINISHED)) {
            System.out.print("Enter the coordinates: ");

            int row = -1;
            int col = -1;
            boolean inputValid = false;

            // Цикл для перевірки введення координат
            while (!inputValid) {
                try {
                    // Читаємо стовпець і рядок
                    // row=вертикаль col=горизонталь
                    if (!scanner.hasNextInt()) {
                        System.out.println("You should enter numbers!");
                        scanner.nextLine(); // Очищаємо буфер
                        System.out.print("Enter the coordinates: ");
                        continue;
                    }
                    col = scanner.nextInt();

                    if (!scanner.hasNextInt()) {
                        System.out.println("You should enter numbers!");
                        scanner.nextLine(); // Очищаємо буфер
                        System.out.print("Enter the coordinates: ");
                        continue;
                    }
                    row = scanner.nextInt();

                    // Обробка зайвого введення після двох чисел
                    if (scanner.hasNextLine()) {
                        scanner.nextLine(); // Споживаємо залишок рядка
                    }

                    // Перевірка діапазону
                    if (row < 1 || row > 3 || col < 1 || col > 3) {
                        System.out.println("Coordinates should be from 1 to 3!");
                        System.out.print("Enter the coordinates: ");
                        continue;
                    }

                    // Перевірка, чи вільна клітинка
                    if (board.makeMove(row, col, currentPlayer)) {
                        inputValid = true;
                    } else {
                        System.out.println("This cell is occupied! Choose another one!");
                        System.out.print("Enter the coordinates: ");
                    }

                } catch (InputMismatchException e) {
                    // Перехоплює, якщо scanner.nextInt() не отримує число
                    // Але вже перевірили це за допомогою hasNextInt()
                    // Додаткова обробка помилок
                    System.out.println("You should enter numbers!");
                    scanner.nextLine(); // Очищаємо буфер
                    System.out.print("Enter the coordinates: ");
                }
            }

            // Виводимо оновлене поле
            board.printGrid();

            // Перевіряємо, чи завершилася гра після ходу
            if (!board.analyzeGameState().equals(Board.GAME_NOT_FINISHED)) {
                break;
            }

            // Змінюємо гравця для наступного ходу
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }

        // Виводимо кінцевий результат
        System.out.println(board.analyzeGameState());
        scanner.close();
    }
}