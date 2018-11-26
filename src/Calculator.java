import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    private final String groupBrackets = "\\([-\\d\\+\\*\\/]*\\)";
    private final String groupMultiDiv = "\\d+[\\*\\/]\\d+";
    private final String groupPlusMinus = "\\d+[-\\+]\\d+";
    private final String multiOrDiv = "[\\*\\/]";
    private final String minusOrPlus = "[-\\+]";
    private final String digMultiDig = "\\d+[\\*]\\d+";
    private final String digPlusDig = "\\d+[\\+]\\d+";
    private final String brackets = "[\\(\\)]";
    private final String digit = "\\d+";

    public String calculate(String exp) {
        if (checkExp(exp)) {
            while (!exp.matches(digit)) {
                //находим выражение без вложенных скобок
                Pattern p = Pattern.compile(groupBrackets);
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
        str = str.replaceAll(brackets, "");
        while (str.contains("*") || str.contains("/")) {
            Pattern p = Pattern.compile(groupMultiDiv);
            Matcher m = p.matcher(str);
            m.find();
            String[] value = m.group().split(multiOrDiv);
            Integer res = m.group().matches(digMultiDig) ?
                    Integer.parseInt(value[0]) * Integer.parseInt(value[1]) :
                    Integer.parseInt(value[0]) / Integer.parseInt(value[1]);
            str = str.replaceFirst(m.group().replaceAll("\\*", "\\\\*").
                            replaceAll("\\/", "\\\\/"),
                    String.valueOf(res));
        }
        while (!str.matches(digit)) {
            Pattern p = Pattern.compile(groupPlusMinus);
            Matcher m = p.matcher(str);
            m.find();
            String[] value = m.group().split(minusOrPlus);
            Integer res = m.group().matches(digPlusDig) ?
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
