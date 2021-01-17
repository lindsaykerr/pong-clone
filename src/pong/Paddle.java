package pong;

/**
 *
 *  Used to create a paddle object. Contains getter methods to obtain data
 *  regarding the position and dimension of the game paddle.
 *  Provides a number of setters for altering such values.
 *
 * @author Lindsay Kerr
 * @version 0.1
 */
public class Paddle implements Impact {

    private int posY;   // Y position of paddle
    private int posX;   // X position of paddle
    private int width;
    private int height;
    public int yLimit;
    private int speed;
    private int normal;

    /**
     * 
     * Constructor which instantiates a default paddle width default parameters.
     * Starting with the coordinate position (10, 10), width of 10, height of 30 and
     * max vertical limit of 100
     *
     */
    Paddle() {
        posX = 10;
        posY = 10;
        width = 10;
        height = 30;
        yLimit = 100;
        speed = 2;
    }

    /**
     *
     * Instantiates a new paddle object
     *
     * @param posX x coordinate position of the paddle located at top left of the paddle
     * @param posY y coordinate position of the paddle
     * @param width of the paddle
     * @param height of the paddle
     * @param yLimit maximum movement position along the Y axis
     */

    Paddle(int posX, int posY, int width, int height, int normal, int yLimit, int speed) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.yLimit = yLimit;
        this.speed = speed;
        this.normal = normal;
    }

    /* PADDLE CONTROLS */


    /**
     * Moves the paddle downwards along the Y axis
     */
    public void moveDown() {
        if (posY + speed <= yLimit-height) posY += speed;
    }


    /**
     * Moves the paddle upwards along the Y axis
     */
    public void moveUp() { if (posY - speed >= 0) posY -= speed; }


    /* SETTERS */

    /**
     * Sets the x position of the paddle
     * @param posX x coordinate of the paddle
     */

    public void setPosX(int posX) {
        this.posX = posX;
    }


    /**
     * Sets the Y position of the paddle
     * @param posY Y coordinate of paddle
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }


    /**
     * Sets the height of the paddle
     * @param height of the paddle
     */
    public void setHeight(int height) {
        this.height = height;
    }


    /**
     * Sets the width of the paddle
     * @param width of the paddle
     */
    public void setWidth(int width) {
        this.width = width;
    }


    /**
     * Set the Y movement limit
     *
     * @param yLimit maximum movement position along the Y axis
     */
    public void setYLimit(int yLimit) { this.yLimit = yLimit; }

    /**
     * Set the paddle movement speed
     * @param speed value
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /* GETTERS */

    /**
     * Gets the set width of the paddle
     *
     * @return paddle width
     */
    public int getWidth() { return width; }


    /**
     * Gets the height of the paddle
     *
     * @return paddle height
     */
    public int getHeight() {
        return height;
    }


    /**
     * Gets the Y position of the, ie the top of the paddle
     *
     * @return Y coordinate of th paddle
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Gets the X position of the, ie the left of the paddle
     *
     * @return X coordinate of th paddle
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Gets the XY coordinates of the paddle, which is the top left corner
     *
     * @return two value array {x, y}
     */
    public int[] getPos() {
        return new int[]{posX, posY};
    }

    /**
     * Return the paddles travel speed
     * @return travel speed in integers
     */
    public int getSpeed() {
        return speed;
    }

    @Override
    public Impact testImpact(double x, double y) {
        if (x >= posX && x <= posX+width && y >= posY && y <= posY+height) {
            return this;
        }
        return null;
    }

    @Override
    public void setNormal(int normal) {
        this.normal = normal;
    }

    @Override
    public int getNormal() {
        return normal;
    }


    public void movePaddleY(double y) {
        if (y >= 0 && y <= yLimit-height) posY = (int) y;
    }
}

