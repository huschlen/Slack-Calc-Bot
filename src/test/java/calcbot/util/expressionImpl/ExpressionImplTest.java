package calcbot.util.expressionImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import calcbot.util.expressionImpl.ExpressionImpl;

public class ExpressionImplTest {
  
    private ExpressionImpl expressionImpl;
  
    @Before
    public void setUp() {
        expressionImpl  = new ExpressionImpl();
    }
    
    @Test
    public void testAdditionPass() {
        assertEquals(Double.valueOf(3.3), Double.valueOf(expressionImpl.eval("1.1 + 2.2")));
    }
    
    @Test
    public void testAdditionFail() {
        assertNotEquals(Double.valueOf(1.2), Double.valueOf(expressionImpl.eval("1.1 + 2.2")));
    }
    
    @Test
    public void testSubtractionPass() {
        assertEquals(Double.valueOf(1.1), Double.valueOf(expressionImpl.eval("2.2 - 1.1")));
    }
    
    @Test
    public void testSubtractionFail() {
        assertNotEquals(Double.valueOf(3.3), Double.valueOf(expressionImpl.eval("2.2 - 1.1")));
    }
  
    @Test
    public void testMultiplicationPass() {
        assertEquals(Double.valueOf(9.7), Double.valueOf(expressionImpl.eval("2.2 * 4.4")));
    }
    
    @Test
    public void testMultiplicationFail() {
        assertNotEquals(Double.valueOf(1.1), Double.valueOf(expressionImpl.eval("8.8 * 4.4")));
    }
    
    @Test
    public void testDivisionPass() {
        assertEquals(Double.valueOf(2.0), Double.valueOf(expressionImpl.eval("4.4 / 2.2")));
    }
    
    @Test
    public void testDivisionFail() {
        assertNotEquals(Double.valueOf(3.3), Double.valueOf(expressionImpl.eval("4.4 / 2.2")));
    }
    
    @Test
    public void testParentheses() {
        assertEquals(Double.valueOf(3.3), Double.valueOf(expressionImpl.eval("(1.1 + 2.2)")));
        assertEquals(Double.valueOf(2.2), Double.valueOf(expressionImpl.eval("((3.3 - 1.1))")));
        assertEquals(Double.valueOf(10.9), Double.valueOf(expressionImpl.eval("(1.1 + 2.2) * (4.4 - 1.1)")));
        assertEquals(Double.valueOf(2.0), Double.valueOf(expressionImpl.eval("(7.7 - 1.1) / (1.1 + 2.2)")));
    }

}
