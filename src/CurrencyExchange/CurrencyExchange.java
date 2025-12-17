package CurrencyExchange;

import java.util.Scanner;

public class CurrencyExchange {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Отримуємо базову валюту
        String baseCurrency = "";
        while (baseCurrency.isEmpty()) {
            System.out.print("Enter base currency code: ");
            baseCurrency = scanner.nextLine().trim();
        }

        // Ініціалізуємо наш менеджер
        RateManager rateManager = new RateManager(baseCurrency);

        // Цикл роботи з користувачем
        while (true) {
            // Отримуємо цільову валюту
            String targetCurrency = scanner.nextLine().trim();
            if (targetCurrency.isEmpty()) {
                break;
            }

            // Отримуємо суму
            String amountString = scanner.nextLine().trim();
            double amount;
            try {
                amount = Double.parseDouble(amountString);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount.");
                continue;
            }

            // Перевірка кешу
            System.out.println("Checking the cache...");
            if (rateManager.isCached(targetCurrency)) {
                System.out.println("It is in the cache!");
            } else {
                System.out.println("Sorry, but it is not in the cache!");
            }

            // Отримання курсу та розрахунок
            double rate = rateManager.getRate(targetCurrency);

            if (rate != -1.0) {
                double result = amount * rate;
                System.out.printf("You received %.2f %s.%n", result, targetCurrency.toUpperCase());
            } else {
                System.out.println("Exchange rate not found for " + targetCurrency.toUpperCase());
            }
        }
    }
}
