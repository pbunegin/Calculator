import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    private final static String GROUP_BRACKETS = "\\([-\\d\\+\\*\\/]*\\)";
    private final static String GROUP_MULTI_DIV = "\\d+[\\*\\/]\\d+";
    private final static String GROUP_PLUS_MINUS = "\\d+[-\\+]\\d+";
    private final static String MULTI_OR_DIV = "[\\*\\/]";
    private final static String MINUS_OR_PLUS = "[-\\+]";
    private final static String DIG_MULTI_DIG = "\\d+[\\*]\\d+";
    private final static String DIG_PLUS_DIG = "\\d+[\\+]\\d+";
    private final static String BRACKETS = "[\\(\\)]";
    private final static String DIGIT = "\\d+";

    public String calculate(String exp) {
        if (checkExp(exp)) {
            while (!exp.matches(DIGIT)) {
                //находим выражение без вложенных скобок
                Pattern p = Pattern.compile(GROUP_BRACKETS);
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
                }
            }
        }
        return exp;
    }

    private String calcGroup(String str) throws ArithmeticException {
        str = str.replaceAll(BRACKETS, "");
        while (str.contains("*") || str.contains("/")) {
            Pattern p = Pattern.compile(GROUP_MULTI_DIV);
            Matcher m = p.matcher(str);
            m.find();
            String[] value = m.group().split(MULTI_OR_DIV);
            Integer res = m.group().matches(DIG_MULTI_DIG) ?
                    Integer.parseInt(value[0]) * Integer.parseInt(value[1]) :
                    Integer.parseInt(value[0]) / Integer.parseInt(value[1]);
            str = str.replaceFirst(m.group().replaceAll("\\*", "\\\\*").
                            replaceAll("\\/", "\\\\/"),
                    String.valueOf(res));
        }
        while (!str.matches(DIGIT)) {
            Pattern p = Pattern.compile(GROUP_PLUS_MINUS);
            Matcher m = p.matcher(str);
            m.find();
            String[] value = m.group().split(MINUS_OR_PLUS);
            Integer res = m.group().matches(DIG_PLUS_DIG) ?
                    Integer.parseInt(value[0]) + Integer.parseInt(value[1]) :
                    Integer.parseInt(value[0]) - Integer.parseInt(value[1]);
            str = str.replaceFirst(m.group().replaceAll("\\+", "\\\\+").
                            replaceAll("\\-", "\\\\-"),
                    String.valueOf(res));
        }
        return str;
    }

    private static boolean checkExp(String str) {
        if (str == null)
            return false;
        if (!str.matches("[-\\/\\*\\+\\(\\)\\d]*")) {
            System.out.println("Не верный формат выражения");
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
