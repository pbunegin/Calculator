import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    Stack<String> stack = new Stack<>();

    public static void main(String[] args) {
        String str = "4*5+(6-5)*5+6*8+(6*5+(5+4)*8+(11*4+6/5)+44-5)";

        while (str.matches("\\([\\d+\\-*\\/\\s]*\\)")) {
            Pattern p = Pattern.compile("\\([\\d+\\-*\\/\\s]*\\)");
            Matcher m = p.matcher(str);
            String res = "";
            while (m.find()) {
                str = str.replace(m.group(), calculate(m.group()));
            }
        }
        System.out.println(str);
    }

    private static String calculate(String str) {
        while (str.matches("[*/]+")) {
            Pattern p = Pattern.compile("\\d+[*/]\\d");
            Matcher m = p.matcher(str);
            while (m.find()) {
                String[] temp = m.group().split("[*/]");
                Integer res = m.group().matches("[*]") ?
                        new Integer(temp[0]) * new Integer(temp[1]) :
                        new Integer(temp[0]) / new Integer(temp[1]);
                str.replace(m.group(), String.valueOf(res));
            }
        }
        while (str.matches("[+-]+")) {
            Pattern p = Pattern.compile("\\d+[+-]\\d");
            Matcher m = p.matcher(str);
            while (m.find()) {
                String[] temp = m.group().split("[+-]");
                Integer res = m.group().matches("[+]") ?
                        new Integer(temp[0]) + new Integer(temp[1]) :
                        new Integer(temp[0]) - new Integer(temp[1]);
                str.replace(m.group(), String.valueOf(res));
            }
        }
        return "(" + str + ")";
    }

}
