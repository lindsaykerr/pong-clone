package pong;

public class PongGame {
    public Board bd;
    public Player[] players = new Player[2];
    public Paddle[] paddles = new Paddle[2];
    public Net[] nets = new Net[2];
    public Ball pongBall;
    public Impact[] impactObject = new Impact[4];
    public Score[] scores = new Score[2];
    public boolean gameEnds = false;
    public boolean isSinglePlayerGame = true;

    public PongGame(int boardWidth, int boardHeight, boolean singlePlayer) {

        bd = new Board(boardWidth, boardHeight);
        pongBall = new Ball((int)(boardWidth * .03));
        isSinglePlayerGame = singlePlayer;

        if (singlePlayer) {
            paddles[0] = new Paddle(
                    (int)(boardWidth * 0.05),
                    (int)(boardHeight / 2.0 - (boardHeight * .15) / 2.0),
                    (int)(boardWidth * .025),
                    (int)(boardHeight * .15),
                    0,
                    boardHeight,
                    (int)(boardHeight * .02)
            );
            impactObject[0] = paddles[0];
            scores[0] = new Score(5);
            nets[0] = new Net(0, 0, (int)(boardWidth * 0.02), boardHeight, 0, scores[0]);
            players[0] = new Player(paddles[0], scores[0]);
            impactObject[1] = nets[0];

            serveBall();


        }
    }

    /**
     * Serves ball at a random angle
     */
    public void serveBall() {
        pongBall.setFromX(0);
        pongBall.setFromY(0);
        pongBall.setX( (int) (bd.getLength() * .15));
        pongBall.setY( (int) (bd.getHeight() * .7));
        pongBall.setDirectionAngel(300+(int)(Math.random()*40));
    }

    /**
     * Checks to the see if the XY coordinate do collied with any
     * Impact object registered by the game
     * @param x coordinate
     * @param y coordinate
     * @return null or the Impact object
     */
    public Impact isThereABallImpact(double x, double y) {
        Impact ahit = null;

        for (Impact impact : impactObject) {

            if (impact != null) {
                ahit = impact.testImpact(x, y);
                if (ahit != null) break;
            }
        }
        return ahit;
    }

    /**
     * Updates player score from an Net object
     * @param net a object
     */
    public void registerScore(Net net) {
        if (net.getScore() - 1 == 0)
            gameEnds = true;
        else {
            pongBall.resetSpeed();
            net.updateScore(-1);
        }

        if (isSinglePlayerGame && !gameEnds)
            System.out.println("Player score: " + players[0].getScore());
    }


}
