package engine;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public interface Infoable extends Selectable {
	public ArrayList<String> getInfo();
	public void drawShapeInfo(Graphics2D g, Point p);
	public boolean showInfo();
}
