import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    static Calculator calculator;

    @BeforeAll
    static void createCalc(){
        calculator = new Calculator();
    }

    @Test
    void calculate() {
        assertEquals("2371", calculator.calculate("4*5/(6-5)*5+6*8+(6*5*(5+4)*8+(11*4-5*5+6/5*5)+44-5)"));
        assertNull(calculator.calculate("4+5/0"));
        assertThrows(NullPointerException.class,()->calculator.calcGroup(null));
        assertNull(calculator.calculate("*8+5"));
    }

    @Test
    void calcGroup() {
        assertEquals("49", calculator.calcGroup("4+5*9-7/8"));
        assertEquals("-45", calculator.calcGroup("-5*9"));
        assertEquals("-311", calculator.calcGroup("4+5*9-8*5*9"));
        assertThrows(ArithmeticException.class,()->calculator.calcGroup("4+5*9-7/0"));
        assertThrows(NullPointerException.class,()->calculator.calcGroup(null));
    }

    @Test
    void calcExp() {
        assertEquals(20,calculator.calcExp("4","*","5"));
        assertEquals(0,calculator.calcExp("4","/","5"));
        assertEquals(2,calculator.calcExp("6","/","3"));
        assertEquals(30,calculator.calcExp("100","-","70"));
        assertEquals(33,calculator.calcExp("16","+","17"));

        assertThrows(ArithmeticException.class,()->calculator.calcExp("5","/","0"));
        assertThrows(NumberFormatException.class,()->calculator.calcExp("5","/",null));
    }

    @Test
    void checkExp() {
        assertTrue(calculator.checkExp("(5+6)"));
        assertTrue(calculator.checkExp("5+6"));
        assertFalse(calculator.checkExp("(5+6"));
        assertFalse(calculator.checkExp(null));
        assertFalse(calculator.checkExp("*8+5"));
        assertFalse(calculator.checkExp("8+5-"));
        assertFalse(calculator.checkExp("акмиы"));
    }
}