package utilities;

/**
 * General geometric and trigonometric calculation methods.
 */
public final class Calculate {


    public static double within360(double angle) {
        if (angle == 360)
            angle = 0;
        else if (angle > 360)
            angle = angle % 360;

        return angle;
    }

    /**
     * Calculates an unknown side of triangle using the sine rule
     *
     * @param sideA known side
     * @param angleA the opposite angle of the known side in degrees
     * @param angleB the angle of opposite side, in degrees, which will be calculated
     * @return the length of an unkown side
     */

    public static double sineRule(int sideA, double angleA, double angleB) {

        if (angleA == 0) {
            throw new ArithmeticException();
        }
        return (Math.sin(Math.toRadians(angleB)) * sideA) / Math.sin(Math.toRadians(angleA));
    }

    /**
     * Finds the dividing angle of a rectangle, ie the angle formed from two opposite points
     *
     * @param x or length of rectangle
     * @param y or height of rectangle
     * @return dividing angle in degrees
     */
    public static double dividingAngleOfARectangle(int x, int y) {
        if (x == 0 || y == 0) {
            return 0.0;
        }
        return Math.toDegrees(Math.atan((double) x / y));
    }

    /**
     * Reduces angle to an angle within 90 degrees
     *
     * @param angle within degrees
     * @return an angle of 90 or less
     */

    public static double angleWithin90Degrees(double angle) {

        return  (angle % 90 == 0 && angle != 0) ? 90 : (angle - 90 * (int)(angle / 90));
    }

    public static double angleWithin180Degrees(double angle) {

        return  (angle % 180 == 0 && angle != 0) ? 180 : (angle - 180 * (int)(angle / 180));
    }

    /**
     * Finds which quadrant (1-4) of a line graph the angle relates to.
     *
     * @param angle in degrees.
     * @return a number 1-4 which represent the quadrants of line graph.
     */
    public static int quadrantOfAnAngle(double angle) {
        return (int) ((angle / 90) + 1);
    }

    /**
     * Determines the unknown angle of right angle triangle
     *
     * @param knownAngle of triangle
     * @return the unknown angle
     */
    public static double angleOfRightTriangle(double knownAngle) {
        return 180 - (knownAngle + 90);
    }

    /**
     * Simply return the complementary angle of a greater angle with the known inner angle
     *
     * @param innerAngle the size of the lesser angle
     * @param angle the size fo the greater angle
     * @return complementary angle
     * @throws ArithmeticException if inner angle is greater than outer
     */

    public static double complementaryAngle(double innerAngle, double angle) throws ArithmeticException {
        if (innerAngle >= angle) throw new ArithmeticException();

        return angle - innerAngle;
    }

    public static double reflectionAngle(double incidence, double normal) {
        double reflectionAngle;
        double inverseNormal;
        inverseNormal = within360(normal + 180);
        incidence = within360(incidence);

        if (inverseNormal == 0 && Math.ceil(incidence) > 270 && Math.floor(incidence) < 360) {
            inverseNormal = 360;
        }

        if (incidence < inverseNormal-90 || incidence > inverseNormal + 90) {
            return -1; // return -1 incidence is not within normal range
        }
        

        if (incidence > inverseNormal) {
            if (normal == 0) {
                normal = 360;
            }
            reflectionAngle = normal - (incidence - inverseNormal);


        } else if (incidence < inverseNormal) {

            reflectionAngle = normal + (inverseNormal - incidence);

        } else
            return normal;

        reflectionAngle = within360(reflectionAngle);

        return reflectionAngle;

    }

    /**
     * Calculates the mathematical gradient of a line
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return gradient of the line
     */
    public static double gradientOfLine(double x1, double y1, double x2, double y2) {
        return (y1-y2) / (x1-x2);
    }

    /**
     * Calculates the Y intercept of line
     * @param gradient
     * @param xCoordinate
     * @param yCoordinate
     * @return
     */
    public static double yIntercept(double gradient, double xCoordinate, double yCoordinate) {
        return yCoordinate - (gradient * xCoordinate);
    }

    public static double yCoordinate(double gradient, double xCoordinate, double yIntercept) {
        return gradient * xCoordinate + yIntercept;
    }

    public static double xCoordinate(double gradient, double yCoordinate, double yIntercept) {
        return (yCoordinate - yIntercept) / gradient;
    }

    public static double distanceBetweenPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
    }
}
