import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

//4*5/(6-5)*5+6*8+(6*5*(5+4)*8+(11*4-5*5+6/5*5)+44-5)
public class Main {
    public static void main(String[] args) {
        String str = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")))) {
            str = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = new Calculator().calculate(str);
        System.out.println(result);
    }
}
