package gameComponents;

import java.util.LinkedList;
import java.util.Optional;

import application.TheController;

public class GameBoardModel {
	private int waitTimeAfterBrickCollision = 3;
	private int levelNum;
	private final double LEFT_ANGLE_LIMIT = -35;
	private final double RIGHT_ANGLE_LIMIT = -150;
	private final int BAT_WIDTH;
	private final int BAT_HEIGHT;
	private final int BAT_SPEED;
	private double BALL_SPEED;
	private int brickHeight; // Similar to brickRowHeight but gap is included
	private int columnWidth;
	private int brickWidth;
	private Rectangle windowRectangle;
	private Rectangle bat;
	private LinkedList<Ball> balls = new LinkedList<Ball>();
	private LinkedList<Brick> bricks = new LinkedList<Brick>();
	private ICollisionDetector detector;

	public GameBoardModel(int levelNum, int width, int height, int brickRowHeight, int brickGapH, int brickGapV,
			int gapAboveBricks, int brickColumns, int brickRows, int batWidth, int batHeight, int batSpeed,
			double ballSpeed, ICollisionDetector detector) {
		this.levelNum = levelNum;
		this.detector = detector;
		this.windowRectangle = new Rectangle(new Coordinate(0, 0), TheController.getBoardWidth(),
				TheController.getBoardHeight(), RectangleType.Window);
		if (brickColumns != 0)
			this.columnWidth = (TheController.getBoardWidth() - 10) / brickColumns;
		this.brickWidth = columnWidth - brickGapH;
		this.brickHeight = brickRowHeight - brickGapV;
		this.BAT_WIDTH = batWidth;
		this.BAT_HEIGHT = batHeight;
		this.BAT_SPEED = batSpeed;
		this.BALL_SPEED = ballSpeed;
		// Brick creation algorithm
		for (int column = 0; column < brickColumns; column++) {
			for (int row = 0; row < brickRows; row++) {
				// The 4+ is so the bricks do not appear right on the edges
				Brick r = new Brick(
						new Coordinate(4 + (column * columnWidth) + (brickGapH / 2),
								(row * brickRowHeight) + gapAboveBricks),
						brickWidth, brickHeight, RectangleType.Twohit2, 0, 2);
				bricks.add(r);
			}
		}
		// Brick creation algorithm end
		Coordinate batUL = new Coordinate((TheController.getBoardWidth() - BAT_WIDTH) / 2,
				(TheController.getBoardHeight() - BAT_HEIGHT - 30));
		bat = new Rectangle(batUL, BAT_WIDTH, BAT_HEIGHT, RectangleType.Bat);
		balls.add(new Ball(new Coordinate(300, TheController.getBoardHeight() / 2), 10, 10, BALL_SPEED,
				Math.toRadians(285), RectangleType.Ball));
		balls.add(new Ball(new Coordinate(400, TheController.getBoardHeight() / 2), 10, 10, BALL_SPEED,
				Math.toRadians(190), RectangleType.Ball));
		balls.add(new Ball(new Coordinate(100, TheController.getBoardHeight() / 2), 10, 10, BALL_SPEED, Math.toRadians(10),
				RectangleType.Ball));
		balls.add(new Ball(new Coordinate(200, TheController.getBoardHeight() / 2), 10, 10, BALL_SPEED, Math.toRadians(70),
				RectangleType.Ball));
		balls.add(new Ball(new Coordinate(450, TheController.getBoardHeight() / 2), 10, 10, BALL_SPEED, Math.toRadians(25),
				RectangleType.Ball));
		balls.add(new Ball(new Coordinate(150, TheController.getBoardHeight() / 2), 10, 10, BALL_SPEED, Math.toRadians(35),
				RectangleType.Ball));
	}

	// This method is for manually adding bricks so that they do not have to be
	// in a dumb row
	public void addBrick(int width, int height, int posX, int posY, RectangleType type, int hitResistance) {
		Brick r = new Brick(new Coordinate(posX, posY), width, height, type, 0, hitResistance);
		bricks.add(r);
	}

	public void movePaddleLeft() {
		if ((bat.getTopLeftCoordinate().getX() - BAT_SPEED) >= 0) {
			bat = bat.createMove(-BAT_SPEED, 0, bat.getType());
		}
	}

	public void movePaddleRight() {
		if (bat.getBottomRightCoordinate().getX() + BAT_SPEED <= TheController.getBoardWidth()) {
			bat = bat.createMove(BAT_SPEED, 0, bat.getType());
		}
	}

	public void updateBricks() {
		// LinkedList<Ball> copyBalls = (LinkedList<Ball>) balls.clone();
		// for (Ball ball : copyBalls) {
		// if (ball.getBrickItHit() != null) {
		// if (ball.getBrickItHit().hitBrick().getHitCount() ==
		// ball.getBrickItHit().getHitResistance()) {
		// bricks.remove(ball.getBrickItHit());
		// bricks.add(ball.getBrickItHit().kill());
		// } else {
		// bricks.remove(ball.getBrickItHit());
		// bricks.add(ball.getBrickItHit().hitBrick());
		// }
		// balls.remove(ball);
		// balls.add(ball.setBrickItHit(null));
		// }
		// }
	}

	// Updating Ball and Destroying bricks

	public void calculateBall() {
		LinkedList<Ball> ballsCopy = (LinkedList<Ball>) balls.clone();
		Optional<Double> r = bricks.stream().map(i -> i.getBottomRightCoordinate().getY()).max(Double::compare);
		for (Ball ball : ballsCopy) {
			Ball nextBall = ball.getMove();
			LineSegment ballPathCenter = new LineSegment(ball.getCenterCoordinate(), nextBall.getCenterCoordinate());
			LineSegment ballPathTopLeft = new LineSegment(ball.getTopLeftCoordinate(), nextBall.getTopLeftCoordinate());
			LineSegment ballPathBottomRight = new LineSegment(ball.getBottomRightCoordinate(),
					nextBall.getBottomRightCoordinate());
			LineSegment ballPathTopRight = new LineSegment(ball.getTopRightCoordinate(),
					nextBall.getTopRightCoordinate());
			LineSegment ballPathBottomLeft = new LineSegment(ball.getBottomLeftCoordinate(),
					nextBall.getBottomLeftCoordinate());

			// ball=ball.setHitBat(false);
			if (detector.intersects(ballPathTopLeft, windowRectangle.getLeftLineSegment())
					|| detector.intersects(ballPathTopRight, windowRectangle.getRightLineSegment())
					|| detector.intersects(ballPathBottomRight, windowRectangle.getRightLineSegment())
					|| detector.intersects(ballPathBottomLeft, windowRectangle.getLeftLineSegment())) {
				balls.remove(ball);
				balls.add(ball.flipXDirection());
			}

			else if (detector.intersects(ballPathTopRight, windowRectangle.getTopLineSegment())
					|| detector.intersects(ballPathTopLeft, windowRectangle.getTopLineSegment())
					|| detector.intersects(ballPathBottomLeft, windowRectangle.getBottomLineSegment())
					|| detector.intersects(ballPathBottomRight, windowRectangle.getBottomLineSegment())
					) {
				balls.remove(ball);
				balls.add(ball.flipYDirection());
			}
			// Variables that will let the ball know what new object to be

			// If Ball made a collision with the bat, then the bat will not be
			// able
			// to cause collision untill ball is above bat's topLeftY coordinate

			// Bat collision detection
			else if ((detector.intersects(ballPathBottomLeft, bat.getTopLineSegment())
					|| detector.intersects(ballPathBottomRight, bat.getTopLineSegment())
					|| detector.intersects(ballPathCenter, bat.getTopLineSegment()))
					//&& ball.getBottomLeftCoordinate().getY() >= bat.getTopLeftCoordinate().getY()
					) {
				Ball ball2 = ball.flipYDirection().changeAngleDegrees(ball.calculatePercentageOffsetWith(bat));
				if (ball2.getAngleInDegrees() > LEFT_ANGLE_LIMIT) {
					ball2 = ball2.setAngleInDegrees(LEFT_ANGLE_LIMIT);
				} else if (ball2.getAngleInDegrees() < RIGHT_ANGLE_LIMIT) {
					ball2 = ball2.setAngleInDegrees(RIGHT_ANGLE_LIMIT);
				}
				// ball=ball.shouldFlipY();
				balls.remove(ball);
				balls.add(ball2);
				// ball=ball.setHitBat(true);
				// canUseBat = false;
			} else if (detector.intersects(ballPathBottomLeft, bat.getRightLineSegment())
					|| detector.intersects(ballPathBottomRight, bat.getRightLineSegment())
					|| detector.intersects(ballPathCenter, bat.getRightLineSegment())
					|| detector.intersects(ballPathBottomLeft, bat.getLeftLineSegment())
					|| detector.intersects(ballPathBottomRight, bat.getLeftLineSegment())
					|| detector.intersects(ballPathCenter, bat.getLeftLineSegment())
					//&& ball.getBottomLeftCoordinate().getY() >= bat.getTopLeftCoordinate().getY()
					) {
				Ball ball2 = ball.flipXDirection().flipYDirection()
						.changeAngleDegrees(ball.calculatePercentageOffsetWith(bat));
				if (ball2.getAngleInDegrees() > LEFT_ANGLE_LIMIT) {
					ball2 = ball2.setAngleInDegrees(LEFT_ANGLE_LIMIT);
				} else if (ball2.getAngleInDegrees() < RIGHT_ANGLE_LIMIT) {
					ball2 = ball2.setAngleInDegrees(RIGHT_ANGLE_LIMIT);
				}
				// ball=ball.shouldFlipY();
				balls.remove(ball);
				balls.add(ball2);
				// ball=ball.setHitBat(true);
			}
			// Bat collision detection end

			// Brick collision detection
			else if (true) {
				if (true) {
					LinkedList<Brick> copyBricks = (LinkedList<Brick>) bricks.clone();
					for (Brick brick : copyBricks) {
						if (brick.isAlive() && detector.basicIntersects(nextBall, brick)) {
							if (brick.hitBrick().getHitCount() == brick.getHitResistance()) {
								bricks.add(brick.kill());
								bricks.remove(brick);
							} else {
								bricks.add(brick.hitBrick());
								bricks.remove(brick);
							}
							int xScore = xScore(brick, ball, nextBall);
							int yScore = yScore(brick, ball, nextBall);
							if (xScore > yScore) {
								// ball = ball.shouldFlipX();
								balls.remove(ball);
								balls.add(ball.flipXDirection().setBrickItHit(brick));

							} else if (yScore > xScore) {
								// ball = ball.shouldFlipY();
								balls.remove(ball);
								balls.add(ball.flipYDirection().setBrickItHit(brick));

							} else if (xScore == yScore) {
								System.out.println("XY");
								balls.remove(ball);
								balls.add(ball.flipYDirection().setBrickItHit(brick));

							}
							// ball=ball.setBrickCollisionCounter(0);
							// ball=ball.setBrickItHit(brick);
							// ball=ball.setHitBrick(true);
							// ball=ball.setScore(xScore, yScore);
						}
					}

				}
			}
			if (ball.getBottomLeftCoordinate().getY() > TheController.getBoardHeight()) {
				balls.remove(ball);
			}
		}

	}

	// Brick collision detection end
	// Move balls to new positions
	public void moveBalls() {
		LinkedList<Ball> ballsCopy = (LinkedList<Ball>) balls.clone();
		for (Ball ball : ballsCopy) {
			if (ball.getShouldFlipX() == false && ball.getShouldFlipY() == false) {
				// ball = ball.getMove();
				balls.add(ball.getMove());
				balls.remove(ball);
				// ball = ball.getMove();
				// ball=ball.setBrickCollisionCounter(ball.getBrickCollisionCounter()
				// + 1);
				// if (ball.getBrickCollisionCounter() >
				// waitTimeAfterBrickCollision) {
				// ball=ball.setBrickCollisionCounter(waitTimeAfterBrickCollision);
				// }

			}
		}
	}

	// Update Ball End

	public void updateAll() {
		calculateBall();
		moveBalls();
		updateBricks();
	}

	public Rectangle getBat() {
		return bat;
	}

	public LinkedList<Ball> getBalls() {
		return balls;
	}

	public LinkedList<Brick> getBricks() {
		return new LinkedList<Brick>(this.bricks);
	}

	public int getLevelNum() {
		return levelNum;
	}

	public int yScore(Brick brick, Ball ball, Ball nextBall) {
		JavaCollisionDetector detector = new JavaCollisionDetector();
		ball.setAdjustments(ball, ball.getXAdjustment());
		int count = 0;

		LineSegment ballPathCenter = new LineSegment(ball.getCenterCoordinate(), nextBall.getCenterCoordinate());
		LineSegment ballPathTopLeft = new LineSegment(ball.getTopLeftCoordinate(), nextBall.getTopLeftCoordinate());
		LineSegment ballPathBottomRight = new LineSegment(ball.getBottomRightCoordinate(),
				nextBall.getBottomRightCoordinate());
		LineSegment ballPathTopRight = new LineSegment(ball.getTopRightCoordinate(), nextBall.getTopRightCoordinate());
		LineSegment ballPathBottomLeft = new LineSegment(ball.getBottomLeftCoordinate(),
				nextBall.getBottomLeftCoordinate());

		if (detector.intersects(ballPathTopLeft, brick.getTopLineSegment())) {
			if (ball.getBottomLeftCoordinate().getY() >= brick.getTopLeftCoordinate().getY()) {
				ball.setAdjustments(ball.setPosition(ball.getTopLeftCoordinate().getX(),
						(brick.getTopLeftCoordinate().getY() - ball.getHeight() - 0.1)), ball.getXAdjustment());

			}
			count++;
		}
		if (detector.intersects(ballPathBottomRight, brick.getTopLineSegment())) {
			if (ball.getBottomLeftCoordinate().getY() >= brick.getTopLeftCoordinate().getY()) {
				ball.setAdjustments(ball.setPosition(ball.getTopLeftCoordinate().getX(),
						(brick.getTopLeftCoordinate().getY() - ball.getHeight() - 0.1)), ball.getXAdjustment());
			}
			count++;
		}
		if (detector.intersects(ballPathTopRight, brick.getTopLineSegment())) {
			if (ball.getBottomLeftCoordinate().getY() >= brick.getTopLeftCoordinate().getY()) {
				ball.setAdjustments(ball.setPosition(ball.getTopLeftCoordinate().getX(),
						(brick.getTopLeftCoordinate().getY() - ball.getHeight() - 0.1)), ball.getXAdjustment());
			}
			count++;
		}
		if (detector.intersects(ballPathBottomLeft, brick.getTopLineSegment())) {
			if (ball.getBottomLeftCoordinate().getY() >= brick.getTopLeftCoordinate().getY()) {
				ball.setAdjustments(ball.setPosition(ball.getTopLeftCoordinate().getX(),
						(brick.getTopLeftCoordinate().getY() - ball.getHeight() - 0.1)), ball.getXAdjustment());

			}
			count++;
		}
		if (detector.intersects(ballPathCenter, brick.getTopLineSegment())) {
			if (ball.getBottomLeftCoordinate().getY() >= brick.getTopLeftCoordinate().getY()) {
				ball.setAdjustments(ball.setPosition(ball.getTopLeftCoordinate().getX(),
						(brick.getTopLeftCoordinate().getY() - ball.getHeight() - 0.1)), ball.getXAdjustment());

			}
			count++;
		}
		if (detector.intersects(ballPathTopLeft, brick.getBottomLineSegment())) {
			if (ball.getTopLeftCoordinate().getY() <= brick.getBottomLeftCoordinate().getY()) {
				ball.setAdjustments(ball.setPosition(ball.getTopLeftCoordinate().getX(),
						(brick.getBottomLeftCoordinate().getY() + 0.1)), ball.getXAdjustment());

			}
			count++;
		}
		if (detector.intersects(ballPathBottomRight, brick.getBottomLineSegment())) {
			if (ball.getTopLeftCoordinate().getY() <= brick.getBottomLeftCoordinate().getY()) {
				ball.setAdjustments(ball.setPosition(ball.getTopLeftCoordinate().getX(),
						(brick.getBottomLeftCoordinate().getY() + 0.1)), ball.getXAdjustment());

			}
			count++;
		}
		if (detector.intersects(ballPathTopRight, brick.getBottomLineSegment())) {
			if (ball.getTopLeftCoordinate().getY() <= brick.getBottomLeftCoordinate().getY()) {
				ball.setAdjustments(ball.setPosition(ball.getTopLeftCoordinate().getX(),
						(brick.getBottomLeftCoordinate().getY() + 0.1)), ball.getXAdjustment());

			}
			count++;
		}
		if (detector.intersects(ballPathBottomLeft, brick.getBottomLineSegment())) {
			if (ball.getTopLeftCoordinate().getY() <= brick.getBottomLeftCoordinate().getY()) {
				ball.setAdjustments(ball.setPosition(ball.getTopLeftCoordinate().getX(),
						(brick.getBottomLeftCoordinate().getY() + 0.1)), ball.getXAdjustment());

			}
			count++;
		}
		if (detector.intersects(ballPathCenter, brick.getBottomLineSegment())) {
			if (ball.getTopLeftCoordinate().getY() <= brick.getBottomLeftCoordinate().getY()) {
				ball.setAdjustments(ball.setPosition(ball.getTopLeftCoordinate().getX(),
						(brick.getBottomLeftCoordinate().getY() + 0.1)), ball.getXAdjustment());
			}
			count++;
		}
		return count;
	}

	public int xScore(Brick brick, Ball ball, Ball nextBall) {
		LineSegment ballPathCenter = new LineSegment(ball.getCenterCoordinate(), nextBall.getCenterCoordinate());
		LineSegment ballPathTopLeft = new LineSegment(ball.getTopLeftCoordinate(), nextBall.getTopLeftCoordinate());
		LineSegment ballPathBottomRight = new LineSegment(ball.getBottomRightCoordinate(),
				nextBall.getBottomRightCoordinate());
		LineSegment ballPathTopRight = new LineSegment(ball.getTopRightCoordinate(), nextBall.getTopRightCoordinate());
		LineSegment ballPathBottomLeft = new LineSegment(ball.getBottomLeftCoordinate(),
				nextBall.getBottomLeftCoordinate());

		int count = 0;
		ball.setAdjustments(ball.getYAdjustment(), ball);
		if (detector.intersects(ballPathTopLeft, brick.getRightLineSegment())) {
			if (ball.getBottomLeftCoordinate().getX() <= brick.getBottomLeftCoordinate().getX()) {
				ball.setAdjustments(ball.getYAdjustment(), ball.setPosition(
						brick.getBottomLeftCoordinate().getX() - 0.1, (ball.getTopLeftCoordinate().getY())));

			}
			count++;
		}
		if (detector.intersects(ballPathBottomRight, brick.getRightLineSegment())) {
			if (ball.getBottomLeftCoordinate().getX() <= brick.getBottomLeftCoordinate().getX()) {
				ball.setAdjustments(ball.getYAdjustment(), ball.setPosition(
						brick.getBottomLeftCoordinate().getX() - 0.1, (ball.getTopLeftCoordinate().getY())));

			}
			count++;
		}
		if (detector.intersects(ballPathTopRight, brick.getRightLineSegment())) {
			if (ball.getBottomLeftCoordinate().getX() <= brick.getBottomLeftCoordinate().getX()) {
				ball.setAdjustments(ball.getYAdjustment(), ball.setPosition(
						brick.getBottomLeftCoordinate().getX() - 0.1, (ball.getTopLeftCoordinate().getY())));

			}
			count++;
		}
		if (detector.intersects(ballPathBottomLeft, brick.getRightLineSegment())) {
			if (ball.getBottomLeftCoordinate().getX() <= brick.getBottomLeftCoordinate().getX()) {
				ball.setAdjustments(ball.getYAdjustment(), ball.setPosition(
						brick.getBottomLeftCoordinate().getX() - 0.1, (ball.getTopLeftCoordinate().getY())));

			}
			count++;
		}
		if (detector.intersects(ballPathCenter, brick.getRightLineSegment())) {
			if (ball.getBottomLeftCoordinate().getX() <= brick.getBottomLeftCoordinate().getX()) {
				ball.setAdjustments(ball.getYAdjustment(), ball.setPosition(
						brick.getBottomLeftCoordinate().getX() - 0.1, (ball.getTopLeftCoordinate().getY())));
			}
			count++;
		}
		if (detector.intersects(ballPathTopLeft, brick.getLeftLineSegment())) {
			if (ball.getBottomRightCoordinate().getX() >= brick.getBottomRightCoordinate().getX()) {
				ball.setAdjustments(ball.getYAdjustment(),
						ball.setPosition(brick.getBottomRightCoordinate().getX() - ball.getWidth() + 0.1,
								(ball.getTopLeftCoordinate().getY())));
			}
			count++;
		}
		if (detector.intersects(ballPathBottomRight, brick.getLeftLineSegment())) {
			if (ball.getBottomRightCoordinate().getX() >= brick.getBottomRightCoordinate().getX()) {
				ball.setAdjustments(ball.getYAdjustment(),
						ball.setPosition(brick.getBottomRightCoordinate().getX() - ball.getWidth() + 0.1,
								(ball.getTopLeftCoordinate().getY())));
			}
			count++;
		}
		if (detector.intersects(ballPathTopRight, brick.getLeftLineSegment())) {
			if (ball.getBottomRightCoordinate().getX() >= brick.getBottomRightCoordinate().getX()) {
				ball.setAdjustments(ball.getYAdjustment(),
						ball.setPosition(brick.getBottomRightCoordinate().getX() - ball.getWidth() + 0.1,
								(ball.getTopLeftCoordinate().getY())));
			}
			count++;
		}
		if (detector.intersects(ballPathBottomLeft, brick.getLeftLineSegment())) {
			if (ball.getBottomRightCoordinate().getX() >= brick.getBottomRightCoordinate().getX()) {
				ball.setAdjustments(ball.getYAdjustment(),
						ball.setPosition(brick.getBottomRightCoordinate().getX() - ball.getWidth() + 0.1,
								(ball.getTopLeftCoordinate().getY())));
			}
			count++;
		}
		if (detector.intersects(ballPathCenter, brick.getLeftLineSegment())) {
			if (ball.getBottomRightCoordinate().getX() >= brick.getBottomRightCoordinate().getX()) {
				ball.setAdjustments(ball.getYAdjustment(),
						ball.setPosition(brick.getBottomRightCoordinate().getX() - ball.getWidth() + 0.1,
								(ball.getTopLeftCoordinate().getY())));
			}
			count++;
		}
		return count;
	}

}
