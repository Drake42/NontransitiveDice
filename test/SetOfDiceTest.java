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
Done: Goal 1: determine whether a given set of dice is nontransitive, for a given set of dice and number of players
implemented in method isSetOfDiceNontransitiveForNumPlayers(...)
we tested this method for two and three players, but not beyond.

TODO: this is the top level goal as of 15 Oct... smaller details live in other todo items below
Goal 2: generate nontransitive die sets, for a given
        number of dice
        given number of sides per die
        given total of face values?
        given lower and upper bounds on face values?

//On 15 Oct, Nathan and Ashley are thinking about approaches to Goal #2

        //assert... because set 1 is non-transitive for 2 players, set 2 is also non-transitive for 2 players, where
        Set 1 is:
        A has sides 2, 4, 9 / -3, -1, 4
        B has sides 1, 6, 8 / -4, 1, 3
        C has sides 3, 5, 7 / -2, 0, 2
        Set 2 is set 1, with -5 added to each face value, resulting in the sum of face values being 0 on each die
        A has sides -3, -1, 4
        B has sides -4, 1, 3
        C has sides -2, 0, 2
        //yes, we actually validated set 2 in the code below...
        //however, we didn't prove that our logic is sound for all such transformations. We're relying on intuition for now.

        //set arbitrary bounds
        Generate set of dice such that:
        1. 3 dice which are nontransitive for 2 players
        2. each die has 6 sides
        3. the sum of face values for all sides of any die is 0
        4. the range of allowed values for any die is whole numbers from -25 to 25
          (or, we could restrict five sides to that range, and allow for the sixth side, any whole number to satistfy #3

        //possible Algorithm... for above...
        A. Generate the set of all possible dice for parameters( sides, sum of face values, range of allowed values)
            DONE - see SetOfDiceGenerator.generateAllPossibleFaceValueCombinationsFor(numFacesPerDie, faceValueSum, faceValueLow, faceValueHigh );
        B. Generate the mappings of which dice beat which other dice
            This is done by the constructor when we create an instance of SetOfDice
        C. Given those mappings, finding a set non-transitive for N dice is simply a graph traversal problem within the mappings




        //because this is a lot of possibilities...
        Hand solve a very simple case like...
        1. 3 dice nontransitive for 2 players
        2. each die has 3 sides
        3. the sum of face values for all sides of any die is 0
        4. the range of allowed values for any die is -4 to 4
        How many possibilities does this involve?
        9 possibilities per face * 3 faces = 27 discrete dice we can generate,
        but some of these are effectively duplicates - (1,2,3) and (3,2,1) for example
        and some of them drop out when we enforce sum of face values
        but since we haven't computed those yet, let's take the worst case and assume there are 27 discrete dice
        Now, how many combinations of 3 dice can we extract from 27 discrete dice?
        //the answer is = n! / r! * (n - r)!, where n is total items, and r is number of items being chosen at a time.
        Which is 2925
        So, in step B, we get to create mappings for up to 2925 dice. I'm glad the 27 will actually be smaller!


        TODO: next step...
        solve the immediately above very simple case by hand...
        build physical artifacts showing the resulting set of dice, at each step of A/B/C above.
        use the physical artifact(s) for the mappings (B) to understand and generalize how we traverse in step C


 Goal 3: generate nontransitive die sets, for a given
        number of players, which will ultimately determine the required number of dice
        and possibly the same side and face value related data above


 */


public class SetOfDiceTest {

    @Test
    public void testFindNontransitiveSubsets_forOneDie() {
        Die y1 = new Die(new int[]{1, 2, 3});
        Set<Die> setOfOneDie = new HashSet<Die>(1);
        setOfOneDie.add(y1);
        SetOfDice setOfDice1 = new SetOfDice(setOfOneDie);
        List<Set<Die>> attempt1 = setOfDice1.findAllNontransitiveSubsets();
        assertNull(attempt1);
    }

    @Test
    public void testFindNontransitiveSubsets_forThreeDieCycle() {
        Set<Die> setOfThreeDice = new HashSet<Die>(3);
        setOfThreeDice.add(new Die( new int[]{0, 0, 0} ));
        setOfThreeDice.add(new Die( new int[]{1, 1, -2} ));
        setOfThreeDice.add(new Die( new int[]{-1, -1, 2} ));
        SetOfDice setOfDice2 = new SetOfDice( setOfThreeDice );
        List<Set<Die>> attempt2 = setOfDice2.findAllNontransitiveSubsets(); //TODO implement this method so it passes this test
        Set<Die> expected = setOfThreeDice;
        assertEquals( expected, attempt2 );
    }

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

        testNontransitiveForOnePlayerWithTheseThreeDice(
                new Die( new int[]{-3, -1, 4} ),
                new Die( new int[]{-4, 1, 3} ),
                new Die( new int[]{-2, 0, 2} ) );


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
