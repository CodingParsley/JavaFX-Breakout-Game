package application;

public class Rectangle {
	private Coordinate topLeft;
	private Coordinate bottomRight;
	public Coordinate getTopLeft() {
		return topLeft;
	}
	public Coordinate getBottomRight() {
		return bottomRight;
	}
	public int getWidth(){
		return Math.abs(topLeft.getX()-bottomRight.getX());
	}
	public int getHeight(){
		return Math.abs(topLeft.getY()-bottomRight.getY());
	}
	public Rectangle(Coordinate topLeft, Coordinate bottomRight){
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}
	public Rectangle(Coordinate topLeft,int width,int height){
		this(topLeft,topLeft.CreateMove(width, height));
	}
	public Rectangle CreateMove(int dx,int dy){
		return new Rectangle(topLeft.CreateMove(dx, dy),bottomRight.CreateMove(dx, dy));
	}
}
