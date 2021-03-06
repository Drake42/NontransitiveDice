import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class SetOfDiceGeneratorTest {

    /*
    Generate all unique sets of dice for these constraints:
        3 dice, each with 3 sides
        the sum of face values for all sides of any die is 0
        the range of allowed values for all sides but one of any die is -4 to 4
    Then confirm that the generated set matches our result from hand calculation.
    The hand calculation is at https://docs.google.com/spreadsheets/d/1VSwHnrMz5Oa-ZO9grq8vIIFW-Haf8TSwCq9Bi6GCo0k/edit#gid=0.
        -4	-4	8
        -4  0   4
        -3	-4	7
        -3	-3	6
        -3	-2	5
        -2	-4	6
        -2	4	-2
        -1	-4	5
        -1	-3	4
        -1	-2	3
        -1	-1	2
        0	-3	3
        0	-2	2
        0	-1	1
        0	0	0
        1	-4	3
        1	-3	2
        1	-2	1
        2	-4	2
        3	2	-5
        3	3	-6
        4	1	-5
        4	2	-6
        4	3	-7
        4	4	-8
    */
    @Test
    public void testGenerateCase01() {


        Set<Die> generatedSetOfDice;

        int numFacesPerDie = 3;
        int faceValueSum = 0;
        int faceValueLow = -4;
        int faceValueHigh = 4;
        generatedSetOfDice = SetOfDiceGenerator.generateAllPossibleFaceValueCombinationsFor(numFacesPerDie, faceValueSum, faceValueLow, faceValueHigh );

        int[][] expectedDice = {
                {-4,-4,8}, {-4,0,4}, {-3,-4,7}, {-3,-3,6}, {-3,-2,5}, {-2,-4,6}, {-2,4,-2}, {-1,-4,5}, {-1,-3,4},
                {-1,-2,3}, {-1,-1,2}, {0,-3,3}, {0,-2,2}, {0,-1,1}, {0,0,0}, {1,-4,3}, {1,-3,2},
                {1,-2,1}, {2,-4,2}, {3,2,-5}, {3,3,-6}, {4,1,-5}, {4,2,-6}, {4,3,-7}, {4,4,-8}
        };


        for (int[] dieFaceValues : expectedDice ) {
            Die d = new Die( dieFaceValues );
            assertTrue( generatedSetOfDice.contains( d ));
        }
        assertEquals("Result has different length than expected result", generatedSetOfDice.size(), expectedDice.length);
    }
}

