import java.util.HashMap;
import java.util.Scanner;

class Main {
    private static final String[] ROMAN_NUMERAL = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "L", "C"};
    private static final HashMap<String, Integer> romanToInteger = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        romanToInteger.put("I", 1);
        romanToInteger.put("II", 2);
        romanToInteger.put("III", 3);
        romanToInteger.put("IV", 4);
        romanToInteger.put("V", 5);
        romanToInteger.put("VI", 6);
        romanToInteger.put("VII", 7);
        romanToInteger.put("VIII", 8);
        romanToInteger.put("IX", 9);
        romanToInteger.put("X", 10);
        romanToInteger.put("L", 50);
        romanToInteger.put("C", 100);

        while (true) {
            System.out.println("Введите арифметическое выражение с римскими или арабскими значениями с пробелами");
            System.out.println("Например: 1 + 1 или I + I");
            try {
                System.out.println(calc(scanner.nextLine()));
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
                break;
            }
        }
    }

    public static String calc(String input) {
        String[] expressionArray = input.split(" ");

        if (expressionArray.length != 3)
            throw new IllegalArgumentException("Неверный ввод");

        int num1;
        int num2;

        if (expressionArray[0].matches("-?\\d+") && expressionArray[2].matches("-?\\d+")) {
            num1 = Integer.parseInt(expressionArray[0]);
            num2 = Integer.parseInt(expressionArray[2]);
        } else if (romanToInteger.containsKey(expressionArray[0]) && romanToInteger.containsKey(expressionArray[2])) {
            num1 = romanToInteger.get(expressionArray[0]);
            num2 = romanToInteger.get(expressionArray[2]);
        } else
            throw new IllegalArgumentException("Оба числа на вводе должны быть арабскими или римскими");

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10)
            throw new IllegalArgumentException("Числа на вводе должны быть от 1 до 10");

        String operator = expressionArray[1];

        int result = switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> num1 / num2;
            default -> throw new IllegalArgumentException("Неизвестный оператор");
        };

        if (expressionArray[0].matches("-?\\d+") && expressionArray[2].matches("-?\\d+"))
            return String.valueOf(result);
        else {
            if (result < 1)
                throw new IllegalArgumentException("Результат вычисления римских чисел не может быть меньше 1");
            else {
                StringBuilder builder = new StringBuilder();
                int i = ROMAN_NUMERAL.length - 1;

                while (result > 0) {
                    if (result >= romanToInteger.get(ROMAN_NUMERAL[i])) {
                        builder.append(ROMAN_NUMERAL[i]);
                        result -= romanToInteger.get(ROMAN_NUMERAL[i]);
                    } else
                        i--;
                }

                return builder.toString();
            }
        }
    }
}
