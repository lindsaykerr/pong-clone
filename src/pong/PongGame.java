package pong;

public class PongGame {
    public Board bd;
    public Player[] players = new Player[2];
    public Paddle[] paddles = new Paddle[2];
    public Net[] nets = new Net[2];
    public Ball ball;
    public Impact[] impactObject = new Impact[4];
    public Score[] scores = new Score[2];
    public boolean isActive = false;
    public boolean isSinglePlayerGame = true;

    public PongGame(int boardWidth, int boardHeight, boolean singlePlayer) {

        bd = new Board(boardWidth, boardHeight);
        ball = new Ball((int)(boardWidth * .03));
        ball.setBallBounds(boardWidth, boardHeight);
        isSinglePlayerGame = singlePlayer;

        if (singlePlayer) {
            paddles[0] = new Paddle(
                    (int)(boardWidth * 0.05),
                    (int)(boardHeight / 2.0 - (boardHeight * .1) / 2.0),
                    (int)(boardWidth * .025),
                    (int)(boardHeight * .1),
                    0,
                    boardHeight,
                    (int)(boardHeight * .04)
            );
            impactObject[0] = paddles[0];
            scores[0] = new Score(5);
            nets[0] = new Net(0, 0, (int)(boardWidth * 0.01), boardHeight-50, 0, scores[0]);
            players[0] = new Player(paddles[0], scores[0]);
            impactObject[1] = nets[0];
        }
    }

    public void start(){
        isActive = true;
        serveBall();
    }

    /**
     * Serves ball at a random angle
     */
    public void serveBall() {
        ball.setFromX(0);
        ball.setFromY(0);
        ball.setPosX(10);
        ball.setPosX(10);
        ball.setToX( (int) (bd.getLength() * .15));
        ball.setToY( (int) (bd.getHeight() * .7));
        ball.setPosX(ball.getToX()/2);
        ball.setPosY(ball.getToY()/2);
        ball.setDirectionAngel(315+(int)(Math.random()*40));
        ball.nextMove();
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
            isActive = false;
        else {
            ball.resetSpeed();
            net.updateScore(-1);
        }

        if (isSinglePlayerGame && !isActive)
            System.out.println("Player score: " + players[0].getScore());
    }


}
