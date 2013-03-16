package engine;

import java.awt.geom.Point2D;

public interface Selectable {
	public void setSelected(boolean selected);
	public Point2D getPosition();
}
