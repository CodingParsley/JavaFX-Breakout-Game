package application;

public class Rectangle {
	private static int count;
	private int id;
	private int opacity = 1;
	private boolean isAlive = true;
	private Coordinate topLeft;
	private Coordinate bottomRight;

	//Main constructor
	protected Rectangle(Coordinate topLeft, Coordinate bottomRight,int id){
		this.id=id;
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}

	//Alternative to the main constructor, preserves id
	protected Rectangle(Coordinate topLeft,double width,double height, int id){
		this(topLeft,topLeft.getMoveDelta(width, height),id);
	}

	//For creating brand new rectangles
	public Rectangle(Coordinate topLeft, Coordinate bottomRight){
		this(topLeft,bottomRight,count++);
	}

	//For creating brand new rectangles as well, calls the constructor above
	public Rectangle(Coordinate topLeft,double width,double height){
		this(topLeft,topLeft.getMoveDelta(width, height));
	}

	public Rectangle createMove(double dx,double dy){
		return new Rectangle(topLeft.getMoveDelta(dx, dy),bottomRight.getMoveDelta(dx, dy),this.id);
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public int getOpacity() {
		return opacity;
	}
	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}

	public int getId() {
		return id;
	}

	public Coordinate getTopLeftCoordinate() {
		return topLeft;
	}

	public Coordinate getBottomRightCoordinate() {
		return bottomRight;
	}

	public Coordinate getTopRightCoordinate(){
		return new Coordinate(topLeft.getX() + this.getWidth(), topLeft.getY());
	}

	public Coordinate getBottomLeftCoordinate(){
		return new Coordinate(topLeft.getX(), topLeft.getY()+this.getHeight());
	}

	public Coordinate getCenterCoordinate(){
		return new Coordinate(this.getTopLeftCoordinate().getX()+(this.getWidth()/2),
				this.getTopLeftCoordinate().getY()+(this.getHeight()/2));
	}

	public LineSegment getTopLineSegment(){
		return new LineSegment(this.getTopLeftCoordinate(), this.getTopRightCoordinate());
	}

	public LineSegment getRightLineSegment(){
		return new LineSegment(this.getTopRightCoordinate(), this.getBottomRightCoordinate());
	}

	public LineSegment getBottomLineSegment(){
		return new LineSegment(this.getBottomLeftCoordinate(), this.getBottomRightCoordinate());
	}
	public LineSegment getLeftLineSegment(){
		return new LineSegment(this.getTopLeftCoordinate(), this.getBottomLeftCoordinate());
	}

	public double getWidth(){
		return Math.abs(topLeft.getX()-bottomRight.getX());
	}
	public double getHeight(){
		return Math.abs(topLeft.getY()-bottomRight.getY());
	}


}
