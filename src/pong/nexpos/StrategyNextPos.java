package pong.nexpos;

/**
 * Abstract class for a Next Position algorithm, Strategy pattern is employed here which allows one
 * implementation to be easily exchanged for another.
 */
public abstract class StrategyNextPos {

    /**
     * Calculates the next XY position and ball direction angle.
     * @param currentX ball coordinate
     * @param currentY ball coordinate
     * @param directionAngle angle the ball is pointing 0-359 degrees
     * @param boundaryX ball boundary length
     * @param boundaryY ball boundary height
     * @return 3 value int array {x, y, ball angle}
     */
    public abstract int[] nextPos(int currentX, int currentY, double directionAngle, int boundaryX, int boundaryY);

}