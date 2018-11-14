import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    Stack<String> stack = new Stack<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = reader.readLine();

        try {
            while (!str.matches("\\d+")) {
                Pattern p = Pattern.compile("\\([-\\d\\+\\*\\/]*\\)");
                Matcher m = p.matcher(str);
                if (!str.contains("(")) {
                    str = calculate(str);
                    break;
                }
                while (m.find()) {
                    str = str.replace(m.group(), calculate(m.group()));
                }
            }
        } catch (ArithmeticException e){
            System.out.println("Ошибка, возможно вы пытались выполнить деление на ноль");
        }
        System.out.println(str);
    }

    //4*5/(6-5)*5+6*8+(6*5*(5+4)*8+(11*4+6/5)+44-5)

    private static String calculate(String str) throws ArithmeticException{
        str = str.replaceAll("[\\(\\)]", "");
        while (str.contains("*") || str.contains("/")) {
            Pattern p = Pattern.compile("\\d+[\\*\\/]\\d+");
            Matcher m = p.matcher(str);
            while (m.find()) {
                String[] value = m.group().split("[\\*\\/]");
                Integer res = m.group().matches("\\d+[\\*]\\d+") ?
                        new Integer(value[0]) * new Integer(value[1]) :
                        new Integer(value[0]) / new Integer(value[1]);
                str = str.replace(m.group(), String.valueOf(res));
                break;
            }
        }
        while (!str.matches("\\d+")) {
            Pattern p = Pattern.compile("\\d+[-\\+]\\d+");
            Matcher m = p.matcher(str);
            while (m.find()) {
                String[] value = m.group().split("[-\\+]");
                Integer res = m.group().matches("\\d+[\\+]\\d+") ?
                        new Integer(value[0]) + new Integer(value[1]) :
                        new Integer(value[0]) - new Integer(value[1]);
                str = str.replace(m.group(), String.valueOf(res));
                break;
            }
        }
        return str;
    }

}
