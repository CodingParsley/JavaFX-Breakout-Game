package application;

import java.awt.geom.Line2D;

public class JavaCollisionDetector implements ICollisionDetector{
	@Override
	public boolean intersects(LineSegment one,LineSegment two){
		Line2D line1 = new Line2D.Double(one.getA().getX(), one.getA().getY(), one.getB().getX(), one.getB().getY());
		Line2D line2 = new Line2D.Double(two.getA().getX(), two.getA().getY(), two.getB().getX(),
		two.getB().getY());
		boolean result = line2.intersectsLine(line1);
		return result;
	}
}
