import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class SetOfDiceGenerator {

    //An example hand calculation is at https://docs.google.com/spreadsheets/d/1VSwHnrMz5Oa-ZO9grq8vIIFW-Haf8TSwCq9Bi6GCo0k/edit#gid=0.
    //That example builds a table with the same algorithm we do here
    //This method returns a set of Dice, containing every possible Die for the given numFaces, and low/high faceValues
    //Note that one face of each die (last column of table) is not constrained by the low/high faceValues, but by the required faceValueSum
    public static Set<Die> generateAllPossibleFaceValueCombinationsFor(
            int numFaces, int faceValueSum, int faceValueLow, int faceValueHigh) {

        int numTableRows = computeMaxPossibleCombinationsFor(numFaces, faceValueLow, faceValueHigh);
        int numTableColumns = numFaces;
        int[][] candidateDiceTable = new int[numTableRows][numTableColumns];

        fillFirstRowOfTableWithLowestValueButIgnoreLastColumn(faceValueLow, candidateDiceTable);
        fillSubsequentRowsOfTableWithPossibleFaceValueCombinationsButIgnoreLastColumn(faceValueLow, faceValueHigh, candidateDiceTable);
        fillInLastColumnOfTableSoThatFacesSumTo(faceValueSum, candidateDiceTable);

        Set<Die> result = getSetOfDiceWithoutDuplicates(candidateDiceTable);

        return result;
    }

    private static int computeMaxPossibleCombinationsFor(int numFaces, int faceValueLow, int faceValueHigh) {
        int numConstrainedFaces = numFaces - 1;
        BigInteger numFaceValuesInRange = BigInteger.valueOf(faceValueHigh - faceValueLow + 1);
        return numFaceValuesInRange.pow(numConstrainedFaces).intValue();
    }

    private static Set<Die> getSetOfDiceWithoutDuplicates(int[][] candidateDiceTable) {
        Set<Die> result = new HashSet<>();
        for(int row=0; row<candidateDiceTable.length; row++) {
            boolean add = result.add(new Die(candidateDiceTable[row]));
        }
        return result;
    }

    private static void fillInLastColumnOfTableSoThatFacesSumTo(int faceValueSum, int[][] diceTable) {
        for(int row=0; row<diceTable.length; row++) {
            int lastColumn = diceTable[row].length - 1;
            int sum = 0;
            for(int column=0; column<lastColumn; column++) {
                sum += diceTable[row][column];
            }
            diceTable[row][lastColumn] = faceValueSum - sum;
        }
    }

    private static void fillSubsequentRowsOfTableWithPossibleFaceValueCombinationsButIgnoreLastColumn(int faceValueLow, int faceValueHigh, int[][] diceTable) {
        for(int row=1; row<diceTable.length; row++) {
            boolean carry = true;
            for(int column=0; column<diceTable[row].length-1; column++) {
                int valueInRowAbove = diceTable[row-1][column];
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
                diceTable[row][column] = valueInThisRow;
            }
        }
    }

    private static void fillFirstRowOfTableWithLowestValueButIgnoreLastColumn(int valueToFillIn, int[][] diceTable) {
        for(int column=0; column<diceTable[0].length-1; column++) {
            diceTable[0][column] = valueToFillIn;
        }
    }
}
