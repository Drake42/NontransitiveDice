import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Combinations {

    static List<List<Die>> GenerateCombinationsOf(int numElementsInResult, List<Die> elements) {
        List<List<Die>> results = GenerateCombinationsOf(elements);
        Iterator<List<Die>> it = results.iterator();
        while( it.hasNext() ) {
            List<Die> combination = it.next();
            if( combination.size()!=numElementsInResult ) {
                it.remove();
            }
        }
        return results;
    }

    //The algorithm here is taken from https://theproductiveprogrammer.blog/GeneratingCombinations.java.php
    static List<List<Die>> GenerateCombinationsOf(List<Die> elements) {
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
