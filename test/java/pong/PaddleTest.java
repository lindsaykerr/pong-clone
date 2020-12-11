package pong;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaddleTest {
    private final Paddle paddle = new Paddle();
    private final int y = paddle.getPosY();

    @Test
    void moveDown() {
        paddle.moveDown();
        assertEquals(y + 1, paddle.getPosY());
    }

    @Test
    void moveUp() {
        paddle.moveUp();
        assertEquals(y-1, paddle.getPosY());
    }

    @Test
    void setPosX() {
        paddle.setPosX(23);
        assertEquals(23, paddle.getPos()[0]);
    }

    @Test
    void setPosY() {
        paddle.setPosY(20);
        assertEquals(20, paddle.getPosY());
    }

    @Test
    void setHeight() {
        paddle.setHeight(50);
        assertEquals(50, paddle.getHeight());
    }

    @Test
    void setWidth() {
        paddle.setWidth(12);
        assertEquals(12, paddle.getWidth());
    }

    @Test
    void setYLimit() {
        paddle.setYLimit(245);
        assertEquals(245, paddle.yLimit);
    }

    @Test
    void getPos() {
        int[] xy = paddle.getPos();
        assertArrayEquals(new int[]{10, 10}, xy);
    }
/*
    @Test
    void getEdge() {
        int[] edge = paddle.getEdge();
        assertArrayEquals(new int[]{20, 10, 40}, edge);

    }
*/

}