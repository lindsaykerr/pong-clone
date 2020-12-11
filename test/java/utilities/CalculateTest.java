package utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculateTest {

    @Test
    void sineRuleChecks() {
        assertEquals( 448, Math.round(Calculate.sineRule(200, 20, 50)));
        assertThrows(ArithmeticException.class, () ->Calculate.sineRule(200, 0, 50));

    }

    @Test
    void getTheDividingSngleOfATrinagle() {
        assertEquals(0,(int)Calculate.dividingAngleOfARectangle(100, 0));
        assertEquals(0,(int)Calculate.dividingAngleOfARectangle(0, 100));
        assertEquals(63,(int)Calculate.dividingAngleOfARectangle(200, 100));
    }


    @Test
    void testTheAngleWithin90Degrees() {
        assertEquals(40, Calculate.angleWithin90Degrees(220));
        assertEquals(35, Calculate.angleWithin90Degrees(125));
        assertEquals(30, Calculate.angleWithin90Degrees(300));

        assertEquals(90, Calculate.angleWithin90Degrees(90));
        assertEquals(90, Calculate.angleWithin90Degrees(180));
        assertEquals(0, Calculate.angleWithin90Degrees(Calculate.within360(360)));
        assertEquals(55, Calculate.angleWithin90Degrees(Calculate.within360(415)));
    }

    @Test
    void whatQuadrantDoesAnAngleLieIn() {
        assertEquals(1, Calculate.quadrantOfAnAngle(0));
        assertEquals(2, Calculate.quadrantOfAnAngle(90));
        assertEquals(2, Calculate.quadrantOfAnAngle(124));
        assertEquals(3, Calculate.quadrantOfAnAngle(200));
        assertEquals(4, Calculate.quadrantOfAnAngle(270));
        assertEquals(4, Calculate.quadrantOfAnAngle(359));
        assertEquals(1, Calculate.quadrantOfAnAngle(Calculate.within360(360)));

    }
    @Test
    void whatIsTheUknownAngleOfARightTrangle() {
        assertEquals(70, Calculate.angleOfRightTriangle(20));
    }

    @Test
    void findTheComplementaryAngle(){
        assertEquals(70, Calculate.complementaryAngle(20, 90));
    }


    @Test
    void checkifthereflectionAnglesareTrue() {
       assertEquals(225, Calculate.reflectionAngle(135, 270));
       assertEquals(190, Calculate.reflectionAngle(170, 270));
       assertEquals(45, Calculate.reflectionAngle(135, 0));
       assertEquals(290, Calculate.reflectionAngle(70, 270));
       assertEquals(135, Calculate.reflectionAngle(45, 180));
       assertEquals(260, Calculate.reflectionAngle(280, 180));
       assertEquals(130, Calculate.reflectionAngle(230, 90));
       assertEquals(70, Calculate.reflectionAngle(290, 90));
       assertEquals(270, Calculate.reflectionAngle(90, 270));


       //int[] norms = {0, 90, 180, 270};


        /*
       for (int i = 269; i > 90; i--) {
               System.out.println(Calculate.within360(i) + " of " + 0 + " = " +Calculate.reflectionAngle(i,0));
       }

       System.out.println("Normal is 90");
        for (int i = 1; i < 360; i++) {
            System.out.println(Calculate.within360(i) + " of " + 90 + " = " +Calculate.reflectionAngle(i,90));
        }

        System.out.println("Normal is 180");
        for (int i = 1; i < 360; i++) {
            System.out.println(Calculate.within360(i) + " of " + 180 + " = " +Calculate.reflectionAngle(i,180));
        }

        System.out.println("Normal is 279");
        for (int i = 1; i < 360; i++) {
            System.out.println(Calculate.within360(i) + " of " + 270 + " = " +Calculate.reflectionAngle(i,270));
        }*/
    }

    @Test
    void checkTheGradientOfALine() {
        assertEquals(2.5, Calculate.gradientOfLine(0,0,2,5));
        assertEquals(-1.2, Calculate.gradientOfLine(8,12,13,6));
    }

    @Test
    void findTheYIntercept() {
        assertEquals(4.0, Calculate.yIntercept(1.0, 4.0, 8.0));
        assertEquals(3, Calculate.yIntercept((-1.0/3), 6,1));
    }

    @Test
    void determineYCoordinate(){
        assertEquals(9, Calculate.yCoordinate(1.0, 5.0, 4.0));
        assertEquals(0, Calculate.yCoordinate((-1.0/3), 9.0, 3.0));

    }

    @Test
    void determineXCoordinate() {
        assertEquals(5.0, Calculate.xCoordinate(1.0, 9.0, 4.0));
        assertEquals(9.0, Calculate.xCoordinate((-1.0/3), 0.0, 3.0));
    }

    @Test
    void distanceBetweenTwoPoints() {
        assertEquals(6, Math.round(Calculate.distanceBetweenPoints(0,4,4,8)));
        assertEquals(3, Math.round(Calculate.distanceBetweenPoints(3,4,6,5)));
    }


}