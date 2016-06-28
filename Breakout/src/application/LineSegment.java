package application;

import java.awt.geom.Line2D;

public class LineSegment {

	private Coordinate a;
	private Coordinate b;


	public LineSegment(Coordinate a, Coordinate b) {
		this.a = a;
		this.b = b;
	}

	public Coordinate getA() {
		return a;
	}

	public Coordinate getB() {
		return b;
	}
	public boolean intersects(LineSegment other){

		Line2D line1 = new Line2D.Double(a.getX(), a.getY(), b.getX(), b.getY());
		Line2D line2 = new Line2D.Double(other.getA().getX(), other.getA().getY(), other.getB().getX(),
				other.getB().getY());
		boolean result = line2.intersectsLine(line1);
		return result;
	}

}
