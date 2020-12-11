package pong.nexpos;

import utilities.Calculate;


/**
 * Strategy which calculates the position of ball based on the quadrant the direction angle is pointing.
 *
 * @author Lindsay Kerr
 * @version 0.1
 */
public class NextPosFromQuads extends pong.nexpos.StrategyNextPos {


    private int ballPosX(int height, double ballAngle, boolean useComplementary) {
        return findUnknownSideLengthOfTriangle(height, ballAngle, useComplementary);
    }

    private int ballPosY(int length, double ballAngle, boolean useComplementary) {
        return findUnknownSideLengthOfTriangle(length, ballAngle, useComplementary);
    }

    private int findUnknownSideLengthOfTriangle(int knownSide, double knownAngle, boolean useComplementary) {
        if (useComplementary) {
            knownAngle = Calculate.angleOfRightTriangle(knownAngle);
        }
        return (int) Math.round(
                Calculate.sineRule(knownSide, Calculate.angleOfRightTriangle(knownAngle),knownAngle)
        );

    }


    @Override
    public int[] nextPos(int currentX, int currentY, double incidence, int boundaryX, int boundaryY) {
        int length, height;
        int newX = 0, newY = 0;
        double dividingAngle;
        double directionAngle = incidence;

        /* using the direction angle find which quadrant the next XY position will be in */
        switch(Calculate.quadrantOfAnAngle((int) directionAngle)) {
            case 1:
                /* from the current XY position determine the length and height of the quadrant */
                length = boundaryX - currentX;
                height = currentY;
                /* next find the diving angle of the quadrant, i.e. the line formed from the
                 * XY position  to the opposite corner of the quadrant. This splits the quadrant
                 * into two triangles.
                 */
                dividingAngle = Calculate.complementaryAngle(
                            Calculate.dividingAngleOfARectangle(length, height),90);

                /* next find within which triangle the direction angle is found, in doing so the side
                 * where the next XY position is found can be calculated with some basic trigonometry.
                 */
                if (directionAngle < (int) dividingAngle) {
                    newY = currentY - ballPosY(length, directionAngle, false);
                    newX = boundaryX;
                    /* finally the ball reflection angle is calculated */
                    directionAngle = Calculate.reflectionAngle(incidence, 180);
                }
                else if (directionAngle > (int) dividingAngle) {
                    newX = currentX + ballPosX(height, directionAngle, true);
                    directionAngle = Calculate.reflectionAngle(incidence, 270);
                }
                else {
                    newX = boundaryX;
                    directionAngle = Calculate.within360(incidence +180);
                    }
                break;
            case 2:
                length = currentX;
                height = currentY;
                directionAngle -= 90;
                dividingAngle =  Calculate.dividingAngleOfARectangle(length, height);
                if (directionAngle < (int) dividingAngle) {
                    newX = currentX - ballPosX(height, directionAngle, false);
                    directionAngle = Calculate.reflectionAngle(incidence, 270);

                }
                else if (directionAngle > (int) dividingAngle) {
                    newY = currentY - ballPosY(length, directionAngle, true);
                    directionAngle = Calculate.reflectionAngle(incidence, 0);
                }
                else
                    directionAngle = Calculate.within360(incidence +180);
                break;
            case 3:
                length = currentX;
                height = boundaryY - currentY;
                directionAngle -= 180;
                dividingAngle = Calculate.complementaryAngle(
                        (int) Calculate.dividingAngleOfARectangle(length, height),
                        90
                );
                if (directionAngle < (int) dividingAngle) {
                    newY = currentY + ballPosY(length, directionAngle, false);
                    directionAngle = Calculate.reflectionAngle(incidence, 0);
                }
                else if (directionAngle > (int) dividingAngle) {
                    newX = currentX - ballPosX(height, directionAngle, true);
                    newY = boundaryY;
                    directionAngle = Calculate.reflectionAngle(incidence, 90);

                }
                else {
                    newY = boundaryY;
                    directionAngle = Calculate.within360(incidence +180);
                }
                break;
            case 4:
                length = boundaryX - currentX;
                height = boundaryY - currentY;
                directionAngle -= 270;
                dividingAngle = Calculate.dividingAngleOfARectangle(length, height);
                if (directionAngle < (int) dividingAngle) {
                    newX = currentX + ballPosX(height, directionAngle, false);
                    newY = boundaryY;
                    directionAngle = Calculate.reflectionAngle(incidence, 90);
                }
                else if (directionAngle > (int) dividingAngle) {
                    newY = currentY + ballPosY(length, directionAngle, true);
                    newX = boundaryX;
                    directionAngle = Calculate.reflectionAngle(incidence, 180);
                }
                else {
                    newX = boundaryX;
                    newY = boundaryY;
                    directionAngle = Calculate.within360(incidence +180);
                }
                break;
        }


        return new int[] {newX, newY, (int) directionAngle};
    }
}
