package application;
public class Ball extends Rectangle {
	private double speed;
	private double angleOfMovement;
	public double getSpeed() {
		return speed;
	}
	//For creating a brand new ball
	public Ball(Coordinate topLeft, double width, double height, double speed, double angleOfMovement) {
		super(topLeft, width, height);
		this.speed = speed;
		this.angleOfMovement = angleOfMovement;
	}
	private Ball(Coordinate topLeft, double width, double height, double speed, double angleOfMovement, int id) {
		super(topLeft, width, height,id);
		this.speed = speed;
		this.angleOfMovement = angleOfMovement;
	}

	public Ball getMove() {
		return new Ball(this.getTopLeftCoordinate().getMoveVelocity(angleOfMovement, speed), this.getWidth(), this.getHeight(),
		speed, angleOfMovement,this.getId());
	}
	public Ball flipXDirection() {
		double cos = Math.cos(angleOfMovement);
		double sin = Math.sin(angleOfMovement);
		double newAngle = Math.atan2(sin, -cos);
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.speed, newAngle, this.getId());
	}

	public Ball flipYDirection() {
		double cos = Math.cos(angleOfMovement);
		double sin = Math.sin(angleOfMovement);
		double newAngle = Math.atan2(-sin, cos);
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(), this.getHeight(), this.speed, newAngle, this.getId());
	}
	public static double randomAngle(double rangeInDegrees){
		double rangeInRadians = Math.toRadians(rangeInDegrees);
		return (Math.random()*rangeInRadians)-(rangeInRadians/2);
	}
	public Ball changeAngle(double range){
		return new Ball(this.getTopLeftCoordinate(), this.getWidth(),this.getHeight(),this.speed,this.angleOfMovement+randomAngle(range), this.getId());
	}


}
