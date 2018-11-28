import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    static Calculator calculator;

    @BeforeAll
    static void createCalc() {
        calculator = new Calculator();
    }

    @Test
    void calculate() {
        String[] res1 = {"2371", "-45", "-311"};
        String[] exp1 = {
                calculator.calculate("4*5/(6-5)*5+6*8+(6*5*(5+4)*8+(11*4-5*5+6/5*5)+44-5)"),
                calculator.calculate("-5*9"),
                calculator.calculate("4+5*9-8*5*9")
        };

        assertThat(res1, equalTo(exp1));

        assertNull(calculator.calculate("4+5/0"));
        assertNull(calculator.calculate("*8+5"));
        assertNull(calculator.calculate("(8+5"));
        assertThrows(NullPointerException.class, () -> calculator.calculate(null));
    }

    @Test
    void calcGroup() {
        assertEquals("49", calculator.calcGroup("4+5*9-7/8"));
        assertEquals("-45", calculator.calcGroup("-5*9"));
        assertEquals("-311", calculator.calculate("4+5*9-8*5*9"));

        assertThrows(ArithmeticException.class, () -> calculator.calcGroup("4+5*9-7/0"));
        assertThrows(NullPointerException.class, () -> calculator.calcGroup(null));
    }

    @Test
    void calcExp() {
        int[] res1 = {20, 0, 2, 30, 33};
        int[] exp1 = {
                calculator.calcExp("4", "*", "5"),
                calculator.calcExp("4", "/", "5"),
                calculator.calcExp("6", "/", "3"),
                calculator.calcExp("100", "-", "70"),
                calculator.calcExp("16", "+", "17")
        };

        assertThat(res1, equalTo(exp1));

        assertThrows(ArithmeticException.class, () -> calculator.calcExp("5", "/", "0"));
        assertThrows(NumberFormatException.class, () -> calculator.calcExp("5", "/", null));
        assertThrows(NumberFormatException.class, () -> calculator.calcExp("5", "/", "\\)"));
        assertThrows(NullPointerException.class, () -> calculator.calcExp("5", null, "5"));
    }

    @Test
    void checkExp() {
        assertTrue(calculator.checkExp("(5+6)"));
        assertTrue(calculator.checkExp("5+6"));
        assertTrue(calculator.checkExp("4+5*9-8*5*9"));
        assertTrue(calculator.checkExp("4+5/0"));

        assertFalse(calculator.checkExp("(5+6"));
        assertFalse(calculator.checkExp(null));
        assertFalse(calculator.checkExp("*8+5"));
        assertFalse(calculator.checkExp("8+5-"));
        assertFalse(calculator.checkExp("акмиы"));
    }
}