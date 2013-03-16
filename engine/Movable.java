package engine;

import java.awt.geom.Point2D;

public interface Movable extends Livable {
	public void move();
	public Point2D getPosition();

}
