package pong;



import pong.nexpos.NextPosFromQuads;
import pong.nexpos.StrategyNextPos;
import utilities.Calculate;

/**
 * Ball entity with a number of attributes and behaviour relating to size,
 * position, speed and orientation within a 2D plain.
 *
 * @author Lindsay Kerr
 * @version 0.1
 */
public class Ball {

    /* behaviour attributes */
    final static double MINSPEED = 2;
    final static int MAXSPEED = 100;
    final private StrategyNextPos nextPos = new NextPosFromQuads();
    private double speed = MINSPEED;


    /* geometry */
    private int diameter;

    /* position */
    private int x;
    private int y;
    private int fromX = 0;
    private int fromY = 0;

    /* orientation */
    private short directionAngle;
    private short previousAngle;
    private byte xTravel = 0;
    private byte yTravel = 0;

    /* limitations */
    private final short[] boundary;


    public Ball() {
        diameter = 0;
        boundary = new short[]{100, 100};
    }


    public Ball(int diameter) {

        this.diameter = diameter;
        boundary = new short[2];
    }



    /* Position */

    /**
     * The X position of the ball
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * The y position of the ball
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Returns XY coordinate of the ball in the form of an array
     * @return {x, y}
     */
    public int[] getCoordinates() {
        return new int[] {x, y};
    }

    /**
     * Change the X coordinate of the ball
     * @param x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Change the Y coordinate of the ball
     * @param y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }


    /**
     * Get the previous X position of the ball
     * @return last X position
     */
    public double getPreviousX() {
        return fromX;
    }

    /**
     * Get the previous Y position of the ball
     * @return last Y position
     */
    public double getPreviousY() {
        return fromY;
    }

    /**
     * This method changes the from x value.
     * Ball contains two X attributes which can be described as X1(from) and X2(2).
     * A change to X1 will affect the result of any linear equation that could be calculated.
     * @param lastX coordinate
     */
    public void setFromX(int lastX) {this.fromX = lastX;}

    /**
     * This method changes the from y value.
     * Ball contains two Y attributes which can be described as Y1(from) and Y2(2).
     * A change to YY1 will affect the result of any linear equation that could be calculated.
     * @param lastY coordinate
     */
    public void setFromY(int lastY) {this.fromY = lastY;}



    /* Geometry */

    /**
     * Get ball diameter
     * @return the diameter of the ball
     */
    public int getDiameter() {
        return  diameter;
    }
    /**
     * Change the diameter of the ball
     * @param diameter of ball
     */
    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }



    /* Orientation */

    /**
     * Get the current direction angle, within 360 degree.
     * @return 0-359
     */
    public int getAngle() {
        return directionAngle;
    }


    /**
     * Changes the direction at which the ball is pointing
     * @param angle of 360 degrees
     */
    public void setDirectionAngel(int angle) {
        directionAngle = (short) angle;
    }

    /**
     * Indicates the direction the ball is traveling along the X axis.
     * @return
     * 0 - ball is not moving along x axis,
     * 1 - ball is moving to the right,
     * -1 - ball is moving to the left.
     */
    public byte getXTravel() {
        return xTravel;
    }

    /**
     * Indicates the direction the ball is traveling along the y axis.
     * @return
     * 0 - ball is not moving along y axis
     * 1 - ball is moving downwards
     * -1 - ball is moving upwards
     */
    public byte getYTravel() {
        return yTravel;
    }

    /**
     * Set both XY coordinate of the ball
     * @param x coordinate
     * @param y coordinate
     */
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }



    /* Speed */

    /**
     * Get the current ball speed
     * @return A value between MINSPEED {@value #MINSPEED}
     * and MAXSPEED {@value #MAXSPEED}.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Increases the ball speed by a value
     * @param incrementValue increment value, limit maximum speed of {@value #MAXSPEED}
     */
    void increaseSpeed(int incrementValue) {
        if (speed + incrementValue <= MAXSPEED)
            speed += incrementValue;
        else
            speed = 10;
    }

    /**
     * Decreases the ball speed by a value
     * @param decrementValue decrement value, limit minimum speed of {@value #MINSPEED}
     */
    public void decreaseSpeed(int decrementValue) {
        if(speed - decrementValue >= MINSPEED)
            speed -= decrementValue;
        else
            speed = 1;

    }

    /**
     * Sets ball to its default speed
     */
    public void resetSpeed() {
        this.speed = MINSPEED;
    }



    /* Action */

    /**
     * Recalculates the balls XY and orientation based on the impact XY values.
     * @param obj the Object which the ball came into contact with
     * @param impactOnX coordinate
     * @param impactOnY coordinate
     */
    public void impact(Impact obj, int impactOnX, int impactOnY) {
        x = impactOnX;
        y = impactOnY;
        speed -= 2;

        directionAngle = (short)Calculate.reflectionAngle(previousAngle, obj.getNormal());
        if (directionAngle == -1) {
            directionAngle = previousAngle;
        }
        newXYAndAngle();

    }

    /**
     * Changes the state of the ball object to the next calculated position
     * and orientation based on the balls current state.
     */
    public void nextMove(){
        newXYAndAngle();
    }


    // changes position and orientation state of the ball
    private void newXYAndAngle() {
        fromX = x;
        fromY = y;
        previousAngle = directionAngle;
        int[] neXYvals = nextPos.nextPos(x, y, directionAngle, boundary[0],boundary[1]);
        x = neXYvals[0];
        y = neXYvals[1];
        directionAngle = (short) neXYvals[2];
        xTravel = (byte) Integer.compare(x, fromX);
        yTravel = (byte) Integer.compare(y, fromY);

    }



    /* Ball Bounds */

    /**
     * An array containing the boundary length and height values
     * @return and array of integer values
     */
    public short[] getBallBoundary() {
        return boundary;
    }

    /**
     * Changes the Maximum boundary length limitation
     * @param length of travel the the ball is limited to
     */
    public void setBoundaryLength(int length) {
        boundary[0] = (short) length;
    }

    /**
     * Changes the Maximum boundary height limitation
     * @param height of travel the ball is limited to
     */
    public void SetBoundaryHeight(int height) {
        boundary[1] = (short) height;
    }

    /**
     * Set both length and height bound limitations
     * @param length of travel the ball is limited to
     * @param height of travel the ball is limited to
     */
    public void setBallBounds(int length, int height) {
        boundary[0] = (short) (length - diameter / 2);
        boundary[1] = (short) (height - diameter / 2);

    }

}
