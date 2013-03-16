package engine;

import java.awt.geom.Point2D;

public interface Consumable {
	public void Consume();
	public Point2D getPosition();
	public boolean isConsumed();
	public int getUnits();
	public void setUnits(int units);
}
