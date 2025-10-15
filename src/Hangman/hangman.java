package Hangman;

import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class hangman {

    // Створюємо один Scanner для всієї програми
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> WORDS = Arrays.asList("python", "java", "javascript", "kotlin");
    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("HANGMAN");

        // Цикл для головного меню
        while (true) {
            System.out.print("\nType \"play\" to play the game, \"exit\" to quit: ");
            String command = scanner.nextLine();
            if (command.equals("play")) {
                playGame();
            } else if (command.equals("exit")) {
                break; // Вихід з циклу і завершення програми
            }
        }
    }

    // Метод з основною логікою гри
    public static void playGame() {
        String secretWord = WORDS.get(random.nextInt(WORDS.size()));
        StringBuilder hiddenWord = new StringBuilder("-".repeat(secretWord.length()));
        int lives = 8;
        List<Character> guessedLetters = new ArrayList<>();

        while (lives > 0 && hiddenWord.indexOf("-") != -1) {
            System.out.println("\n" + hiddenWord);
            System.out.print("Input a letter: > ");
            String input = scanner.nextLine();

            if (input.length() != 1) {
                System.out.println("You should input a single letter.");
                continue;
            }

            char letter = input.charAt(0);

            if (!Character.isLowerCase(letter)) {
                System.out.println("Please enter a lowercase English letter.");
                continue;
            }

            if (guessedLetters.contains(letter)) {
                System.out.println("You've already guessed this letter.");
                continue;
            }

            guessedLetters.add(letter);

            if (secretWord.indexOf(letter) >= 0) {
                for (int i = 0; i < secretWord.length(); i++) {
                    if (secretWord.charAt(i) == letter) {
                        hiddenWord.setCharAt(i, letter);
                    }
                }
            } else {
                System.out.println("That letter doesn't appear in the word");
                lives--;
            }
        }

        System.out.println();
        if (hiddenWord.indexOf("-") == -1) {
            System.out.println("You guessed the word " + secretWord + "!");
            System.out.println("You survived!");
        } else {
            System.out.println("You lost!");
        }
    }
}