package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JunitTest {
    private Calculator calc;

    @BeforeEach
    public void setUp()
    {
        calc = new Calculator();
    }

    @Test
    public void testAddition()
    {
        assertEquals(12.0, calc.add(5.0, 7.0), 0.0001);
    }
    @Test
    public void testSubtraction()
    {
        assertEquals(67.0, calc.subtract(676.7, 609.7), 0.0001);
    }
    @Test
    public void testMultiplication()
    {
        assertEquals(4586.4027, calc.multiply(865.359, 5.3), 0.0001);
    }
    @Test
    public void testDivision()
    {
        assertEquals(66.666666666666, calc.divide(100, 1.5), 0.0001);
    }
    @Test
    public void testPower()
    {
        assertEquals(625, calc.power(5,4), 0.0001);
    }
    @Test
    public void testReciprocal()
    {
        assertEquals(0.01492537313432835, calc.reciprocal(67), 0.0001);
    }
    @Test
    public void testSquareRoot()
    {
        assertEquals(69.67, calc.sqrt(4853.9089),  0.0001);
    }
    @Test
    public void testLogarithm()
    {
        assertEquals(5, calc.log(148.413159102576603), 0.0001);
    }
    @Test
    public void testFactorial()
    {
        assertEquals(120, calc.factorial(5), 0.0001);
    }
    @Test
    public void testToggleSign()
    {
        assertEquals(-67, calc.toggleSign(67), 0.0001);
    }
}
