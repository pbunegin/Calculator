import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    private final static String GROUP_BRACKETS = "\\([-\\d\\+\\*\\/]*\\)";
    private final static String GROUP_MULTI_DIV = "\\d+[\\*\\/]\\d+";
    private final static String GROUP_PLUS_MINUS = "\\d+[-\\+]\\d+";
    private final static String MULTI_DIV_MINUS_PLUS = "[-\\+\\*\\/]";
    private final static String BRACKETS = "[\\(\\)]";
    private final static String DIGIT = "\\d+";

    public String calculate(String exp) {
        exp = exp.replaceAll("\\s", "");
        Pattern p = Pattern.compile(GROUP_BRACKETS);
        if (checkExp(exp)) {
            while (!exp.matches(DIGIT)) {
                //находим выражение без вложенных скобок
                Matcher m = p.matcher(exp);
                try {
                    while (m.find()) {
                        exp = exp.replace(m.group(), calcGroup(m.group()));
                    }
                    if (!exp.contains("(")) {
                        //если нет скобок просто считаем
                        exp = calcGroup(exp);
                        break;
                    }
                } catch (ArithmeticException e) {
                    System.out.println("Ошибка, возможно вы пытались выполнить деление на ноль");
                    return null;
                }
            }
            return exp;
        }
        return null;
    }

    public String calcGroup(String str) throws ArithmeticException {
        str = str.replaceAll(BRACKETS, "");
        Pattern p;
        while (!str.matches("[-]?" + DIGIT)) {
            if (str.contains("*") || str.contains("/")) {
                p = Pattern.compile(GROUP_MULTI_DIV);
            } else {
                p = Pattern.compile(GROUP_PLUS_MINUS);
            }
            Matcher m = p.matcher(str);
            m.find();
            String numberOne = m.group().split(MULTI_DIV_MINUS_PLUS)[0];
            String numberTwo = m.group().split(MULTI_DIV_MINUS_PLUS)[1];
            String operator = m.group().replaceAll("\\d", "");
            int res = calcExp(numberOne, operator, numberTwo);
            str = str.replaceFirst(numberOne + MULTI_DIV_MINUS_PLUS + numberTwo, String.valueOf(res));
        }
        return str;
    }

    public int calcExp(String numberOne, String operator, String numberTwo) throws ArithmeticException {
        int a = Integer.parseInt(numberOne);
        int b = Integer.parseInt(numberTwo);
        switch (operator) {
            case "*":
                return a * b;
            case "/":
                return a / b;
            case "+":
                return a + b;
            case "-":
                return a - b;
        }
        return 0;
    }

    public boolean checkExp(String str) {
        if (str == null)
            return false;
        if (!str.matches("[-\\/\\*\\+\\(\\)\\d]*")) {
            System.out.println("Не верный формат выражения.\n" +
                    "Выражение может содержать только операторы *, /, +, -, скобки и цифры");
            return false;
        }
        if (str.replaceAll("[\\(]", "").length() != str.replaceAll("[\\)]", "").length()) {
            System.out.println("Не равное количество открывающих и закрывающих скобок");
            return false;
        }
        if (str.matches("^[\\*\\/].*") || str.matches(".*[-\\*\\/\\+]$")) {
            System.out.println("Выражение не может начинаться со знака умножения или деления\n" +
                    "или заканчиваться любым из операторов");
            return false;
        }
        return true;
    }
}
