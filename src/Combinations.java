import java.util.*;

public class Combinations {


    //TODO: #2 The caller actually iterates through the List of List<Die>, converting each to a Set<Die>, so change this to return List<Set<Die>>
    //The reason it's currently passing a List<Die> is because we test drove to get here, but then realized we'd
    //built the wrong client interface and we chose to move the client toward Set rather than List.
    public static List<List<Die>> generateCombinationsOf(int numCombinations, Set<Die> diceToMap) {
        return generateCombinationsOf_NEWSIG(numCombinations - 1, diceToMap);
    }

    //This method is a helper to enable old tests to still call the new version of this method which
    // requires a parameter of type Set<Die> rather than List<Die>
    //  TODO: refactor those tests to the new signature, then delete this method.
    static List<List<Die>> generateCombinationsOf(int numElementsInResult, Collection<Die> elements) {
        return generateCombinationsOf_NEWSIG(numElementsInResult, elements);
    }


    private static List<List<Die>> generateCombinationsOf_NEWSIG(int numElementsInResult, Collection<Die> elements) {
        List<List<Die>> results = generateCombinationsOf(elements);
        removeCombinationsNotMatchingSize(numElementsInResult, results);
        return results;
    }


    //To support change item #1 above, this signature must change from List<Die> to Set<Die>
    private static void removeCombinationsNotMatchingSize(int numElementsInResult, List<List<Die>> results) {
        Iterator<List<Die>> it = results.iterator();
        while( it.hasNext() ) {
            List<Die> combination = it.next();
            if( combination.size()!=numElementsInResult ) {
                it.remove();
            }
        }
    }

    //The algorithm here is taken from https://theproductiveprogrammer.blog/GeneratingCombinations.java.php
    //and modified to use iterators
    static List<List<Die>> generateCombinationsOf(Collection<Die> elements) {

        List<List<Die>> results = new ArrayList<>();
        int numElements = elements.size();

        for(int num=0; num<(1<<numElements); num++) { //iterate from 0 to the max number of combinations, which is 2^numElements
            List<Die> combination = new ArrayList<>();

            Iterator<Die> it = elements.iterator();
            int idx = 0;
            while (it.hasNext()) {
                Die currentDie = it.next();
                //for the combination we're working on, numbered "num", do we include element number idx?
                if((num&(1<<idx)) != 0) { //then include it in this combination
                    combination.add( currentDie );
                }
                idx++;
            }

            results.add(combination);
        }
        return results;
    }


}
