import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class SetOfDiceGeneratorTest {

    /*
    Generate all unique sets of dice for these constraints:
        3 dice, each with 3 sides
        the sum of face values for all sides of any die is 0
        the range of allowed values for all sides but one of any die is -4 to 4
    Then confirm that the generated set matches our result from hand calculation
        -4	-4	8
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
    //TODO: the following test cannot work until we write .equals for Die
    @Test
    public void testGenerateCase01() {


        Set<Die> generatedSetOfDice;

        int numFacesPerDie = 3;
        int faceValueSum = 0;
        int faceValueLow = -4;
        int faceValueHigh = 4;
        generatedSetOfDice = SetOfDiceGenerator.generateAllPossibleFaceValueCombinationsFor(numFacesPerDie, faceValueSum, faceValueLow, faceValueHigh );

        int[][] expectedDice = {
                {-4,-4,8}, {-3,-4,7}, {-3,-3,6}, {-3,-2,5}, {-2,-4,6}, {-2,4,-2}, {-1,-4,5}, {-1,-3,4},
                {-1,-2,3}, {-1,-1,2}, {0,-3,3}, {0,-2,2}, {0,-1,1}, {0,0,0}, {1,-4,3}, {1,-3,2},
                {1,-2,1}, {2,-4,2}, {3,2,-5}, {3,3,-6}, {4,1,-5}, {4,2,-6}, {4,3,-7}, {4,4,-8}
        };


        for (int[] dieFaceValues : expectedDice ) {
            Die d = new Die( dieFaceValues );
            assertTrue( "Test written, **code not yet written** 'cause this is what to work on next!"+d, generatedSetOfDice.contains( d ));
            //TODO: the above assert fails, and will continue to do so until we finish implementing
            // the method SetOfDiceGenerator.generateAllPossibleFaceValueCombinationsFor...
            // per the TODO items in that method
        }
        assertTrue( "Result has different length than expected result",generatedSetOfDice.size() == expectedDice.length );

    }
}
