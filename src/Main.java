import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    Stack<String> stack = new Stack<>();

    public static void main(String[] args) {
        String str = "4*5+(6-5)*5+6*8+(6*5+(5+4)*8+(11*4+6/5)+44-5)";

        while (!str.matches("\\d+")) {
            Pattern p = Pattern.compile("\\([-\\d\\+\\*\\/]*\\)");
            Matcher m = p.matcher(str);
            String res = "";
            while (m.find()) {
                str = str.replace(m.group(), calculate(m.group()));
            }
        }
        System.out.println(str);
    }

    private static String calculate(String str) {
        Pattern p = Pattern.compile("\\d+[\\*\\/]\\d+");
        Matcher m = p.matcher(str);
        while (m.find()) {
            String[] value = m.group().split("[\\*\\/]");
            Integer res = m.group().matches("\\d+[\\*]\\d+") ?
                    new Integer(value[0]) * new Integer(value[1]) :
                    new Integer(value[0]) / new Integer(value[1]);
            str.replace(m.group(), String.valueOf(res));
        }
        p = Pattern.compile("\\d+[-\\+]\\d+");
        m = p.matcher(str);
        while (m.find()) {
            String[] value = m.group().split("[-\\+]");
            Integer res = m.group().matches("\\d+[\\+]\\d+") ?
                    new Integer(value[0]) + new Integer(value[1]) :
                    new Integer(value[0]) - new Integer(value[1]);
            str.replace(m.group(), String.valueOf(res));
        }
        return str;
    }

}
