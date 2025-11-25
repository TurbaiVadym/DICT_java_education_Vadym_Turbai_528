package TicTacToe;

/**
 * Клас, що представляє ігрове поле та логіку гри (перемога, нічия, незавершено).
 */
public class Board {
    private char[] cells; // Масив з 9 символів, що представляють поле
    public static final String GAME_NOT_FINISHED = "Game not finished";
    public static final String DRAW = "Draw";
    public static final String X_WINS = "X wins";
    public static final String O_WINS = "O wins";

    // Конструктор: створює порожнє поле
    public Board() {
        this.cells = new char[9];
        // Заповнюємо поле символами '_'
        for (int i = 0; i < 9; i++) {
            this.cells[i] = '_';
        }
    }

    // Метод для відображення ігрової сітки
    public void printGrid() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                char cellChar = this.cells[index];
                // Виводимо пробіли якщо клітинка порожня
                if (cellChar == '_') {
                    System.out.print("  ");
                } else {
                    System.out.print(cellChar + " ");
                }
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    /**
     * Перевіряє, чи виграв вказаний гравець.
     * @param player Символ гравця ('X' або 'O').
     * @return true, якщо гравець виграв.
     */
    private boolean checkWin(char player) {
        // Перевірка рядків, стовпців та діагоналей

        // Перевірка рядків (0,1,2), (3,4,5), (6,7,8)
        for (int i = 0; i < 9; i += 3) {
            if (cells[i] == player && cells[i + 1] == player && cells[i + 2] == player) {
                return true;
            }
        }
        // Перевірка стовпців (0,3,6), (1,4,7), (2,5,8)
        for (int i = 0; i < 3; i++) {
            if (cells[i] == player && cells[i + 3] == player && cells[i + 6] == player) {
                return true;
            }
        }
        // Перевірка діагоналей (0,4,8) та (2,4,6)
        if (cells[0] == player && cells[4] == player && cells[8] == player) {
            return true;
        }
        if (cells[2] == player && cells[4] == player && cells[6] == player) {
            return true;
        }
        return false;
    }

    /**
     * Аналізує поточний стан гри.
     * @return Рядок, що описує стан (X_WINS, DRAW, GAME_NOT_FINISHED і т.д.).
     */
    public String analyzeGameState() {
        boolean xWins = checkWin('X');
        boolean oWins = checkWin('O');

        if (xWins) {
            return X_WINS;
        }
        if (oWins) {
            return O_WINS;
        }

        // Перевірка на наявність порожніх клітинок
        for (char cell : cells) {
            if (cell == '_') {
                return GAME_NOT_FINISHED;
            }
        }

        return DRAW;
    }

    /**
     * Здійснює хід гравця, якщо клітинка вільна.
     * @param row Рядок (1-3).
     * @param col Стовпець (1-3).
     * @param player Символ гравця ('X' або 'O').
     * @return true, якщо хід був успішним.
     */
    public boolean makeMove(int row, int col, char player) {
        // Перетворення координат (1-3) на індекс масиву (0-8)
        int index = (row - 1) * 3 + (col - 1);

        if (this.cells[index] == '_') {
            this.cells[index] = player;
            return true;
        }
        return false;
    }
}