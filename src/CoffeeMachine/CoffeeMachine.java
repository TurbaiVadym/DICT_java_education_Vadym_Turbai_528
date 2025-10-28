package CoffeeMachine;

import java.util.Scanner;

enum State {
    MAIN_MENU,       // Вибір дії (buy, fill, take, remaining, exit)
    BUY_COFFEE,      // Вибір типу кави (1, 2, 3, back)
    FILL_WATER,      // Додавання води
    FILL_MILK,       // Додавання молока
    FILL_BEANS,      // Додавання кавових зерен
    FILL_CUPS,       // Додавання стаканчиків
    EXIT             // Вихід з програми
}

enum CoffeeType {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6);

    final int water;
    final int milk;
    final int beans;
    final int cost;

    CoffeeType(int water, int milk, int beans, int cost) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cost = cost;
    }
}

class MachineLogic {
    // Ресурси машини
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;
    private State currentState;

    // Конструктор для ініціалізації початкових ресурсів
    public MachineLogic(int water, int milk, int beans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.money = money;
        this.currentState = State.MAIN_MENU; // Початковий стан
    }

    //Повертає поточний стан (для головного циклу в main).
    public State getCurrentState() {
        return this.currentState;
    }

    /**
     * Головний метод обробки введення
     * Він діє як "машина станів", реагуючи по-різному залежно від поточного стану.
     */
    public void processInput(String input) {
        switch (this.currentState) {
            case MAIN_MENU:
                handleMainMenu(input);
                break;
            case BUY_COFFEE:
                handleBuyCoffee(input);
                break;
            case FILL_WATER:
                handleFillWater(input);
                break;
            case FILL_MILK:
                handleFillMilk(input);
                break;
            case FILL_BEANS:
                handleFillBeans(input);
                break;
            case FILL_CUPS:
                handleFillCups(input);
                break;
            case EXIT:
                // Нічого не робимо, цикл в main завершиться
                break;
        }
    }

    //Друкує підказку для користувача залежно від поточного стану.
    public void printCurrentPrompt() {
        switch (this.currentState) {
            case MAIN_MENU:
                System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
                break;
            case BUY_COFFEE:
                System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                break;
            case FILL_WATER:
                System.out.println("\nWrite how many ml of water you want to add:");
                break;
            case FILL_MILK:
                System.out.println("Write how many ml of milk you want to add:");
                break;
            case FILL_BEANS:
                System.out.println("Write how many grams of coffee beans you want to add:");
                break;
            case FILL_CUPS:
                System.out.println("Write how many disposable coffee cups you want to add:");
                break;
            case EXIT:
                break;
        }
    }

    // --- Обробники станів ---
    //Обробляє введення, коли машина в стані MAIN_MENU
    private void handleMainMenu(String action) {
        switch (action) {
            case "buy":
                this.currentState = State.BUY_COFFEE; // Перехід до стану покупки
                break;
            case "fill":
                this.currentState = State.FILL_WATER; // Початок процесу поповнення
                break;
            case "take":
                actionTake(); // Виконуємо дію і залишаємось в MAIN_MENU
                break;
            case "remaining":
                actionRemaining(); // Виконуємо дію і залишаємось в MAIN_MENU
                break;
            case "exit":
                this.currentState = State.EXIT; // Перехід до стану виходу
                break;
            default:
                System.out.println("Unknown action. Please try again.");
                // Залишаємось в MAIN_MENU
                break;
        }
    }

    // Обробляє введення, коли машина в стані BUY_COFFEE
    private void handleBuyCoffee(String choice) {
        switch (choice) {
            case "1": // Espresso
                checkAndMakeCoffee(CoffeeType.ESPRESSO);
                this.currentState = State.MAIN_MENU; // Повернення в головне меню
                break;
            case "2": // Latte
                checkAndMakeCoffee(CoffeeType.LATTE);
                this.currentState = State.MAIN_MENU; // Повернення в головне меню
                break;
            case "3": // Cappuccino
                checkAndMakeCoffee(CoffeeType.CAPPUCCINO);
                this.currentState = State.MAIN_MENU; // Повернення в головне меню
                break;
            case "back":
                this.currentState = State.MAIN_MENU; // Повернення в головне меню
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                // Залишаємось в стані BUY_COFFEE
                break;
        }
    }

    // --- Обробники для процесу поповнення ---

    private void handleFillWater(String input) {
        try {
            this.water += Integer.parseInt(input);
            this.currentState = State.FILL_MILK; // Перехід до наступного кроку
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            // Залишаємось в стані FILL_WATER
        }
    }

    private void handleFillMilk(String input) {
        try {
            this.milk += Integer.parseInt(input);
            this.currentState = State.FILL_BEANS; // Перехід до наступного кроку
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private void handleFillBeans(String input) {
        try {
            this.beans += Integer.parseInt(input);
            this.currentState = State.FILL_CUPS; // Перехід до наступного кроку
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private void handleFillCups(String input) {
        try {
            this.cups += Integer.parseInt(input);
            this.currentState = State.MAIN_MENU; // Завершення поповнення, повернення в меню
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    // --- Основні дії машини ---

    // Перевіряє ресурси і готує каву
    private void checkAndMakeCoffee(CoffeeType coffee) {
        // Перевірка ресурсів
        if (this.water < coffee.water) {
            System.out.println("Sorry, not enough water!");
        } else if (this.milk < coffee.milk) {
            System.out.println("Sorry, not enough milk!");
        } else if (this.beans < coffee.beans) {
            System.out.println("Sorry, not enough coffee beans!");
        } else if (this.cups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
        } else {
            // Якщо ресурсів достатньо
            System.out.println("I have enough resources, making you a coffee!");

            // Оновлення ресурсів
            this.water -= coffee.water;
            this.milk -= coffee.milk;
            this.beans -= coffee.beans;
            this.cups -= 1;
            this.money += coffee.cost;
        }
    }

    // Дія "take": видає гроші
    private void actionTake() {
        System.out.println("I gave you " + this.money);
        this.money = 0;
    }

    // Дія "remaining": показує залишки
    private void actionRemaining() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(this.water + " of water");
        System.out.println(this.milk + " of milk");
        System.out.println(this.beans + " of coffee beans");
        System.out.println(this.cups + " of disposable cups");
        System.out.println(this.money + " of money");
    }
}


public class CoffeeMachine {

    /**
     * Головний метод (main) - точка входу програми.
     * Створює екземпляр MachineLogic і запускає цикл обробки введення.
     */
    public static void main(String[] args) {
        // Початкові ресурси
        MachineLogic machine = new MachineLogic(400, 540, 120, 9, 550);
        Scanner scanner = new Scanner(System.in);

        // Головний цикл програми
        // Працює, доки стан машини не стане EXIT
        while (machine.getCurrentState() != State.EXIT) {
            // 1. Друкуємо підказку для поточного стану
            machine.printCurrentPrompt();

            // 2. Отримуємо введення від користувача
            String input = scanner.nextLine();

            // 3. Передаємо введення в клас логіки для обробки
            machine.processInput(input);
        }

        scanner.close(); // Закриваємо сканер при виході
    }
}



