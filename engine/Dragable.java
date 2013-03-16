package engine;

import java.awt.geom.Point2D;

public interface Dragable {
	//public void drag();
	public Point2D getPosition();
	public int getHeight();
	public int getWidth();
	public void setPosition(Point2D position);
	public void setDragged(boolean dragged);
	public int getId();
	public boolean canBeDragged();
}
