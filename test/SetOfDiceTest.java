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


public class SetOfDiceTest {

    @Test
    public void testSeveralNontransitiveSimpleSets() {

        testNontransitiveForOnePlayerWithTheseThreeDice(
                new Die( new int[]{0, 0, 0} ),
                new Die( new int[]{1, 1, -2} ),
                new Die( new int[]{-1, -1, 2} ) );

        testNontransitiveForOnePlayerWithTheseThreeDice(
                new Die( new int[]{2, 4, 9} ),
                new Die( new int[]{1, 6, 8} ),
                new Die( new int[]{3, 5, 7} ) );

    }

    private void testNontransitiveForOnePlayerWithTheseThreeDice(Die d1, Die d2, Die d3) {
        Set<Die> diceToMap = new HashSet<>(3);
        diceToMap .add(d1);
        diceToMap .add(d2);
        diceToMap .add(d3);
        SetOfDice dice = new SetOfDice(diceToMap);
        assertTrue(dice.isSetOfDiceNontransitiveForNumPlayers(2));
    }

    @Test
    public void testNotNontransitiveSimpleSet() {
        Die d1, d2, d3;
        d1 = new Die( new int[]{0, 0} );
        d2 = new Die( new int[]{1, 1} );
        d3 = new Die( new int[]{-1, -1} );

        Set<Die> diceToMap = new HashSet<>(3);
        diceToMap .add(d1);
        diceToMap .add(d2);
        diceToMap .add(d3);

        SetOfDice dice = new SetOfDice(diceToMap);
        assertFalse(dice.isSetOfDiceNontransitiveForNumPlayers(2));
    }

    //Per wikipedia article on nontransitive dice the following set of dice, discovered by Oskar,
    // have these characteristics (https://en.wikipedia.org/wiki/Nontransitive_dice#Oskar_dice)
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

    @Test
    public void testNontransitiveForThreePlayers() {
        SetOfDice dice = new SetOfDice(new HashSet<>(Arrays.asList(allOskarDice)));
        assertTrue( dice.isSetOfDiceNontransitiveForNumPlayers(3));
    }

    @Test
    public void testFindDiceToBeatOneDie() {
        SetOfDice dice = new SetOfDice(new HashSet<>(Arrays.asList(allOskarDice)));

        Set<Die> result;
        result = dice.findDiceToBeatOneDie(oskarDiceA);
        assertTrue( result.contains( oskarDiceD ));
        assertTrue( result.contains( oskarDiceF ));
        assertTrue( result.contains( oskarDiceG ));
        assertEquals(result.size(), 3);

        result = dice.findDiceToBeatOneDie(oskarDiceD);
        assertTrue( result.contains( oskarDiceB ));
        assertTrue( result.contains( oskarDiceC ));
        assertTrue( result.contains( oskarDiceG ));
        assertEquals(result.size(), 3);
    }

    @Test
    public void testFindDiceToBeatAll() {
        SetOfDice dice = new SetOfDice(new HashSet<>(Arrays.asList(allOskarDice)));
        Set<Die> result;

        Set<Die> alreadyChosenDice;
        alreadyChosenDice = new HashSet<>();
        alreadyChosenDice.add( oskarDiceA );
        alreadyChosenDice.add( oskarDiceD );
        result = dice.findDiceToBeatAll( alreadyChosenDice);
        assertEquals(result.size(), 1);
        assertTrue( result.contains( oskarDiceG ));

        alreadyChosenDice = new HashSet<>();
        alreadyChosenDice.add( oskarDiceA );
        alreadyChosenDice.add( oskarDiceB );
        result = dice.findDiceToBeatAll( alreadyChosenDice);
        assertEquals(result.size(), 1);
        assertTrue( result.contains( oskarDiceG ));
    }

}
