package pong;
/**
 * Player class is primarily used to hold reference to a paddle object and score object
 */
public class Player implements IScore {
    public Paddle paddle;
    private Score score;

    public Player(Paddle paddle, Score score) {

        this.paddle = paddle;
        this.score = score;
    }

    /**
     * Retrieves a the player score.
     * @return score value
     */
    public int getScore() {
        return score.getScore();
    }

    /**
     * Used to update players score value.
     * @param changeScoreBy positive or negative integer
     */
    public void updateScore(int changeScoreBy) {
        score.updateScore(changeScoreBy);
    }
}
