package pong;

/**
 * Used to create Net object, if a ball enters the net points can be awarded or deducted
 * from a Score object
 *
 * @author Lindsay Kerr
 * @version 0.1
 */

public class Net implements Impact, IScore {

    /* nets position */
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;

    /* normal on the facing side */
    private int normal;

    /* net affects a score */
    private Score score;


    public Net(int x1, int y1, int x2, int y2, int normal, Score score) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.normal = normal;
        this.score = score;

    }

    @Override
    public void setNormal(int normal) {
        this.normal = normal;
    }

    @Override
    public Impact testImpact(double x, double y) {
        if (x >= x1 && y >= y1 && x <= x2 && y <= y2) {
            return this;
        }
        return null;
    }

    @Override
    public int getNormal() {
        return normal;
    }

    @Override
    public String toString() {
        return "net";
    }


    public void updateScore(int i) {
        score.updateScore(i);
    }


    public int getScore() {
        return score.getScore();
    }



}
