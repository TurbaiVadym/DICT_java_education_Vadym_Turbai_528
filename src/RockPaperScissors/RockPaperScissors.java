package RockPaperScissors;

import java.util.Scanner;

public class RockPaperScissors {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameLogic gameLogic = new GameLogic();

        // 1. Введення імені
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Hello, " + name);

        // 2. Отримання рейтингу
        int score = gameLogic.getInitialRating(name);

        // 3. Налаштування опцій
        String inputOptions = scanner.nextLine();
        gameLogic.setOptions(inputOptions);

        System.out.println("Okay, let's start");

        // 4. Ігровий цикл
        while (true) {
            String userInput = scanner.nextLine().trim();

            if (userInput.equals("!exit")) {
                System.out.println("Bye!");
                break;
            }

            if (userInput.equals("!rating")) {
                System.out.println("Your rating: " + score);
                continue;
            }

            // Перевірка на валідність ходу
            if (!gameLogic.getOptions().contains(userInput)) {
                System.out.println("Invalid input");
                continue;
            }

            // 5. Процес гри
            String computerOption = gameLogic.getRandomOption();
            int result = gameLogic.determineResult(userInput, computerOption);

            if (result == 0) {
                // Нічия
                System.out.printf("There is a draw (%s)%n", computerOption);
                score += 50;
            } else if (result == 1) {
                // Перемога
                System.out.printf("Well done. The computer chose %s and failed%n", computerOption);
                score += 100;
            } else {
                // Поразка
                System.out.printf("Sorry, but the computer chose %s%n", computerOption);
            }
        }
    }
}
