package engine;

import java.awt.geom.Point2D;

public abstract class Shape implements Drawable {
	// v akej maximalnej pozicii sa moze nachadzat (obmedzenie dane rozmermi kresliacej plochy)
	private Point2D max;
	private Point2D position;
	public void setPosition(Point2D position) {
		this.position = position;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setMax(Point2D max) {
		this.max = max;
	}

	public Point2D getMax() {
		return max;
	}
}
