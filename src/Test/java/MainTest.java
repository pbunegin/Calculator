import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void readConsole() {
        String str = "4*5/(6-5)*5+6*8+(6*5*(5+4)*8+(11*4-5*5+6/5*5)+44-5)";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(str.getBytes(Charset.forName("UTF-8")));
        assertEquals(str, Main.readConsole(inputStream));
        assertThrows(NullPointerException.class, () -> Main.readConsole(null));
        assertThrows(IOException.class, () -> Main.readConsole(new FileInputStream("gasergh")));
    }
}