package CreditCalculator;

import java.lang.Math;

public class CalculatorLogic {

    private String type;
    private Double principal;
    private Integer periods;
    private Double interest;
    private Double payment;
    private Double monthlyInterestRate;

    /**
     * Перевіряє вхідні параметри на коректність, парсить їх
     * та зберігає в полях класу.
     * @return true, якщо параметри коректні, false інакше.
     */
    public boolean validateAndProcess(String type, String principalStr, String periodsStr, String interestStr, String paymentStr, int argumentCount) {

        // 1. Перевірка наявності type і interest
        if (type == null || interestStr == null) {
            return false;
        }

        // 2. Перевірка типу
        if (!type.equals("annuity") && !type.equals("diff")) {
            return false;
        }
        this.type = type;

        // 3. Перевірка кількості параметрів (має бути не менше 4)
        if (argumentCount < 4) {
            return false;
        }

        // 4. Перевірка: diff-платіж не може мати параметр payment
        if (type.equals("diff") && paymentStr != null) {
            return false;
        }

        // 5. Парсинг та перевірка на від'ємні та некоректні числові значення
        try {
            this.principal = principalStr != null ? Double.parseDouble(principalStr) : null;
            this.periods = periodsStr != null ? Integer.parseInt(periodsStr) : null;
            this.interest = Double.parseDouble(interestStr);
            this.payment = paymentStr != null ? Double.parseDouble(paymentStr) : null;

            // Перевірка на від'ємні значення
            if (this.principal != null && this.principal < 0 ||
                    this.periods != null && this.periods < 0 ||
                    this.interest < 0 ||
                    this.payment != null && this.payment < 0) {
                return false;
            }

            // Відсоток не може бути нульовим
            if (this.interest == 0.0) {
                return false;
            }

            // Розрахунок номінальної місячної ставки
            this.monthlyInterestRate = this.interest / (100.0 * 12.0);

        } catch (NumberFormatException e) {
            return false; // Помилка парсингу
        }

        return true;
    }

    /**
     * Викликає відповідний метод розрахунку на основі типу кредиту.
     */
    public void calculate() {
        if (this.type.equals("annuity")) {
            calculateAnnuity();
        } else if (this.type.equals("diff")) {
            calculateDifferentiated();
        }
    }

    /**
     * Логіка розрахунку диференційованих платежів.
     * Dm = P/n + i * (P - P * (m-1) / n)
     */
    private void calculateDifferentiated() {
        long totalPayment = 0;

        // Основна сума, яка погашається щомісяця
        double principalPerMonth = this.principal / this.periods;

        for (int m = 1; m <= this.periods; m++) {
            // Розрахунок відсоткової частини для m-го місяця: i * (P - (m-1) * (P/n))
            double interestPart = this.monthlyInterestRate * (this.principal - principalPerMonth * (m - 1));

            // Загальний платіж (округлення вгору)
            long monthlyPayment = (long) Math.ceil(principalPerMonth + interestPart);

            System.out.println("Month " + m + ": payment is " + monthlyPayment);
            totalPayment += monthlyPayment;
        }

        long overpayment = totalPayment - this.principal.longValue();
        System.out.println("Overpayment = " + overpayment);
    }

    /**
     * Логіка розрахунку ануїтетних платежів.
     */
    private void calculateAnnuity() {

        // Визначаємо, що саме потрібно розрахувати
        if (this.payment == null) {
            // Обчислення ануїтетного платежу (A)
            calculateAnnuityPayment();
        } else if (this.principal == null) {
            // Обчислення основної суми кредиту (P)
            calculatePrincipal();
        } else if (this.periods == null) {
            // Обчислення кількості платежів (n)
            calculatePeriods();
        }
    }

    /**
     * A = P * (i * (1 + i)^n) / ((1 + i)^n - 1)
     */
    private void calculateAnnuityPayment() {
        double P = this.principal;
        int n = this.periods;
        double i = this.monthlyInterestRate;

        double power = Math.pow(1 + i, n);
        double annuityPaymentDouble = P * (i * power) / (power - 1);

        // Округлення до найближчого цілого вгору
        long A = (long) Math.ceil(annuityPaymentDouble);

        System.out.println("Your annuity payment = " + A + "!");

        // Розрахунок переплати
        long totalPayment = A * n;
        long overpayment = totalPayment - (long) P;
        System.out.println("Overpayment = " + overpayment);
    }

    /**
     * P = A / (i * (1 + i)^n / ((1 + i)^n - 1))
     */
    private void calculatePrincipal() {
        double A = this.payment;
        int n = this.periods;
        double i = this.monthlyInterestRate;

        // Вираз в знаменнику: (i * (1 + i)^n) / ((1 + i)^n - 1)
        double power = Math.pow(1 + i, n);
        double expression = (i * power) / (power - 1);

        double principalDouble = A / expression;

        // Округлення до найближчого цілого
        long P = (long) Math.round(principalDouble);

        System.out.println("Your loan principal = " + P + "!");

        // Розрахунок переплати
        long totalPayment = (long) (A * n);
        long overpayment = totalPayment - P;
        System.out.println("Overpayment = " + overpayment);
    }

    /**
     * n = log(1+i) * (A / (A - i * P))
     */
    private void calculatePeriods() {
        double P = this.principal;
        double A = this.payment;
        double i = this.monthlyInterestRate;

        // Перевірка умови: A - i * P має бути > 0
        if (A - i * P <= 0) {
            System.out.println("The monthly payment is too low to repay the loan.");
            return;
        }

        // Обчислення logNumber = A / (A - i * P)
        double logNumber = A / (A - i * P);

        // n = log_base(logNumber), де base = 1 + i
        // n = Math.log(logNumber) / Math.log(base)
        double nDouble = Math.log(logNumber) / Math.log(1 + i);

        // Кількість місяців округлюємо вгору
        int n = (int) Math.ceil(nDouble);

        // Форматування результату (роки та місяці)
        int years = n / 12;
        int months = n % 12;

        String result = "It will take ";

        if (years > 0) {
            result += years + (years == 1 ? " year" : " years");
            if (months > 0) {
                result += " and ";
            }
        }
        if (months > 0) {
            result += months + (months == 1 ? " month" : " months");
        }

        // Випадок, якщо n < 1 місяця (що тут малоймовірно), але для повноти
        if (years == 0 && months == 0) {
            result += n + (n == 1 ? " month" : " months");
        }

        result += " to repay this loan!";
        System.out.println(result);

        // Розрахунок переплати
        // Потрібно враховувати, що останній платіж може бути меншим, але для ануїтету
        // зазвичай береться загальна сума A * n
        long totalPayment = (long) Math.ceil(A) * n;
        long overpayment = totalPayment - (long) P;
        System.out.println("Overpayment = " + overpayment);
    }
}