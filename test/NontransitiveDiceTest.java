import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class NontransitiveDiceTest {
    @Test
    public void testFoo() {
        assertEquals(1, 1);
        assertTrue(true);
        assertFalse(false);

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

        NEXT: Goal 1: determine whether a given set of dice is nontransitive, for a given
                set of dice
                number of players

        Goal 2: generate nontransitive die sets, for a given
                number of dice
                given number of sides per die
                given total of face values?
                given lower and upper bounds on face values?


         Goal 3: generate nontransitive die sets, for a given
                number of players, which will ultimately determine the required number of dice
                and possibly the same side and face value related data above


         */

        /*
        A has sides 2, 4, 9
        B has sides 1, 6, 8
        C has sides 3, 5, 7
        */
        /*
        public void testIsNontransitiveForThreeDiceAndTwoPlayers() {
            Die d1 = new Die(2, 4, 9);
            Die d2 = new Die(1, 6, 8);
            Die d3 = new Die(3, 5, 7);
            Die d4 = new Die(3, 4, 8);

            assertTrue( isNontransitiveForTwoPlayers(d1, d2, d3) );
            assertFalse( isNontransitiveForTwoPlayers(d1, d2, d4) );
        }
        */
    }


    // Goal 1: determine whether a given set of dice is nontransitive for a given number of players
    // The following test is still for three dice and two players, but I'll use it to evolve the working method that's
    // hard coded to two players into one that accepts a number of players.
    // Once we make a general method work for the existing test case, we'll add one that actually uses three players.
    @Test
    public void testNontransitiveTwoPlayerSimpleSet() {

        List setOfDice = new ArrayList(3);
        setOfDice .add(new Die( new int[]{0, 0, 0} ));
        setOfDice .add(new Die( new int[]{1, 1, -2} ));
        setOfDice .add(new Die( new int[]{-1, -1, 2} ));
        assertTrue( isSetOfDiceIsNontransitiveForNumPlayers( setOfDice, 2) );
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





    //TODO: This doesn't belong in the test class.
    /*
    //We have an arbitrary number of players now...
    //First make it work for two players
    //Then test with three players
    //Then go back and test the cases with less than two players

    //Given an arbitrary number of players, we'll end up nesting the loops below as deeply as needed...
    //Each player iterates across all the dice, choosing one that no prior player has chosen,
    // and verifying their choice can win

    //So now our outer loop must iterate across the num players, and we'll keep a list or array that holds
    // each players iterator across the set of dice


    */
    /*
    //TODO ... NO, DON'T START AT THE TWO START HERE'S BELOW...
    //      instead, start near a comment near the end "// TODO: this *might* be where we are..."
    //TODO <==== START HERE
    //The new methods at the bottom should make it easy to rewrite this method to work with arbitrary numbes of players
    //TODO <=== an alternate place to start is...
    //for the method at the bottom that uses mappings...
    //we'll eventually need to be able to create our own mappings, rather than hard coding the Oskar set
    //So...
    //write, via TDD, a method like this
    // Map<Die, Set<Die>> computeMappingsFor( Set<Die> allDice );
    */
    private boolean isSetOfDiceIsNontransitiveForNumPlayers(List setOfDice, int numPlayers) {
        Die[] selectedDieForEachPlayerWhosChosen = new Die[numPlayers];
        Iterator<Die> player1Dice = setOfDice.iterator();
        while(player1Dice.hasNext()) {
            Die player1Die = player1Dice.next();
            selectedDieForEachPlayerWhosChosen[0] = player1Die;

            Iterator<Die> unchosenDiceIterator = filterUnchosenFrom( setOfDice, selectedDieForEachPlayerWhosChosen ).iterator();

            //Die choice = chooseDieSoThisPlayerWinsAgainstPriorPlayers( ... )
            if (!thisPlayerWinsAgainstPriorPlayers(unchosenDiceIterator, selectedDieForEachPlayerWhosChosen))
                return false;
        }
        return true;
    }

    /*
    //TODO: This doesn't belong in the test class.
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

    // Goal 0: determine whether a given set of 3 dice is nontransitive for 2 players
    @Test
    public void testNontransitiveSimpleSet() {
//
//        testNontransitiveForTheseThreeDice(
//                new Die( new int[]{0, 0, 0} ),
//                new Die( new int[]{1, 1, -2} ),
//                new Die( new int[]{-1, -1, 2} ) );
//
//        // TODO: Nathan, modify following to add other known non-transitive sets (for 3 dice and 2 players)
//        testNontransitiveForTheseThreeDice(
//                new Die( new int[]{2, 4, 9} ),
//                new Die( new int[]{1, 6, 8} ),
//                new Die( new int[]{3, 5, 7} ) );


/*
//        testNontransitiveForThisDiceList(
//                new Die( new int[]{4, 4, 4, 4, 0, 0} ),
//                new Die( new int[]{3, 3, 3, 3, 3, 3} ),
//                new Die( new int[]{6, 6, 2, 2, 2, 2} ),
//                new Die( new int[]{5, 5, 5, 1, 1, 1} ) );
*/
    }

/*
//    private void testNontransitiveForThisDiceList(Die d1, Die d2, Die d3) {
//        List setOfDice = new ArrayList(3);
//        setOfDice .add(d1);
//        setOfDice .add(d2);
//        setOfDice .add(d3);
//        assertTrue( isSetOfDiceIsNontransitiveForTwoPlayers( setOfDice ) );
//    }
*/

    /*
    //These might be helpful:
    //https://javarevisited.blogspot.com/2012/12/how-to-initialize-list-with-array-in-java.html
    //http://www.java67.com/2015/10/how-to-declare-arraylist-with-values-in-java.html
    */

    private void testNontransitiveForTheseThreeDice(Die d1, Die d2, Die d3) {
        List setOfDice = new ArrayList(3);
        setOfDice .add(d1);
        setOfDice .add(d2);
        setOfDice .add(d3);
        assertTrue( isSetOfDiceIsNontransitiveForTwoPlayers( setOfDice ) );
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
        assertFalse( isSetOfDiceIsNontransitiveForTwoPlayers( setOfDice ) );
    }


    //TODO: This doesn't belong in the test class.
    private boolean isSetOfDiceIsNontransitiveForTwoPlayers(List setOfDice) {
        Iterator<Die> player1Dice = setOfDice.iterator();
        while(player1Dice.hasNext()) {
            Die player1Die = player1Dice.next();
            Iterator<Die> player2Dice = setOfDice.iterator();
            boolean player2HasWonAginstThisPlayer1Die = false;
            while(player2Dice.hasNext() && !player2HasWonAginstThisPlayer1Die) {
                Die player2Die = player2Dice.next();
                if (player2Die == player1Die)
                    continue;
                if (player2Die.beats(player1Die))
                    player2HasWonAginstThisPlayer1Die = true;
            }
            if (!player2HasWonAginstThisPlayer1Die)
                return false;
        }
        return true;
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
    // TODO: this *might* be where we are...
    // we're trying to reconstruct where we were before A's trip
    // we had started to refactor a method above to attempt to make it work for an arbitrary number of players
    // (to find that, search for TODO and Goal)
    // but N had an idea for a set based approach which we worked out on paper and decided was better
    // the stuff below *might* be the beginnings of us implementing that better approach
    // DECIDE: is that true...
    // If it's true, then complete code below to the point that it can service the requests of existing tests so we
    // can then delete the original approach code.

    // So what do we need to do to complete the code below? At least these two things...
    // 1.  Write a replacement for the existing method (above),
    //      "private boolean isSetOfDiceIsNontransitiveForNumPlayers(List setOfDice, int numPlayers) {"
    //      which is implemented using the technology below, specifically testFindDiceToBeatOneDie and testFindDiceToBeatAll
    // 2. In the course of doing #1, it's likely we'll need to write a replacement for the existing method...
    //      "private Map<Die, Set<Die>> setupOskarDiceMappingsFromDieToDiceThatBeatIt() {"
    //      that instead of hard coding mappings, and hard coding them for Oskar Dice, it computes them for any set of dice

    // 3. Refactor so all existing tests which currently call the existing method...
    //      "private boolean isSetOfDiceIsNontransitiveForNumPlayers(List setOfDice, int numPlayers) {"
    //      now work with the new method

    //Per wikipedia article on nontransitive dice INSERT REFERENCE HERE
    //the following set of dice, discovered by Oskar, have these characteristics
    //A beats {B,C,E};
    //B beats {C,D,F};
    //C beats {D,E,G};
    //D beats {A,E,F};
    //E beats {B,F,G};
    //F beats {A,C,G};
    //G beats {A,B,D}.
    */
    private static Die oskarDiceA = new Die( new int[]{2, 2, 14, 14, 17, 17} );
    private static Die oskarDiceB = new Die( new int[]{7, 7, 10, 10, 16, 16} );
    private static Die oskarDiceC = new Die( new int[]{5, 5, 13, 13, 15, 15} );
    private static Die oskarDiceD = new Die( new int[]{3, 3, 9, 9, 21, 21} );
    private static Die oskarDiceE = new Die( new int[]{1, 1, 12, 12, 20, 20} );
    private static Die oskarDiceF = new Die( new int[]{6, 6, 8, 8, 19, 19} );
    private static Die oskarDiceG = new Die( new int[]{4, 4, 11, 11, 18, 18} );

    @Test
    public void testBeatsAllOfThese() {
        assertTrue( oskarDiceA.beatsAllOfThese(new Die[]{oskarDiceB, oskarDiceC, oskarDiceE}));
        assertFalse( oskarDiceA.beatsAllOfThese(new Die[]{oskarDiceB, oskarDiceC, oskarDiceD}));
        assertFalse( oskarDiceA.beatsAllOfThese(new Die[]{oskarDiceD, oskarDiceC, oskarDiceE}));
    }

    @Test
    public void testFindDiceToBeatOneDie() {
        Map<Die, Set<Die>> mappings;
        mappings = setupOskarDiceMappingsFromDieToDiceThatBeatIt();

        Set<Die> result;
        result = findDiceToBeatOneDie(oskarDiceA, mappings);
        assertTrue( result.contains( oskarDiceD ));
        assertTrue( result.contains( oskarDiceF ));
        assertTrue( result.contains( oskarDiceG ));
        assertTrue( result.size() == 3 );

        result = findDiceToBeatOneDie(oskarDiceD, mappings);
        assertTrue( result.contains( oskarDiceB ));
        assertTrue( result.contains( oskarDiceC ));
        assertTrue( result.contains( oskarDiceG ));
        assertTrue( result.size() == 3 );

    }

    @Test
    public void testFindDiceToBeatAll() {
        Map<Die, Set<Die>> mappings;
        mappings = setupOskarDiceMappingsFromDieToDiceThatBeatIt();

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

    /*
    // 3. Refactor so all existing tests which currently call the existing method...
    //      "private boolean isSetOfDiceIsNontransitiveForNumPlayers(List setOfDice, int numPlayers) {"
    //      now work with the new method


    //Per wikipedia article on nontransitive dice INSERT REFERENCE HERE
    //the following set of dice, discovered by Oskar, have these characteristics
    //A beats {B,C,E};
    //B beats {C,D,F};
    //C beats {D,E,G};
    //D beats {A,E,F};
    //E beats {B,F,G};
    //F beats {A,C,G};
    //G beats {A,B,D}.
    */

/*
//    private static Die oskarDiceA = new Die( new int[]{2, 2, 14, 14, 17, 17} );
//    private static Die oskarDiceB = new Die( new int[]{7, 7, 10, 10, 16, 16} );
//    private static Die oskarDiceC = new Die( new int[]{5, 5, 13, 13, 15, 15} );
//    private static Die oskarDiceD = new Die( new int[]{3, 3, 9, 9, 21, 21} );
//    private static Die oskarDiceE = new Die( new int[]{1, 1, 12, 12, 20, 20} );
//    private static Die oskarDiceF = new Die( new int[]{6, 6, 8, 8, 19, 19} );
//    private static Die oskarDiceG = new Die( new int[]{4, 4, 11, 11, 18, 18} );
*/


    @Test
    public void testSetupDiceMappingsFromDieToDiceThatBeatIt() {

        Die testDieA = new Die( new int[]{2, 2, 14, 14, 17, 17} );
        Die testDieB = new Die( new int[]{7, 7, 10, 10, 16, 16} );
        assertTrue( testDieA.beats(testDieB) );

        Set<Die> diceToMap = new HashSet<>();
        diceToMap.add( testDieA );
        diceToMap.add( testDieB );
        Map<Die, Set<Die>> testMap = setupOskarDiceMappingsFromDieToDiceThatBeatIt();
        Set<Die> result = testMap.get(testDieA);
//        assertEquals( 1, result.size() ); //TODO: Fails now... impliment that method below instead of returning null
//        assertTrue( result.contains( testDieB ));
//        //THIS IS NEW STUFF INSERTED TO TEST GIT SYNCHRONIZATION

    }


    private Map<Die, Set<Die>> setupOskarDiceMappingsFromDieToDiceThatBeatIt(Set<Die> diceToMap) {
        return null;
    }

    private Map<Die, Set<Die>> setupOskarDiceMappingsFromDieToDiceThatBeatIt() {
        Map<Die, Set<Die>> mappings;
        mappings = new HashMap<Die, Set<Die>>();
        Set<Die> stuff;
        stuff = new HashSet<>();
        stuff.add( oskarDiceD );
        stuff.add( oskarDiceF );
        stuff.add( oskarDiceG );
        mappings.put( oskarDiceA, stuff );
        stuff = new HashSet<>();
        stuff.add( oskarDiceA );
        stuff.add( oskarDiceE );
        stuff.add( oskarDiceG );
        mappings.put( oskarDiceB, stuff );
        stuff = new HashSet<>();
        stuff.add( oskarDiceA );
        stuff.add( oskarDiceB );
        stuff.add( oskarDiceF );
        mappings.put( oskarDiceC, stuff );
        stuff = new HashSet<>();
        stuff.add( oskarDiceB );
        stuff.add( oskarDiceC );
        stuff.add( oskarDiceG );
        mappings.put( oskarDiceD, stuff );
        stuff = new HashSet<>();
        stuff.add( oskarDiceA );
        stuff.add( oskarDiceC );
        stuff.add( oskarDiceD );
        mappings.put( oskarDiceE, stuff );
        stuff = new HashSet<>();
        stuff.add( oskarDiceB );
        stuff.add( oskarDiceD );
        stuff.add( oskarDiceE );
        mappings.put( oskarDiceF, stuff );
        stuff = new HashSet<>();
        stuff.add( oskarDiceC );
        stuff.add( oskarDiceE );
        stuff.add( oskarDiceF );
        mappings.put( oskarDiceG, stuff );
        return mappings;
    }

    /* TODO: DOESN'T BELONG IN TEST CLASS */
    private Set<Die> findDiceToBeatOneDie(Die alreadyChosenDie, Map<Die, Set<Die>> mappings) {
        return mappings.get( alreadyChosenDie );
    }




}
