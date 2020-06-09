import org.junit.Test;

import static org.junit.Assert.*;

public class DieTest {
    @Test
    public void testBeats() {
        Die die1 = new Die( new int[]{1} );
        Die die2 = new Die( new int[]{2} );

        assertTrue( die2.beats( die1 ));
        assertFalse( die1.beats( die2 ));

        assertFalse( die1.beats( die1 ));
        assertFalse( die1.beats( new Die( new int[]{1} ) ));


        Die die11 = new Die( new int[]{1, 1} );
        assertTrue( die2.beats(die11) );


        Die die21 = new Die( new int[]{2, 1} );
        assertFalse( die21.beats( die11 ));

        Die die12 = new Die( new int[]{1, 2} );
        assertFalse( die21.beats( die12 ));


        Die die221 = new Die( new int[]{2, 2, 1} );
        assertTrue( die221.beats( die11 ));

        Die die211 = new Die( new int[]{2, 1, 1} );
        assertFalse( die221.beats( die211 ));

        Die die111 = new Die( new int[]{1, 1, 1} );
        assertTrue( die221.beats( die111) );

    }

    @Test
    public void testEquals() {

        //For any non-null reference value x, x.equals(null) should return false.
        Die x = new Die( new int[] {1, 2, 3} );
        assertNotNull( x );

        //reflexive: for any non-null reference value x, x.equals(x) should return true.
        assertEquals(x, x);

        //symmetric: for any non-null reference values x and y, x.equals(y) should return true if and only if y.equals(x) returns true.
        Die y1 = new Die( new int[] {1, 2, 3} );
        assertEquals(x, y1);
        Die y2 = new Die( new int[] {3, 2, 1} );
        assertEquals(x, y2);
        Die y3 = new Die( new int[] {1, 2, 4} );
        assertNotEquals(x, y3);

        /*
        we're deliberately not testing for the required properties of...
            transitive: for any non-null reference values x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) should return true.
            consistent: for any non-null reference values x and y, multiple invocations of x.equals(y) consistently return true or consistently return false, provided no information used in equals comparisons on the objects is modified.

        We're not testing these because they are trivially true for our implimentation, and also,
        we have no idea how to create a failure case for them.
         */

    }

    @Test
    public void testToString() {
        Die x = new Die( new int[] {1, 2, 3} );
        String result = x.toString();
        String expected = "Die:[1,2,3]";
        assertEquals(expected, result);
    }

}
