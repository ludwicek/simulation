package engine;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

import simulation.Animation;

public interface Drawable extends Serializable {
	public void drawShape(Graphics2D g);
	public ArrayList<Double> getSensorInfo();
	public Point2D getPosition();
	public boolean show();
	public void setAnim(Animation anim);
}
