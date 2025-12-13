package CreditCalculator;

public class CreditCalculator {

    public static void main(String[] args) {

        // Використовуємо System.getProperty для отримання аргументів типу -Dkey=value
        String type = System.getProperty("type");
        String principalStr = System.getProperty("principal");
        String periodsStr = System.getProperty("periods");
        String interestStr = System.getProperty("interest");
        String paymentStr = System.getProperty("payment");

        // Збираємо аргументи для зручності підрахунку
        int argumentCount = 0;
        if (type != null) argumentCount++;
        if (principalStr != null) argumentCount++;
        if (periodsStr != null) argumentCount++;
        if (interestStr != null) argumentCount++;
        if (paymentStr != null) argumentCount++;

        // Створюємо екземпляр класу логіки
        CalculatorLogic logic = new CalculatorLogic();

        // Перевіряємо та обробляємо параметри
        if (logic.validateAndProcess(type, principalStr, periodsStr, interestStr, paymentStr, argumentCount)) {
            // Якщо валідація успішна, виконуємо розрахунок
            logic.calculate();
        } else {
            // Якщо валідація не пройдена
            System.out.println("Incorrect parameters");
        }
    }
}