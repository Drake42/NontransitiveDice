import java.util.*;

public class Combinations {

    //This method determines all combinations of dice which have exactly numElements elements
    //The return is a list of all such Set<Dice>
    public static List<Set<Die>> generateCombinationsOf(int numElements, Collection<Die> dice) {
        List<Set<Die>> results = generateCombinationsOf(dice);
        removeCombinationsNotMatchingSize(numElements, results);
        return results;
    }

    //The algorithm here is taken from https://theproductiveprogrammer.blog/GeneratingCombinations.java.php
    //and modified to use iterators
    static List<Set<Die>> generateCombinationsOf(Collection<Die> elements) {
        List<Set<Die>> results = new ArrayList<>();
        int numElements = elements.size();
        for(int num=0; num<(1<<numElements); num++) { //iterate from 0 to the max number of combinations, which is 2^numElements
            Set<Die> combination = new HashSet<>();
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

    private static void removeCombinationsNotMatchingSize(int numElementsInResult, List<Set<Die>> results) {
        Iterator<Set<Die>> it = results.iterator();
        while( it.hasNext() ) {
            Set<Die> combination = it.next();
            if( combination.size()!=numElementsInResult ) {
                it.remove();
            }
        }
    }

}
