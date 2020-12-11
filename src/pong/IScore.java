package pong;

/**
 * A common interface for classes which utilise the Score class
 *
 * @author Lindsay Kerr
 * @version 0.1
 */

public interface IScore {

    /**
     * Increment or decrement the score by some value
     * @param scoreValue positive or negative integer;
     */
    void updateScore(int scoreValue);

    /**
     * Retrieve a Score
     * @return integer value
     */
    int getScore();
}
