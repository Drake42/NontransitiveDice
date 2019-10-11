import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
}
