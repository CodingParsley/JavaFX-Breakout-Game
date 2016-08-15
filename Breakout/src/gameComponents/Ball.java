package gameComponents;

public class Ball extends Rectangle {
	private double speed;
	private double angleOfMovement;
	private boolean shouldFlipX = false;
	private boolean shouldFlipY = false;
	private int xScore = 0;
	private int yScore = 0;
	private Brick brickItHit = null;
	private Ball yAdjustment = null;
	private Ball xAdjustment = null;
	private boolean hitBrick = false;
	private boolean hitBat = false;
	private int brickCollisionCounter = 0;

	// For creating a brand new ball
	public Ball(Coordinate topLeft, double width, double height, double speed, double angleOfMovement,
			RectangleType type) {
		super(topLeft, width, height, type);
		this.speed = speed;
		this.angleOfMovement = angleOfMovement;
	}

	private Ball(Coordinate topLeft, double width, double height, RectangleType type, boolean shouldFlipX,
			boolean shouldFlipY, boolean hitBat, Brick brickItHit, Ball xAdjustment, Ball yAdjustment, int xScore, int yScore, int id, int brickCollisionCounter, double angleOfMovement, double speed) {
		super(topLeft, width, height,id, type);
		this.shouldFlipX = shouldFlipX;
		this.shouldFlipY = shouldFlipY;
		this.hitBat=hitBat;
		this.brickItHit=brickItHit;
		this.xAdjustment=xAdjustment;
		this.yAdjustment=yAdjustment;
		this.xScore=xScore;
		this.yScore=yScore;
		this.brickCollisionCounter=brickCollisionCounter;
		this.angleOfMovement=angleOfMovement;
		this.speed=speed;
	}
	private Ball(Coordinate topLeft, double width, double height, RectangleType type, boolean hitBrick) {
		super(topLeft, width, height, type);
		this.hitBrick=hitBrick;
	}

	public Ball setBrickCollisionCounter(int brickCollisionCounter){
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.getType(), true,
				this.shouldFlipY, this.hitBat,this.brickItHit, this.xAdjustment,this.yAdjustment,this.xScore,this.yScore,this.getId(),brickCollisionCounter,this.getAngleOfMovement(),this.getSpeed());
	}

	public Ball shouldFlipX() {
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.getType(), true,
				this.shouldFlipY, this.hitBat,this.brickItHit, this.xAdjustment,this.yAdjustment,this.xScore,this.yScore,this.getId(),this.brickCollisionCounter,this.getAngleOfMovement(),this.getSpeed());
	}

	public Ball shouldFlipY() {
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.getType(), this.shouldFlipX,
				true, this.hitBat,this.brickItHit, this.xAdjustment,this.yAdjustment,this.xScore,this.yScore,this.getId(),this.brickCollisionCounter,this.getAngleOfMovement(),this.getSpeed());
	}

	public Ball setScore(int xScore, int yScore) {
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.getType(), this.shouldFlipX,
				this.shouldFlipY, this.hitBat, brickItHit, this.xAdjustment,this.yAdjustment,xScore,yScore,this.getId(),this.brickCollisionCounter,this.getAngleOfMovement(),this.getSpeed());
	}

	public Ball setBrickItHit(Brick brickItHit) {
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.getType(), this.shouldFlipX,
				this.shouldFlipY, this.hitBat, brickItHit, this.xAdjustment,this.yAdjustment,this.xScore,this.yScore,this.getId(),this.brickCollisionCounter,this.getAngleOfMovement(),this.getSpeed());
	}
	public Ball setHitBrick(boolean hitBrick){
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.getType(), true,
				this.shouldFlipY, this.hitBat,this.brickItHit, this.xAdjustment,this.yAdjustment,this.xScore,this.yScore,this.getId(),this.brickCollisionCounter,this.getAngleOfMovement(),this.getSpeed());
	}

	public Ball setAdjustments(Ball yAdj, Ball xAdj) {
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.getType(), this.shouldFlipX,
				this.shouldFlipY, hitBat, this.brickItHit,xAdj,yAdj,this.xScore,this.yScore,this.getId(),this.brickCollisionCounter,this.getAngleOfMovement(),this.getSpeed());
	}

	public Ball setHitBat(boolean hitBat){
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.getType(), this.shouldFlipX,
				this.shouldFlipY, hitBat, this.brickItHit, this.xAdjustment,this.yAdjustment,this.xScore,this.yScore,this.getId(),this.brickCollisionCounter,this.getAngleOfMovement(),this.getSpeed());
	}

	public Ball resetData() {
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.getType(), false,
				false, false, null, null,null,0,0,this.getId(),this.brickCollisionCounter,this.getAngleOfMovement(),this.getSpeed());
	}

	public Ball getMove() {
		return new Ball(this.getTopLeftCoordinate().getMoveVelocity(angleOfMovement, speed), this.getWidth(), this.getHeight(), this.getType(), this.shouldFlipX,
				this.shouldFlipY, this.hitBat, this.brickItHit, this.xAdjustment,this.yAdjustment,this.xScore,this.yScore,this.getId(),this.brickCollisionCounter,
				this.getAngleOfMovement(),this.getSpeed());
	}

	public Ball flipXDirection() {
		double cos = Math.cos(angleOfMovement);
		double sin = Math.sin(angleOfMovement);
		double newAngle = Math.atan2(sin, -cos);
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.getType(), this.shouldFlipX,
				this.shouldFlipY, this.hitBat, this.brickItHit, this.xAdjustment,this.yAdjustment,this.xScore,this.yScore,this.getId(),this.brickCollisionCounter,newAngle,this.getSpeed());
	}

	public Ball flipYDirection() {
		double cos = Math.cos(angleOfMovement);
		double sin = Math.sin(angleOfMovement);
		double newAngle = Math.atan2(-sin, cos);
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.getType(), this.shouldFlipX,
				this.shouldFlipY, this.hitBat, this.brickItHit, this.xAdjustment,this.yAdjustment,this.xScore,this.yScore,this.getId(),this.brickCollisionCounter,newAngle,this.getSpeed());
	}

	public Ball changeAngleDegrees(double changeAmount) {
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.getType(), this.shouldFlipX,
				this.shouldFlipY, this.hitBat, this.brickItHit, this.xAdjustment,this.yAdjustment,this.xScore,this.yScore,this.getId(),this.brickCollisionCounter,(this.angleOfMovement + Math.toRadians(changeAmount)),this.getSpeed());
	}

	public Ball setPosition(double setX, double setY) {
		return new Ball(new Coordinate(setX,setY), this.getWidth(), this.getHeight(), this.getType(), this.shouldFlipX,
				this.shouldFlipY, this.hitBat, this.brickItHit, this.xAdjustment,this.yAdjustment,this.xScore,this.yScore,this.getId(),this.brickCollisionCounter,this.angleOfMovement,this.getSpeed());
	}

	public Ball setAngleInDegrees(double changeAmount) {
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.getType(), this.shouldFlipX,
				this.shouldFlipY, this.hitBat, this.brickItHit, this.xAdjustment,this.yAdjustment,this.xScore,this.yScore,this.getId(),this.brickCollisionCounter,(Math.toRadians(changeAmount)),this.getSpeed());
	}

	public double calculatePercentageOffsetWith(Rectangle r) {
		// Formula is (centerCoordinate of ball / (1/2) r's width)*50 =
		// percentage offset from -50% to +50%
		double offset = this.getCenterCoordinate().getX() - r.getCenterCoordinate().getX();
		return (offset / (0.5 * r.getWidth())) * 50;
	}

	public double getAngleOfMovement() {
		return angleOfMovement;
	}

	public double getAngleInDegrees() {
		return Math.toDegrees(angleOfMovement);
	}

	public double getSpeed() {
		return speed;
	}

	public int getXScore() {
		return xScore;
	}

	public int getYScore() {
		return yScore;
	}

	public boolean getShouldFlipX() {
		return shouldFlipX;
	}

	public boolean getShouldFlipY() {
		return shouldFlipY;
	}

	public Brick getBrickItHit() {
		return brickItHit;
	}

	public Ball getXAdjustment() {
		return yAdjustment;
	}

	public Ball getYAdjustment() {
		return xAdjustment;
	}

	public boolean didHitBrick() {
		return hitBrick;
	}

	public boolean getHitBat() {
		return hitBat;
	}

	public int getBrickCollisionCounter() {
		return brickCollisionCounter;
	}

}