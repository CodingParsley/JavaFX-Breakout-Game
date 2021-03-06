package tests;

import static org.junit.Assert.*;
import gameComponents.*;

import org.junit.Test;

public class GameModelTests {

	@Test
	public void stringEqualsTest() {
		String s = "hi";
		assertTrue(s=="hi");
	}

	@Test
	public void equalsIsCaseSensitive(){
		String zebra = "Zebra";
		assertTrue(zebra.contains("Z"));
	}

	@Test
	public void doesBallMove(){
		Ball ball = new Ball(new Coordinate(0,0), 2, 2, 1,Math.toRadians(0),
				RectangleType.Ball);
		Ball ball2 = ball.getMove();
		assertTrue(1==ball2.getTopLeftCoordinate().getX());
	}


}
