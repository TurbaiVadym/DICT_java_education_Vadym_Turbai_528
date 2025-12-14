package RockPaperScissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GameLogic {
    private List<String> options;
    private final Random random;

    public GameLogic() {
        this.random = new Random();
    }

    /**
     * Зчитує початковий рейтинг користувача з файлу rating.txt
     */
    public int getInitialRating(String userName) {
        File file = new File("rating.txt");
        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNext()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(" ");
                    if (parts.length >= 2 && parts[0].equals(userName)) {
                        return Integer.parseInt(parts[1]);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error reading rating file.");
            }
        }
        return 0;
    }

    /**
     * Встановлює варіанти гри
     */
    public void setOptions(String inputOptions) {
        if (inputOptions.trim().isEmpty()) {// За замовчуванням
            this.options = new ArrayList<>(Arrays.asList("rock", "paper", "scissors"));
        } else {
            // Розбір введених опцій через кому
            String[] splitOptions = inputOptions.split(",");
            this.options = new ArrayList<>();
            for (String opt : splitOptions) {
                this.options.add(opt.trim());
            }
        }
    }

    public List<String> getOptions() {
        return options;
    }

    public String getRandomOption() {
        int index = random.nextInt(options.size());
        return options.get(index);
    }

    /**
     * Визначає результат гри.
     * Повертає: 0 (Нічия), 1 (Перемога користувача), -1 (Поразка користувача)
     */
    public int determineResult(String userOption, String computerOption) {
        if (userOption.equals(computerOption)) {
            return 0;// Нічия
        }

        int userIndex = options.indexOf(userOption);
        int computerIndex = options.indexOf(computerOption);
        int size = options.size();

        // Математика визначення переможця у колі
        int distance = (computerIndex - userIndex + size) % size;

        if (distance <= size / 2 && distance != 0) {
            return -1; // Поразка
        } else {
            return 1; // Перемога
        }
    }
}
