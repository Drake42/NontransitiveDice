import org.junit.*;

import java.math.BigInteger;

import static junit.framework.TestCase.*;


public class StuffTest {

    @Test
    public void testExponent() {
//        assertEquals( 8, 2^3 );
//        assertEquals( 81, 9^2 );

        assertEquals( 8, (long) Math.pow(2, 3));
        assertEquals( 81, (long) Math.pow(9, 2));

        BigInteger x = new BigInteger("2");
        assertEquals( 8, x.pow(3).longValue() );
    }

}
