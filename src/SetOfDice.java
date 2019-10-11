import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SetOfDice {

    private final Set<Die> dice;
    private final Map<Die, Set<Die>> mappings;

    public SetOfDice(Set<Die> dice) {
        this.dice = dice;
        mappings = setupMappingsFromDieToDiceThatBeatIt(this.dice);
    }

    Map<Die, Set<Die>> setupMappingsFromDieToDiceThatBeatIt(@NotNull Set<Die> diceToMap) {
        Map<Die, Set<Die>> mappings = new HashMap<>();

        for (Die currentDie : diceToMap) {
            Set<Die> diceThatBeatCurrentDie = currentDie.findDiceThatBeatMeFrom(diceToMap);
            mappings.put(currentDie, diceThatBeatCurrentDie);
        }

        return mappings;
    }

    public boolean isSetOfDiceNontransitiveForNumPlayers(int numPlayers) {
        List<List<Die>> allCombinationsOfNMinusOneDice;
        allCombinationsOfNMinusOneDice = Combinations.generateCombinationsOf(numPlayers, dice);

        for (List<Die> combination : allCombinationsOfNMinusOneDice) {
            Set<Die> chosenDice = new HashSet<>(combination);
            Set<Die> beatsCurrent = findDiceToBeatAll(chosenDice);
            if (beatsCurrent == null || beatsCurrent.size() < 1) {
                return false;
            }
        }
        return true;
    }

    //This method has been updated to realize Goal #1 (see comment at top)
    //However, we haven't yet tested for anything larger than two players. So...
    //TODO: write some tests to verify that "isSetOfDiceNontransitiveForNumPlayers" works for more than two players
    // ARJ: ^^^ as of 25 Sep, this ^^^ is the first next step

    public Set<Die> findDiceToBeatAll(Set<Die> alreadyChosenDice) {
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

    public Set<Die> findDiceToBeatOneDie(Die alreadyChosenDie) {
        return this.mappings.get(alreadyChosenDie);
    }

}
