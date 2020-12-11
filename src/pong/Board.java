package pong;

/**
 * Creates a data structure which defines the size of the game board.
 */
public class Board {
    private final int height;
    private final int length;

    /**
     * Constructor sets the length and height of the game board
     * @param length this would likely be pixels
     * @param height this would likely be pixels
     */

    Board(int length, int height) {
        this.height = height;
        this.length = length;
    }

    /**
     * Get the height of the game board
     * @return this value may relate to pixels
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the length of the game board
     * @return this value may relate to pixels
     */
    public int getLength() {
        return length;
    }
}
