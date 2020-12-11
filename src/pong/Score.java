package pong;

/**
 * Holds game scores
 *
 * @author Lindsay Kerr
 * @version 0.1
 */

public class Score implements IScore {
    private int numberScore;

    public Score() {
        numberScore = 0;
    }
    public Score(int scoreValue) {
        numberScore = scoreValue;
    }


    public void updateScore(int numberScore) {
        this.numberScore += numberScore;
    }



    public int getScore() {
        return numberScore;
    }

}
