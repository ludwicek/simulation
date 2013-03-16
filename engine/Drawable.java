package engine;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public interface Drawable {
	public void drawShape(Graphics2D g);
	public ArrayList<Double> getSensorInfo();
	public Point2D getPosition();
	public boolean show();
}
