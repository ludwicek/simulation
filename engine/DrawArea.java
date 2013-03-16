package engine;

import javax.swing.*;
import java.awt.*;
//import java.awt.geom.Point2D;
import java.util.ArrayList;
public class DrawArea extends JPanel {
	private static final long serialVersionUID = 1L;
	private Point mousePosition = new Point(0,0);
	private MainWindow frame;
	private DrawAreaListener listener = new DrawAreaListener(this);
	private ArrayList<Drawable> shapes = new ArrayList<Drawable>();
    public DrawArea(MainWindow frame) {
		setFrame(frame);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
	}
	public void paintComponent(Graphics g) {
		System.out.println(shapes.size());
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D)g;
		//graphics.drawRect(1.7, 5.8, 54.7, 90.1);
		if (shapes.isEmpty()) return;
        for (Drawable shape : shapes) {
        	if (shape.show())
        		shape.drawShape(graphics);
        }
       // this.update(graphics);
	}
	public void drawCircle(Graphics g, int xCenter, int yCenter, int r) {
		g.drawOval(xCenter-r, yCenter-r, 2*r, 2*r);
	}
	
	public Point getMousePosition() {
		return mousePosition;
	}
	public void setMousePosition(Point mousePosition) {
		this.mousePosition = mousePosition;
	}
	public MainWindow getFrame() {
		return frame;
	}
	public void setFrame(MainWindow frame) {
		this.frame = frame;
	}
	public ArrayList<Drawable> getShapes() {
		return shapes;
	}
	public void setShapes(ArrayList<Drawable> shapes) {
		this.shapes = shapes;
	}
	public void setListener(DrawAreaListener listener) {
		this.listener = listener;
	}
	public DrawAreaListener getListener() {
		return listener;
	}

}
