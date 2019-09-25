import java.util.*;

public class Combinations {


    public static List<List<Die>> generateCombinationsOf(int numCombinations, Set<Die> diceToMap) {
        return generateCombinationsOf(numCombinations - 1, new ArrayList<>(diceToMap));
    }

    static List<List<Die>> generateCombinationsOf(int numElementsInResult, List<Die> elements) {
        List<List<Die>> results = generateCombinationsOf(elements);
        Iterator<List<Die>> it = results.iterator();
        while( it.hasNext() ) {
            List<Die> combination = it.next();
            if( combination.size()!=numElementsInResult ) {
                it.remove();
            }
        }
        return results;
    }

    //TODO: can we decouple the algorithm here from List, change the parameter to Collection...
    // ... and eliminate the helper method above that's currently needed to call it with a Set
    //The algorithm here is taken from https://theproductiveprogrammer.blog/GeneratingCombinations.java.php
    static List<List<Die>> generateCombinationsOf(List<Die> elements) {
        List<List<Die>> results = new ArrayList<>();
        int numElements = elements.size();
        for(int num=0; num<(1<<numElements); num++) { //iterate from 0 to the max number of combinations, which is 2^numElements
            List<Die> combination = new ArrayList<>();
            for(int idx=0; idx<numElements; idx++) {
                //for the combination we're working on, numbered "num", do we include element number idx?
                if((num&(1<<idx)) != 0) { //then include it in this combination
                    combination.add(elements.get(idx));
                }
            }
            results.add(combination);
        }
        return results;
    }


}
