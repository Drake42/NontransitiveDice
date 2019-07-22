import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Die {
    private List<Integer> faceValues;

    public Die(int[] faceValuesArray ) {
        this.faceValues = new ArrayList<Integer>( faceValuesArray.length );
        for( int i=0; i<faceValuesArray.length; i++ ) {
            this.faceValues.add( faceValuesArray[i] );
        }
    }

    public boolean beats(Die die1) {
        if( die1 == this ) return false;
        int wins = 0;
        int nonWins = 0;
        Iterator<Integer> it = faceValues.iterator();
        while (it.hasNext()) {
            int myValue = it.next();
            Iterator<Integer> other = die1.faceValues.iterator();
            while (other.hasNext()) {
                int otherValue = other.next();
                if (myValue > otherValue) wins++;
                else nonWins++;
            }
        }
        return wins > nonWins;
    }

    public boolean beatsAllOfThese(Die[] dice) {
        int index=0;
        while(hasMoreElements(index, dice)) {
            if (!this.beats(dice[index]))
                return false;
            index++;
        }
        return true;
    }

    private boolean hasMoreElements(int index, Die[] dice) {
        return index<dice.length && dice[index]!=null;
    }
}
