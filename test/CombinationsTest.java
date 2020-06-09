import org.junit.*;

import java.util.*;

import static junit.framework.TestCase.*;

public class CombinationsTest {

    private List<Die> elements;
    private static final Die d1 = new Die(new int[]{1, 1, 1});
    private static final Die d2 = new Die(new int[]{2, 2, 2});
    private static final Die d3 = new Die(new int[]{3, 3, 3});

    @Before
    public void setup() {
        elements = new ArrayList<>();
    }

    @Test
    public void testCombinationsOfZero() {

        List<Set<Die>> results;
        results = Combinations.generateCombinationsOf(elements);

        assertNotNull(results);
        assertEquals(1, results.size() );
        Set<Die> result0 = results.get(0);
        assertNotNull(result0);
        assertEquals( 0, result0.size() );

        results = Combinations.generateCombinationsOf(2, elements);
        assertNotNull(results);
        assertEquals(0, results.size() );

    }

    @Test
    public void testCombinationsOfOne() {

        elements.add(d1);

        List<Set<Die>> results = Combinations.generateCombinationsOf(elements);

        assertNotNull(results);
        assertEquals(2, results.size() );
        Set<Die> result0 = results.get(0);
        assertNotNull(result0);
        assertEquals( 0, result0.size() );

        Set<Die> result1 = results.get(1);
        assertNotNull(result1);
        assertEquals( 1, result1.size() );
        assertTrue( result1.contains( d1 ) );

        results = Combinations.generateCombinationsOf(2, elements);
        assertNotNull(results);
        assertEquals(0, results.size() );
    }

    @Test
    public void testCombinationsOfTwo() {
        elements.add(d1);
        elements.add(d2);

        List<Set<Die>> results = Combinations.generateCombinationsOf(elements);

        assertNotNull(results);
        assertEquals(4, results.size());

        results = Combinations.generateCombinationsOf(2, elements);
        assertNotNull(results);
        assertEquals(1, results.size() );

    }

    @Test
    public void testCombinationsOfThree() {
        elements.add(d1);
        elements.add(d2);
        elements.add(d3);

        List<Set<Die>> results = Combinations.generateCombinationsOf(elements);

        assertNotNull(results);
        assertEquals(8, results.size());

        results = Combinations.generateCombinationsOf(2, elements);
        assertNotNull(results);
        assertEquals(3, results.size() );
        //if we wanted to verify, we'd expect results to contain these combinations: (d1,d2) (d2,d3) (d1,d3)
    }

}
