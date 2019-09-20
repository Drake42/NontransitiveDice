import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


/*
We require a set of dice, containing at least 3 members
Each die has at least 3 sides, we're using NUM_SIDES=3 for starters
The set is nontransitive if probabilities are that more than 50% of the time A>B, and B>C, and C>A (yup, not backwards)
Example:
A has sides 2, 4, 9
B has sides 1, 6, 8
C has sides 3, 5, 7
It may be relevant, for fairness, that the sum of sides is identical for each die.

Done: Goal 0: determine whether a given set of 3 dice is nontransitive for 2 players
This was completed with the method (and associated tests and supporting methods)
    private boolean isSetOfDiceNontransitiveForTwoPlayers(List setOfDice) {

TODO: This is the highest level of context for what we're currently working on as of 6 Sep 2019
NEXT: Goal 1: determine whether a given set of dice is nontransitive, for a given
        set of dice
        number of players
In other words, generalize Goal 0 to work with an arbitrary number of players.

Goal 2: generate nontransitive die sets, for a given
        number of dice
        given number of sides per die
        given total of face values?
        given lower and upper bounds on face values?

 Goal 3: generate nontransitive die sets, for a given
        number of players, which will ultimately determine the required number of dice
        and possibly the same side and face value related data above


 */


public class NontransitiveDiceTest {

    @Test
    public void testNontransitiveTwoPlayerSimpleSet() {

        List setOfDice = new ArrayList(3);
        setOfDice .add(new Die( new int[]{0, 0, 0} ));
        setOfDice .add(new Die( new int[]{1, 1, -2} ));
        setOfDice .add(new Die( new int[]{-1, -1, 2} ));
        assertTrue( isSetOfDiceNontransitiveForNumPlayers( setOfDice, 2) );
    }


    @Test
    public void testFilterUnchosenFrom_withNullParameters() {
        List<Die> setOfDice = null;
        Die[] selectedDieForEachPlayerWhosChosen = null;
        try {
            List unchosenDice = filterUnchosenFrom(setOfDice, selectedDieForEachPlayerWhosChosen);
            fail("filterUnchosenFrom method should have thrown an exception due to null parameters.");
        } catch (Exception ex) {
            //deliberate NOP, as exception is expected
        }
    }

    @Test
    public void testFilterUnchosenFrom_withNoDiceChosen() {
        List<Die> setOfDice = null;
        Die[] selectedDieForEachPlayerWhosChosen = null;

        Die die1 = new Die( new int[]{0, 0, 0} );
        Die die2 = new Die( new int[]{0, 0, 0} );
        Die die3 = new Die( new int[]{0, 0, 0} );
        setOfDice = new ArrayList<Die>();
        setOfDice.add(die1);
        setOfDice.add(die2);
        setOfDice.add(die3);

        selectedDieForEachPlayerWhosChosen = new Die[5];

        List unchosenDice = filterUnchosenFrom( setOfDice, selectedDieForEachPlayerWhosChosen );

        assertTrue( unchosenDice.contains( die1 ) );
        assertTrue( unchosenDice.contains( die2 ) );
        assertTrue( unchosenDice.contains( die3 ) );

    }

    @Test
    public void testFilterUnchosenFrom_withSomeDiceChosen() {
        List<Die> setOfDice = null;
        Die[] selectedDieForEachPlayerWhosChosen = null;

        Die die1 = new Die( new int[]{0, 0, 0} );
        Die die2 = new Die( new int[]{0, 0, 0} );
        Die die3 = new Die( new int[]{0, 0, 0} );
        setOfDice = new ArrayList<Die>();
        setOfDice.add(die1);
        setOfDice.add(die2);
        setOfDice.add(die3);

        selectedDieForEachPlayerWhosChosen = new Die[5];
        selectedDieForEachPlayerWhosChosen[0] = die1;
        selectedDieForEachPlayerWhosChosen[1] = die2;

        List unchosenDice = filterUnchosenFrom( setOfDice, selectedDieForEachPlayerWhosChosen );

        assertFalse(unchosenDice.contains(die1));
        assertFalse(unchosenDice.contains(die2));
        assertTrue( unchosenDice.contains( die3 ) );

    }

    //TODO: This doesn't belong in the test class.
    private List<Die> filterUnchosenFrom(List<Die> setOfDice, Die[] selectedDieForEachPlayerWhosChosen) {
        List<Die> result = new ArrayList<Die>( setOfDice );
        int index = 0;
        while( index<selectedDieForEachPlayerWhosChosen.length && selectedDieForEachPlayerWhosChosen[index] != null ) {
            result.remove( selectedDieForEachPlayerWhosChosen[index]);
            index++;
        }
        return result;
    }

    /* TODO: The following method doesn't belong in the test class. */

    //TODO: This is the method in which we are working to realize Goal #1 (see comment at top)
    //TODO: Although this method takes a parameter for numPlayers, the logic doesn't yet use it and is hard coded to two players
    //  to do that, first modify the logic to remove the hard coding to two players, while keeping tests working
    //  then test with three players
    // TODO: DO THIS SECOND ^^^
    // TODO: AND THE THIRD THING TO DO IS PROBABLY TO REVISIT ALL THE COMMENTS ABOUT METHODS NOT BELONGING IN TEST CLASS

    /* Thinking about how to make that work...
    //Given an arbitrary number of players, we'll end up nesting the loops below as deeply as needed...
    //Each player iterates across all the dice, choosing one that no prior player has chosen,
    // and verifying their choice can win

    //So now our outer loop must iterate across the num players, and we'll keep a list or array that holds
    // each players iterator across the set of dice

    //Except now that we've built infrastructure for a set based approach, maybe all those loops aren't needed.
    */

    // TODO: current implementation iterates & checks all... shift to new mappings based stuff by calling...
    //  ...either   private Set<Die> findDiceToBeatAll(Set<Die> alreadyChosenDice, Map<Die, Set<Die>> mappings)
    //  ...or       private Set<Die> findDiceToBeatOneDie(Die alreadyChosenDie, Map<Die, Set<Die>> mappings)
    // TODO: DO THIS FIRST ^^^ (noted on 6 Sep 2019) ARJ START HERE
    // TODO: When beginning to change this implementation to use the new mappings stuff, be aware that we already
    //  successfully made the same change in the method isSetOfDiceNontransitiveForTwoPlayers down below.

    public void testWithLessDiceThanPlayers() {
        List<Die> setOfDice;

        setOfDice = new ArrayList<>();
        assertFalse( isSetOfDiceNontransitiveForNumPlayers(setOfDice, 1) );

        setOfDice = new ArrayList<>( Arrays.asList( new Die[] {new Die( new int[]{1, 1, -2} )} ) );
        assertFalse( isSetOfDiceNontransitiveForNumPlayers(setOfDice, 2) );

        setOfDice = new ArrayList<Die>(Arrays.asList(allOskarDice));
        assertFalse( isSetOfDiceNontransitiveForNumPlayers(setOfDice, 20) );
    }

    //for N players, Nontransitive means...
    // for any possible set of (n-1) dice, there exists at least one die that beats all of them.
    private boolean isSetOfDiceNontransitiveForNumPlayers(List setOfDice, int numPlayers) {

        Map<Die, Set<Die>> mappings = setupMappingsFor(setOfDice);

        List<List<Die>> allCombinationsOfNMinusOneDice = Combinations.GenerateCombinationsOf(numPlayers-1, setOfDice);
        for(Iterator<List<Die>> it=allCombinationsOfNMinusOneDice.iterator(); it.hasNext(); ) {
            List<Die> combination = it.next();
            Set<Die> chosenDice = new HashSet<>( combination );
            Set<Die> beatsCurrent = findDiceToBeatAll(chosenDice, mappings);
            if( beatsCurrent==null || beatsCurrent.size()<1 ) {
                return false;
            }
        }
        return true;
    }


    /* TODO: The following method doesn't belong in the test class. */

    /*
    // For purposes of this method, our definition of a player winning is in the example here...
    // https://en.wikipedia.org/wiki/Nontransitive_dice#Nontransitive_dice_set_for_more_than_two_players
    */
    private boolean thisPlayerWinsAgainstPriorPlayers(Iterator<Die> currentPlayerDieChoices, Die[] selectedDieForEachPlayerWhosChosen) {
        boolean result = false;

        while(currentPlayerDieChoices.hasNext() && !result) {
            Die proposedDieForCurrentPlayer = currentPlayerDieChoices.next();

            if (proposedDieForCurrentPlayer.beatsAllOfThese(selectedDieForEachPlayerWhosChosen)) {
                result = true;
            }
        }
        return result;
    }


    @Test
    public void testSeveralNontransitiveSimpleSets() {

        testNontransitiveForTheseThreeDice(
                new Die( new int[]{0, 0, 0} ),
                new Die( new int[]{1, 1, -2} ),
                new Die( new int[]{-1, -1, 2} ) );

        testNontransitiveForTheseThreeDice(
                new Die( new int[]{2, 4, 9} ),
                new Die( new int[]{1, 6, 8} ),
                new Die( new int[]{3, 5, 7} ) );

        // TODO: Nathan, copy and modify the above to add other known non-transitive sets (for 3 dice and 2 players)

    }

    private void testNontransitiveForTheseThreeDice(Die d1, Die d2, Die d3) {
        List setOfDice = new ArrayList(3);
        setOfDice .add(d1);
        setOfDice .add(d2);
        setOfDice .add(d3);
        assertTrue( isSetOfDiceNontransitiveForTwoPlayers( setOfDice ) );
    }

    @Test
    public void testNotNontransitiveSimpleSet() {
        Die d1, d2, d3;
        d1 = new Die( new int[]{0, 0} );
        d2 = new Die( new int[]{1, 1} );
        d3 = new Die( new int[]{-1, -1} );

        List setOfDice = new ArrayList(3);
        setOfDice .add(d1);
        setOfDice .add(d2);
        setOfDice .add(d3);
        assertFalse( isSetOfDiceNontransitiveForTwoPlayers( setOfDice ) );
    }


    /* TODO: The following method doesn't belong in the test class. */
    // BUT - we'd like to generalize this to test for more than Two players...
    // ...and that's happening above in "isSetOfDiceNontransitiveForNumPlayers"
    // TODO: as soon as the above mentioned new method is working, reduce this method to simply calling the new one
    private boolean isSetOfDiceNontransitiveForTwoPlayers(List setOfDice) {

        Map<Die, Set<Die>> mappings = setupMappingsFor(setOfDice);

        Iterator<Die> it = setOfDice.iterator();
        while( it.hasNext() ) {
            Die current = it.next();
            Set<Die> beatsCurrent = findDiceToBeatOneDie(current, mappings);
            if (null==beatsCurrent || beatsCurrent.isEmpty()) {
                return false;
            }
        }
        return true;

    }

    private Map<Die, Set<Die>> setupMappingsFor(List setOfDice) {
        Die[] diceToMap = (Die[]) setOfDice.toArray( new Die[ setOfDice.size()] );
        Map<Die, Set<Die>> mappings;
        mappings = setupMappingsFromDieToDiceThatBeatIt(diceToMap);
        return mappings;
    }

    @Test
    public void testBeats() {
        Die die1 = new Die( new int[]{1} );
        Die die2 = new Die( new int[]{2} );

        assertTrue( die2.beats( die1 ));
        assertFalse( die1.beats( die2 ));

        assertFalse( die1.beats( die1 ));
        assertFalse( die1.beats( new Die( new int[]{1} ) ));


        Die die11 = new Die( new int[]{1, 1} );
        assertTrue( die2.beats(die11) );


        Die die21 = new Die( new int[]{2, 1} );
        assertFalse( die21.beats( die11 ));

        Die die12 = new Die( new int[]{1, 2} );
        assertFalse( die21.beats( die12 ));


        Die die221 = new Die( new int[]{2, 2, 1} );
        assertTrue( die221.beats( die11 ));

        Die die211 = new Die( new int[]{2, 1, 1} );
        assertFalse( die221.beats( die211 ));

        Die die111 = new Die( new int[]{1, 1, 1} );
        assertTrue( die221.beats( die111) );

    }


    /*
    // This is a temporary comment... delete it once the described division is eliminated...
    // Stuff above here is from the original iterative approach (see comment above method isSetOfDiceNontransitiveForNumPlayers)
    // Stuff below here is the new mappings based approach that Nathan suggested
    // After we alter isSetOfDiceNontransitiveForNumPlayers to use this mappings based stuff, then the distinction
    // between above/below sections will disappear.
     */

    //Per wikipedia article on nontransitive dice (TODO INSERT REFERENCE HERE to wikipedia article)
    //the following set of dice, discovered by Oskar, have these characteristics
    //A beats {B,C,E};
    //B beats {C,D,F};
    //C beats {D,E,G};
    //D beats {A,E,F};
    //E beats {B,F,G};
    //F beats {A,C,G};
    //G beats {A,B,D}.
    private static Die oskarDiceA = new Die( new int[]{2, 2, 14, 14, 17, 17} );
    private static Die oskarDiceB = new Die( new int[]{7, 7, 10, 10, 16, 16} );
    private static Die oskarDiceC = new Die( new int[]{5, 5, 13, 13, 15, 15} );
    private static Die oskarDiceD = new Die( new int[]{3, 3, 9, 9, 21, 21} );
    private static Die oskarDiceE = new Die( new int[]{1, 1, 12, 12, 20, 20} );
    private static Die oskarDiceF = new Die( new int[]{6, 6, 8, 8, 19, 19} );
    private static Die oskarDiceG = new Die( new int[]{4, 4, 11, 11, 18, 18} );
    private static final Die[] allOskarDice = new Die[] {oskarDiceA, oskarDiceB, oskarDiceC, oskarDiceD, oskarDiceE, oskarDiceF, oskarDiceG};

    private Map<Die, Set<Die>> setupOskarDiceMappingsFromDieToDiceThatBeatIt() {
        return setupMappingsFromDieToDiceThatBeatIt(allOskarDice);
    }

    private Map<Die, Set<Die>> setupMappingsFromDieToDiceThatBeatIt(Die[] diceToMap) {
        Set<Die> setOfDiceToMap = new HashSet<Die>(Arrays.asList( diceToMap ));
        Map<Die, Set<Die>> mappings = new HashMap<Die, Set<Die>>();

        for(Iterator<Die> it=setOfDiceToMap.iterator(); it.hasNext(); ) {
            Die currentDie = it.next();
            Set<Die> diceThatBeatCurrentDie = currentDie.findDiceThatBeatMeFrom(setOfDiceToMap);
            mappings.put(currentDie, diceThatBeatCurrentDie );
        }

        return mappings;
    }

    @Test
    public void testBeatsAllOfThese() {
        assertTrue( oskarDiceA.beatsAllOfThese(new Die[]{oskarDiceB, oskarDiceC, oskarDiceE}));
        assertFalse( oskarDiceA.beatsAllOfThese(new Die[]{oskarDiceB, oskarDiceC, oskarDiceD}));
        assertFalse( oskarDiceA.beatsAllOfThese(new Die[]{oskarDiceD, oskarDiceC, oskarDiceE}));
    }

    @Test
    public void testFindDiceToBeatOneDie() {
        Map<Die, Set<Die>> mapDieToDiceThatBeatIt = setupOskarDiceMappingsFromDieToDiceThatBeatIt();

        Set<Die> result;
        result = findDiceToBeatOneDie(oskarDiceA, mapDieToDiceThatBeatIt);
        assertTrue( result.contains( oskarDiceD ));
        assertTrue( result.contains( oskarDiceF ));
        assertTrue( result.contains( oskarDiceG ));
        assertTrue( result.size() == 3 );

        result = findDiceToBeatOneDie(oskarDiceD, mapDieToDiceThatBeatIt);
        assertTrue( result.contains( oskarDiceB ));
        assertTrue( result.contains( oskarDiceC ));
        assertTrue( result.contains( oskarDiceG ));
        assertTrue( result.size() == 3 );
    }

    @Test
    public void testFindDiceToBeatAll() {
        Map<Die, Set<Die>> mappings = setupOskarDiceMappingsFromDieToDiceThatBeatIt();

        Set<Die> result;

        Set<Die> alreadyChosenDice;
        alreadyChosenDice = new HashSet<>();
        alreadyChosenDice.add( oskarDiceA );
        alreadyChosenDice.add( oskarDiceD );
        result = findDiceToBeatAll( alreadyChosenDice, mappings );
        assertTrue( result.size() == 1 );
        assertTrue( result.contains( oskarDiceG ));

        alreadyChosenDice = new HashSet<>();
        alreadyChosenDice.add( oskarDiceA );
        alreadyChosenDice.add( oskarDiceB );
        result = findDiceToBeatAll( alreadyChosenDice, mappings );
        assertTrue( result.size() == 1 );
        assertTrue( result.contains( oskarDiceG ));
    }

    /* TODO: DOESN'T BELONG IN TEST CLASS */
    private Set<Die> findDiceToBeatAll(Set<Die> alreadyChosenDice, Map<Die, Set<Die>> mappings) {
        Set<Die> result = new HashSet<>();
        Iterator<Die> it = alreadyChosenDice.iterator();
        if( it.hasNext() ) {
            Die oneChosenDie = it.next();
            Set<Die> diceThatBeatTheOne = findDiceToBeatOneDie( oneChosenDie, mappings);
            result = new HashSet<>(diceThatBeatTheOne);
            while( it.hasNext() ) {
                Die nextChosenDie = it.next();
                Set<Die> diceThatBeatTheNextChosen = findDiceToBeatOneDie( nextChosenDie, mappings);
                result.retainAll( diceThatBeatTheNextChosen );
            }
        }
        return result;
    }

    /* TODO: DOESN'T BELONG IN TEST CLASS */
    private Set<Die> findDiceToBeatOneDie(Die alreadyChosenDie, Map<Die, Set<Die>> mappings) {
        return mappings.get( alreadyChosenDie );
    }

}
