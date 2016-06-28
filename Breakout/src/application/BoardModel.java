package application;

import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.Optional;

public class BoardModel {
	private final int WIDTH;
	private final int HEIGHT;
	private final int BAT_WIDTH = 40;
	private final int BAT_HEIGHT = 10;
	private final int BAT_SPEED = 10;
	private int gapAboveBricks;
	private int brickRowHeight; // The height of a row of bricks, gap is not included
	private int brickHeight; // Similar to brickRowHeight but gap is included
	private int brickGap; // The gap that will separate the bricks
	private int brickColumns; // The number of columns of bricks
	private int brickRows; // The number of rows of bricks
	private int columnWidth;
	private int brickWidth;
	private Rectangle bat;
	private Ball ball;
	private LinkedList<Rectangle> bricks = new LinkedList<Rectangle>();
	private LinkedList<Rectangle> destroyedBricks = new LinkedList<Rectangle>();

	public LinkedList<Rectangle> getDestroyedBricks() {
		return destroyedBricks;
	}

	private ICollisionDetector detector;

	public BoardModel(int width, int height, int brickRowHeight, int brickGap, int gapAboveBricks, int brickColumns,
			int brickRows) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.detector = new JavaCollisionDetecter();
		this.brickColumns = brickColumns;
		this.brickRows = brickRows;
		this.brickRowHeight = brickRowHeight;
		this.brickGap = brickGap;
		this.gapAboveBricks = gapAboveBricks;
		this.columnWidth = WIDTH / brickColumns;
		this.brickWidth = columnWidth - brickGap;
		this.brickHeight = brickRowHeight - brickGap;
		for (int column = 0; column < brickColumns; column++) {
			for (int row = 0; row < brickRows; row++) {
				Rectangle r = new Rectangle(
						new Coordinate(column * columnWidth, (row * brickRowHeight) + gapAboveBricks), brickWidth,
						brickHeight);
				bricks.add(r);
			}
		}
		Coordinate batUL = new Coordinate((WIDTH - BAT_WIDTH) / 2, (HEIGHT - BAT_HEIGHT));
		bat = new Rectangle(batUL, BAT_WIDTH, BAT_HEIGHT);
		ball = new Ball(new Coordinate(WIDTH / 2, HEIGHT / 2), 7, 7, 1, 2);
	}

	public void movePaddleLeft() {
		if ((bat.getTopLeftCoordinate().getX() - BAT_SPEED) >= 0) {
			bat = bat.createMove(-BAT_SPEED, 0);
		}
	}

	public void movePaddleRight() {
		if (bat.getBottomRightCoordinate().getX() + BAT_SPEED <= WIDTH) {
			bat = bat.createMove(BAT_SPEED, 0);
		}
	}

	public void updateBricks() {
		for (Rectangle b : destroyedBricks) {
			b.setOpacity(0);
			b.setAlive(false);
		}
	}

	public void updateBall() {
		Ball nextBall = ball.getMove();
		Ball oldBall = ball;
		boolean shouldFlipX = nextBall.getTopLeftCoordinate().getX() <= 0 || nextBall.getBottomRightCoordinate().getX() >= WIDTH;
		boolean shouldFlipY = nextBall.getBottomRightCoordinate().getY() <= 0 || nextBall.getTopLeftCoordinate().getY() >= HEIGHT;
		Optional<Double> r = bricks.stream().map(i -> i.getBottomRightCoordinate().getY()).max(Double::compare);
		// Variables that will let the ball know what new object to be

		// Wall collision detection
		if (shouldFlipX == true && shouldFlipY == true) {
			ball = ball.flipXDirection().flipYDirection();
		} else if (shouldFlipX == true && shouldFlipY == false) {
			ball = ball.flipXDirection();
		} else if (shouldFlipX == false && shouldFlipY == true) {
			ball = ball.flipYDirection();
		}
		// Wall collision detection end

		// Bat collision detection
		else if (detector.intersects(ball, bat)) {
			ball = ball.flipYDirection();
		}
		// Bat collision detection end
		else if (r.get() >= ball.getTopLeftCoordinate().getY()) {
			for (Rectangle brick : bricks) {
				if (brick.isAlive()) {
					boolean collisionOccured = false;
						LineSegment ballPath = new LineSegment(ball.getCenterCoordinate(),
							ball.getMove().getCenterCoordinate());
						if(ballPath.intersects(brick.getBottomLineSegement())||
						ballPath.intersects(brick.getTopLineSegment())){
						 ball = ball.flipYDirection();
						 collisionOccured = true;
						System.out.println("BT");
						}
						if(ballPath.intersects(brick.getLeftLineSegment())
							||ballPath.intersects(brick.getRightLineSegment())){
							ball = ball.flipXDirection();
							System.out.println("RL");
							collisionOccured = true;
						}
						if(collisionOccured == true){
							destroyedBricks.add(brick);
						}

					}
				}
		}
	 	if (ball == oldBall) {
		ball = nextBall;
	 	}
	}

	public Rectangle getBat() {
		return bat;
	}

	public Rectangle getBall() {
		return ball;
	}

	public void update() {
		updateBall();
		updateBricks();
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public LinkedList<Rectangle> getBricks() {
		return new LinkedList<Rectangle>(this.bricks);
	}
}

interface ICollisionDetector {
	boolean intersects(Rectangle r1, Rectangle r2);
}

class JavaCollisionDetecter implements ICollisionDetector {

	@Override
	public boolean intersects(Rectangle r1, Rectangle r2) {
		if (r1.getTopLeftCoordinate().getX() < r2.getTopLeftCoordinate().getX() + r2.getWidth()
				&& r1.getTopLeftCoordinate().getX() + r1.getWidth() > r2.getTopLeftCoordinate().getX()
				&& r1.getTopLeftCoordinate().getY() < r2.getTopLeftCoordinate().getY() + r2.getHeight()
				&& r1.getHeight() + r1.getTopLeftCoordinate().getY() > r2.getTopLeftCoordinate().getY()) {
			return true;

		} else
			return false;
	}
}
