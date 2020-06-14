import java.util.*;


public class SetOfDice {

    private final Set<Die> dice;
    private final Map<Die, Set<Die>> mappings;

    public SetOfDice(Set<Die> dice) {
        this.dice = dice;
        mappings = setupMappingsFromDieToDiceThatBeatIt(this.dice);
    }

    private Map<Die, Set<Die>> setupMappingsFromDieToDiceThatBeatIt(Set<Die> diceToMap) {
        Map<Die, Set<Die>> mappings = new HashMap<>();

        for (Die currentDie : diceToMap) {
            Set<Die> diceThatBeatCurrentDie = currentDie.findDiceThatBeatMeFrom(diceToMap);
            mappings.put(currentDie, diceThatBeatCurrentDie);
        }

        return mappings;
    }

    //Currently tested up through 3 players.
    public boolean isSetOfDiceNontransitiveForNumPlayers(int numPlayers) {
        List<Set<Die>> combinations = Combinations.generateCombinationsOf(numPlayers - 1, dice);

        for (Set<Die> currentCombination : combinations) {
            Set<Die> beatsCurrent = findDiceToBeatAll(currentCombination);
            if (beatsCurrent == null || beatsCurrent.size() < 1) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param alreadyChosenDice
     * @return the subset of this SetOfDice which beat all dice in the specified alreadyChosenDice
     */
    Set<Die> findDiceToBeatAll(Set<Die> alreadyChosenDice) {
        Set<Die> result = new HashSet<>();
        Iterator<Die> it = alreadyChosenDice.iterator();
        if( it.hasNext() ) {
            Die oneChosenDie = it.next();
            Set<Die> diceThatBeatTheOne = findDiceToBeatOneDie( oneChosenDie);
            result = new HashSet<>(diceThatBeatTheOne);
            while( it.hasNext() ) {
                Die nextChosenDie = it.next();
                Set<Die> diceThatBeatTheNextChosen = this.mappings.get(nextChosenDie);
                result.retainAll( diceThatBeatTheNextChosen );
            }
        }
        return result;
    }

    Set<Die> findDiceToBeatOneDie(Die alreadyChosenDie) {
        return this.mappings.get(alreadyChosenDie);
    }

    /**
     * @return a List of Set<Die> such that each returned Set<Die> is nontransitive, and all nontransitive subsets within this were returned
     * Example 1:
     *      If this contains only one die, then there's only one subset, and that subset can't be nontransitive, so we return null.
     *      (similarly, any Set containing less than three dice will result in a null return as no subset can be nontransitive)
     * Example 2:
     *      If this contains dice A, B, C such that
     *          A beats B beats C beats A,
     *          then the returned list will contain exactly set ABC
     * Example 3:
     *      If this contains dice ABCDEF, such that
     *          A beats B beats C beats A,
     *          and also D beats E beats F beats D,
     *          then the returned list will contain set ABC and set DEF
     * Example 4:
     *      If this contains dice ABCDEFH, such that
     *          A beats B beats C beats A,
     *          and also D beats E beats F beats D,
     *          and also H beats C
     *          and also H beats E
     *          then the returned list will contain set ABC and set DEF, and H will not appear in any returned set
     *
     */
    public List<Set<Die>> findAllNontransitiveSubsets() {
        //I want a method like this...
        //List<Set<Die>> findAllSubsetsOf( Set<Die> dice )
        return null; //TODO implement this, as driven by tests already written elsewhere

        /*
            Example set, for purpose of discussing algorithm below
                A beats B beats C beats A
                also B beats D beats A
                also E beats D (on-ramp)
                also C beats F (dead-end off-ramp)
                also G beats nothing and is beat by nothing
                also H beats I beats J beats H
                also F beats J

            Proposed algorithm
                1. Clone this set, because the following algorithm is destructive
                2. Pick any starting element in this workingSet
                3. Prune the graph we can get to from this starting element of off-ramps and on-ramps,
                    because, any of these do not participate in cycles and thus aren't in a nontransitive subset
                    3.1: To do this, traverse the graph from this starting element, maintaining a visited list,
                        and any time we find an element that doesn't lead anywhere else (a deadend offramp),
                        we delete that element from this set and restart the traversal.
                        (The traversal can be backed up easily if we use recursion rather than iteration.)
                    3.2: Whenever we traverse to an element we've already visited, flush the visited list and
                        restart traversal from this point. Because we already visited it, we're guaranteed it is
                        not an onramp. Continue traversing until we again visit this element. When that occurs, our
                        visited list contains the loop, so... take every node in the visited list and extract it from
                        the working set, into a new set, and add that new set to the results list.
                        (This can undesirably break up compound cycles like ABCD above, incorrectly separating
                        either ABC from D or ABD from C. THEREFORE, this algorithm was a nice try, but start over.)
                4. If the working set is now empty, then we're done. If it's not empty, choose a new starting point
                    in the working set, and go back through step 3 again.
         */

    }
}
