import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

//4*5/(6-5)*5+6*8+(6*5*(5+4)*8+(11*4-5*5+6/5*5)+44-5)
public class Main {
    public static void main(String[] args) {
        String result = new Calculator().calculate(readConsole(System.in));
        System.out.println(result);
    }

    public static String readConsole(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")))) {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
