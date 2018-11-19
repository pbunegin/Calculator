import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")))) {
            String str = reader.readLine();

            if (str != null && checkStr(str)) {
                while (!str.matches("\\d+")) {
                    //находим выражение без вложенных скобок
                    Pattern p = Pattern.compile("\\([-\\d\\+\\*\\/]*\\)");
                    Matcher m = p.matcher(str);
                    while (m.find()) {
                        str = str.replace(m.group(), calculate(m.group()));
                    }

                    if (!str.contains("(")) {
                        //если нет скобок просто считаем
                        str = calculate(str);
                        break;
                    }
                }
                System.out.println(str);
            }
        } catch (ArithmeticException e) {
            System.out.println("Ошибка, возможно вы пытались выполнить деление на ноль");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //4*5/(6-5)*5+6*8+(6*5*(5+4)*8+(11*4-5*5+6/5*5)+44-5)

    private static String calculate(String str) throws ArithmeticException {
        str = str.replaceAll("[\\(\\)]", "");
        while (str.contains("*") || str.contains("/")) {
            Pattern p = Pattern.compile("\\d+[\\*\\/]\\d+");
            Matcher m = p.matcher(str);
            while (m.find()) {
                String[] value = m.group().split("[\\*\\/]");
                Integer res = m.group().matches("\\d+[\\*]\\d+") ?
                        Integer.parseInt(value[0]) * Integer.parseInt(value[1]) :
                        Integer.parseInt(value[0]) / Integer.parseInt(value[1]);
                str = str.replaceFirst(m.group().replaceAll("\\*", "\\\\*").
                                replaceAll("\\/", "\\\\/"),
                        String.valueOf(res));
                break;
            }
        }
        while (!str.matches("\\d+")) {
            Pattern p = Pattern.compile("\\d+[-\\+]\\d+");
            Matcher m = p.matcher(str);
            while (m.find()) {
                String[] value = m.group().split("[-\\+]");
                Integer res = m.group().matches("\\d+[\\+]\\d+") ?
                        Integer.parseInt(value[0]) + Integer.parseInt(value[1]) :
                        Integer.parseInt(value[0]) - Integer.parseInt(value[1]);
                str = str.replaceFirst(m.group().replaceAll("\\+", "\\\\+").
                                replaceAll("\\-", "\\\\-"),
                        String.valueOf(res));
                break;
            }
        }
        return str;
    }

    private static boolean checkStr(String str) {
        if (!str.matches("[-\\/\\*\\+\\(\\)\\d]*")) {
            System.out.println("Не верный формат выражения");
            return false;
        }
        if (str.replaceAll("[^\\(]", "").length() != str.replaceAll("[^\\)]", "").length()) {
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
