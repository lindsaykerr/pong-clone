package pong;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {


    private Ball aBall = new Ball();

    @Test
    void instantiateBall() {
        assertEquals(aBall.getDiameter(), 0);
    }

    @Test
    void ballDiameterChange() {
        aBall.setDiameter(10);
        assertEquals(aBall.getDiameter(), 10);
    }


    @Test
    void initialiseBallWithDiameter() {
        aBall = new Ball(10);
        assertEquals(aBall.getDiameter(), 10);
    }

    @Test
    void ballsXposition() {
        assertEquals(aBall.getToX(), 0);

    }

    @Test
    void ballsXpositionChange() {
        aBall.setToX(10);
        assertEquals(aBall.getToX(), 10);
    }

    @Test
    void ballsYposition() {
        assertEquals(aBall.getToY(), 0);
    }

    @Test
    void ballsYpositionChange() {
        aBall.setToY(10);
        assertEquals(aBall.getToY(), 10);
    }

    @Test
    void ballsCoordinates() {
        int x = aBall.getToX();
        int y = aBall.getToY();
        assertArrayEquals(new int[] {x, y}, new int[] {0,0});
    }

    @Test
    void ballsCoordinatesAsAnArray() {
        int[] coords = aBall.getCoordinates();
        assertArrayEquals(new int[] {coords[0], coords[1]}, new int[] {0,0});
    }

    @Test
    void ballCoordinateChange() {
        aBall.setCoordinates(10, 10);
        int x = aBall.getToX();
        int y = aBall.getToY();
        assertArrayEquals(new int[] {x, y}, new int[] {10,10});
    }

    @Test
    void ballSpeed(){
        assertEquals(1, aBall.getSpeed());
    }

    @Test
    void ballSpeedIncrease() {
        aBall.increaseSpeed(2);
        assertEquals(3, aBall.getSpeed());
        aBall.increaseSpeed(4);
        assertEquals(7, aBall.getSpeed());

        // The highest speed a ball can travel is 10
        aBall.increaseSpeed(4);
        assertEquals(10, aBall.getSpeed());

    }

    @Test
    void ballSpeedDecrease() {
        aBall.increaseSpeed(4);

        aBall.decreaseSpeed(2);
        assertEquals(3, aBall.getSpeed());

        // the slowest speed a ball can go is 1
        aBall.decreaseSpeed(4);
        assertEquals(1, aBall.getSpeed());

    }

    @Test
    void ballDirection() {
        aBall.setDirectionAngel(30);
        assertEquals(aBall.getAngle(), 30);

        aBall.setDirectionAngel(60);
        assertEquals(aBall.getAngle(), 60);
    }



/*

    @Test
    void testBallCircuit45() {
        aBall.setBallBounds(200,200);
        aBall.setCoordinates(180, 190);
        aBall.setDirectionAngel(135);
        aBall.newXY();
        assertArrayEquals(new int[]{0, 10, 45}, new int[]{aBall.getX(), aBall.getY(), aBall.getAngle()});
        System.out.println(aBall.getAngle());
        aBall.newXY();
        assertArrayEquals(new int[]{10, 0, 315}, new int[]{aBall.getX(), aBall.getY(), aBall.getAngle()});
        System.out.println(aBall.getAngle());
        aBall.newXY();
        assertArrayEquals(new int[]{200, 200, 225}, new int[]{aBall.getX(), aBall.getY(), aBall.getAngle()});
        System.out.println(aBall.getAngle());
        aBall.newXY();
        System.out.println(aBall.getAngle());
    }

    /*
    @Test
    void testBallCircuit20() {
        aBall.setBallBounds(20,20);
        aBall.setCoordinates(1, 4);
        aBall.setDirectionAngel(20);
        System.out.println(aBall.getAngle());
        aBall.newXY();
        System.out.println();
        System.out.println(aBall.getX() +","+ aBall.getY());
        System.out.println(aBall.getAngle());
        assertTrue(true);


    }



    @Test
    void ballBoundery(){
        short[] bounds = aBall.getBallBoundary();
        assertArrayEquals(new short[] {100,100}, bounds);
    }

    @Test
    void ballBoundaryChange(){
        aBall.setBoundaryLength(200);
        aBall.SetBoundaryHeight(200);
        short[] bounds = aBall.getBallBoundary();
        assertArrayEquals(new short[] {200,200,}, bounds);
    }

*/


}