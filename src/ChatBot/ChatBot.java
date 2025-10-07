package ChatBot;
import java.util.Scanner;

public class ChatBot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String botName = "Siren";
        int birthYear = 2025;

        System.out.println("Hello! My name is " + botName + ".");
        System.out.println("I was created in " + birthYear + ".");
        System.out.println("Please, remind me your name.");

        String userName = scanner.nextLine();

        System.out.println("What a great name you have, " + userName + "!");

        System.out.println("Let me guess your age.");
        System.out.println("Enter remainders of dividing your age by 3, 5 and 7.");

        int remainder3 = scanner.nextInt();
        int remainder5 = scanner.nextInt();
        int remainder7 = scanner.nextInt();

        int age = (remainder3 * 70 + remainder5 * 21 + remainder7 * 15) % 105;

        System.out.println("Your age is " + age + "; that's a good time to start programming!");

        System.out.println("Now I will prove to you that I can count to any number you want!");
        int userInp = scanner.nextInt();

        for (int i = 0; i <= userInp; i++) {
            System.out.println(i + "!");
        }

        System.out.println("Let's test your programming knowledge.");
        System.out.println("Why do we use the 'main' method in a Java program?");
        System.out.println("1. To define the entry point of the application.");
        System.out.println("2. To declare variables.");
        System.out.println("3. To create objects.");
        System.out.println("4. To import necessary libraries.");

        int correctAnswer = 1;
        int userAnswer;

        do {
            if (scanner.hasNextInt()) { // Перевірка, чи користувач ввів число
                userAnswer = scanner.nextInt();
                if (userAnswer == correctAnswer) {
                    System.out.println("Congratulations, that's correct!");
                } else {
                    System.out.println("Incorrect answer, please try again.");
                }
            } else {
                // Обробка неправильного типу вводу, щоб уникнути нескінченного циклу
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Споживаємо неправильний ввід
                userAnswer = -1; // Примушуємо цикл продовжитися
            }
        } while (userAnswer != correctAnswer);

        System.out.println("Goodbye, have a nice day!");

        scanner.close(); // Закриваємо Scanner в кінці роботи
    }
}
