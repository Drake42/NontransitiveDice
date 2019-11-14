import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class SetOfDiceGenerator {

    //A. Generate the set of all possible dice for the given parameters
    //In a loop through all possible values for each side,
    //  assign every possible combination of face values to all sides other than one
    //  compute the value for the final side needed to make the sum of face values match that specified.
    public static Set<Die> generateAllPossibleFaceValueCombinationsFor(
            int numFaces, int faceValueSum, int faceValueLow, int faceValueHigh) {

        int numPossibleValuesForAllConstrainedFaces= faceValueHigh - faceValueLow + 1;
        int numConstraintedFaces = numFaces - 1;
        BigInteger numPossibleValuesForAllConstraintedFaces_big = BigInteger.valueOf(numPossibleValuesForAllConstrainedFaces);
        int maxNumCombinations = numPossibleValuesForAllConstraintedFaces_big.pow(numConstraintedFaces).intValue();
        int[][] candidateDice = new int[numFaces][maxNumCombinations];

        //fill first first row of table with the low range value
        //fill next row of table with values incremented by 1 using adder like protocol
        //repeat until done with table

        //fill first row of table, ignoring last column
        for(int column=0; column<numFaces-1; column++) {
            candidateDice[column][0] = faceValueLow;
        }

        //fill all subsequent rows of table, ignoring last column
        for(int row=1; row<maxNumCombinations; row++) {
            boolean carry = true;
            for(int column=0; column<numFaces-1; column++) {
                int valueInRowAbove = candidateDice[column][row-1];
                int valueInThisRow = valueInRowAbove;
                if (carry) {
                    valueInThisRow++;
                }

                if (valueInThisRow > faceValueHigh) {
                    valueInThisRow = faceValueLow;
                    carry = true;
                } else {
                    carry = false;
                }
                candidateDice[column][row] = valueInThisRow;
            }
        }

        //TODO: fill in final row of table


        Set<Die> result = new HashSet<>();

        //TODO: add each combination from the table to the result set

        //TODO: confirm that adding to the set silently removes duplicates

        //TODO: delete the following two lines
        Die d = new Die( new int[]{-4,-4,8} );
        result.add( d );

        return result;
    }
}
