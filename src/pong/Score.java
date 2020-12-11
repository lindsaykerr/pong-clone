package pong;

/**
 * Holds game scores
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
