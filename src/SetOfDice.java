import java.util.*;

public class SetOfDice {

    private final Set<Die> dice;
    private final Map<Die, Set<Die>> mappings;

    //TODO: temporary for transition of everything to the one arg constructor below. Delete this afterwards.
    public SetOfDice() {
        this.dice = null;
        this.mappings = null;
    }

    public SetOfDice(Set<Die> dice) {
        this.dice = dice;
        mappings = setupMappingsFromDieToDiceThatBeatIt(this.dice);
    }

    Map<Die, Set<Die>> setupMappingsFromDieToDiceThatBeatIt(Set<Die> diceToMap) {
        Map<Die, Set<Die>> mappings = new HashMap<>();

        for (Die currentDie : diceToMap) {
            Set<Die> diceThatBeatCurrentDie = currentDie.findDiceThatBeatMeFrom(diceToMap);
            mappings.put(currentDie, diceThatBeatCurrentDie);
        }

        return mappings;
    }

    public boolean isSetOfDiceNontransitiveForNumPlayers(int numPlayers) {
        return isSetOfDiceNontransitiveForNumPlayers(dice, numPlayers);
    }

    //This method has been updated to realize Goal #1 (see comment at top)
    //However, we haven't yet tested for anything larger than two players. So...
    //TODO: write some tests to verify that "isSetOfDiceNontransitiveForNumPlayers" works for more than two players
    // ARJ: ^^^ as of 25 Sep, this ^^^ is the first next step

    //for N players, Nontransitive means...
    // for any possible set of (n-1) dice, there exists at least one die that beats all of them.
    public boolean isSetOfDiceNontransitiveForNumPlayers(Set<Die> diceToMap, int numPlayers) {
        Map<Die, Set<Die>> mappings1;
        mappings1 = setupMappingsFromDieToDiceThatBeatIt(diceToMap);
        Map<Die, Set<Die>> mappings = mappings1;

        List<List<Die>> allCombinationsOfNMinusOneDice;
        //noinspection JoinDeclarationAndAssignmentJava
        allCombinationsOfNMinusOneDice = Combinations.generateCombinationsOf(numPlayers, diceToMap);

        for (List<Die> combination : allCombinationsOfNMinusOneDice) {
            Set<Die> chosenDice = new HashSet<>(combination);
            Set<Die> beatsCurrent = findDiceToBeatAll(chosenDice, mappings);
            if (beatsCurrent == null || beatsCurrent.size() < 1) {
                return false;
            }
        }
        return true;
    }


    Set<Die> findDiceToBeatAll(Set<Die> alreadyChosenDice, Map<Die, Set<Die>> mappings) {
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

    Set<Die> findDiceToBeatOneDie(Die alreadyChosenDie, Map<Die, Set<Die>> mappings) {
        return mappings.get( alreadyChosenDie );
    }



}
