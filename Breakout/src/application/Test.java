package application;
import java.awt.geom.Line2D;

public class Test {
	public static void run(){

		Line2D line1 = new Line2D.Float(100, 100, 200, 200);
		Line2D line2 = new Line2D.Float(1, 1, 0,0);
		boolean result = line2.intersectsLine(line1);
		System.out.println(result); // => true
	}
	public static void main(String[] args){
		run();
	}
}
