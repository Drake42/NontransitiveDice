import java.util.*;

public class Die {
    private List<Integer> faceValues;

    public Die(int[] faceValuesArray ) {
        this.faceValues = new ArrayList<Integer>( faceValuesArray.length );
        for (int value : faceValuesArray) {
            this.faceValues.add(value);
        }
    }

    public boolean beats(Die die1) {
        if( die1 == this )
            return false;

        int wins = 0;
        int nonWins = 0;

        for (int myValue : faceValues) {
            for (int otherValue : die1.faceValues) {
                if (myValue > otherValue) wins++;
                else nonWins++;
            }
        }
        return wins > nonWins;
    }

    //return the subset of opponentDice which beat this Die
    Set<Die> findDiceThatBeatMeFrom(Set<Die> opponentDice) {
        Set<Die> diceThatBeatCurrentDie = new HashSet<>();
        for (Die opponent : opponentDice) {
            if (opponent.beats(this)) {
                diceThatBeatCurrentDie.add(opponent);
            }
        }
        return diceThatBeatCurrentDie;
    }
}
