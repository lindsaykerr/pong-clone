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
    private int radius;

    /* linear Algebra */
    private double lineGradient;
    private double distanceBetweenPoints;
    private double yIntercept;

    /* position */
    private int toX;
    private int toY;
    private double posX;
    private double posY;
    private int fromX = 0;
    private int fromY = 0;

    /* orientation */
    private short directionAngle;
    private short previousAngle;
    private byte xTravel = 0;
    private byte yTravel = 0;



    /* limitations */
    private short[] boundary = new short[4];


    public Ball() {
        radius = 0;
        boundary = new short[]{0, 0, 100, 100};
    }


    public Ball(int diameter) {

        this.radius = diameter / 2;
    }



    /* Position */

    /**
     * The X position of the ball
     * @return x coordinate
     */
    public int getToX() {
        return toX;
    }

    /**
     * The y position of the ball
     * @return y coordinate
     */
    public int getToY() {
        return toY;
    }

    /**
     * Returns XY coordinate of the ball in the form of an array
     * @return {x, y}
     */
    public int[] getCoordinates() {
        return new int[] {toX, toY};
    }

    /**
     * Change the X coordinate of the ball
     * @param toX coordinate
     */
    public void setToX(int toX) {
        this.toX = toX;
    }

    /**
     * Change the Y coordinate of the ball
     * @param toY coordinate
     */
    public void setToY(int toY) {
        this.toY = toY;
    }


   public double getXPos() { return this.posX; }


   public double getYPos() { return this.posY; }



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
        return radius * 2;
    }
    /**
     * Change the diameter of the ball
     * @param diameter of ball
     */
    public void setDiameter(int diameter) {
        this.radius = diameter / 2;
    }

    public int getRadius() {
        return radius;
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
        this.toX = x;
        this.toY = y;
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
    public void increaseSpeed(int incrementValue) {
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
     */
    public void impact(Impact obj) {
        toX = (int) posX;
        toY = (int) posY;

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

    public void linearStep(double step) {

        // When the slope ratio is greater than |1| the x coordinate of the line is
        // calculated rather than the y as it provides more accurate result.
        if (Math.abs(lineGradient) < 1) {

            // if the ball is moving towards 0 on the x axis, step is negative.
            if (xTravel == -1) step = -(step);

            posX += step;
            posY = Calculate.yCoordinate(lineGradient, posX, yIntercept);
        }
        else {
            // if the ball is moving towards 0 on the y axis, step is negative.
            if (yTravel == -1) step = -(step);

            posY += step;
            posX = Calculate.xCoordinate(lineGradient, posY, yIntercept);
        }
    }

    // changes position and orientation state of the ball
    private void newXYAndAngle() {
        fromX = toX;
        fromY = toY;

        previousAngle = directionAngle;
        int[] neXYvals = nextPos.nextPos(
                toX,
                toY,
                directionAngle,
                boundary[2]-radius,
                boundary[3]-radius
        );
        toX = neXYvals[0]+radius;
        toY = neXYvals[1]+radius;
        calculateLinearValues();
        System.out.println("bounds x1 "+boundary[0] +" bounds y1 "+boundary[1]+" bounds x2 "+boundary[2]+" bounds y2 "+ boundary[3]);
        directionAngle = (short) neXYvals[2];
        xTravel = (byte) Integer.compare(toX, fromX);
        yTravel = (byte) Integer.compare(toY, fromY);




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
        boundary[0] = (short) (radius);
        boundary[2] = (short) (length - radius);
    }

    /**
     * Changes the Maximum boundary height limitation
     * @param height of travel the ball is limited to
     */
    public void SetBoundaryHeight(int height) {
        boundary[1] = (short) (radius);
        boundary[3] = (short) (height - radius);
    }

    /**
     * Set both length and height bound limitations
     * @param length of travel the ball is limited to
     * @param height of travel the ball is limited to
     */
    public void setBallBounds(int length, int height) {
        boundary[0] = (short) (radius);
        boundary[1] = (short) (radius);
        boundary[2] = (short) (length - radius);
        boundary[3] = (short) (height - radius);

    }

    public boolean isWithinBounds() {
        return  posX > boundary[0] && posX < boundary[2] &&
                posY > boundary[1] && posY < boundary[3];
    }

    /* linear Algebra Calculation */

    private void calculateLinearValues() {

        lineGradient = Calculate.gradientOfLine(fromX, fromY, toX, toY);

        yIntercept = Calculate.yIntercept(lineGradient, toX, toY);

        distanceBetweenPoints = Calculate.distanceBetweenPoints(fromX, fromY, toX, toY);
    }

    public void recalculateLinearValue(){
        calculateLinearValues();
    }

    public void printVariables() {
        System.out.println(
                "slope:\t\t\t" + lineGradient +
                        "\nbetweenPoints:\t" + distanceBetweenPoints +
                        "\nyIntercept:\t\t" + yIntercept +
                        "\ntoX:\t\t\t" + toX +
                        "\ntoY:\t\t\t" + toY +
                        "\nfromX:\t\t\t" + fromX +
                        "\nfromY\t\t\t" + fromY
        );
        System.out.println("\n\n");
    }

    public void setPosY(int y) {
        posY = y;
    }
    public void setPosX(int x) {
        posX = x;
    }
}
