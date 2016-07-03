package application;

public interface ICollisionDetector {
	boolean intersects(LineSegment one, LineSegment two);
}