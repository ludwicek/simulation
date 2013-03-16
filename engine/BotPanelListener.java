package engine;

import graphic.Graphic;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

public class BotPanelListener extends MouseAdapter implements MouseMotionListener {
	private BotPanelDrawArea botpanel;
	public BotPanelListener (BotPanelDrawArea panel) {
		setBotpanel(panel);
	}
	public void mouseDragged(MouseEvent e) {
		//System.out.println("hú prdele");
	}
	public void mouseMoved(MouseEvent e) {
		//int i = 0;
		for (Graphic shape : botpanel.getShapes()) {
			Point2D mouse = new Point2D.Double(e.getX(),e.getY());
			if (mouse.distance(shape.getPosition()) < shape.getRadius() + 2) {
				shape.setHovered(true);
				botpanel.repaint();
			}
			else {
				shape.setHovered(false);
				botpanel.repaint();
			}
			//i++;
		}
	}
	public void mousePressed(MouseEvent e) {
		Point2D mouse = new Point2D.Double(e.getX(),e.getY());
		boolean clickedCircle = false;
		botpanel.getBotpanel().setWage(0);
		for (Graphic shape : botpanel.getShapes()) {
			if (mouse.distance(shape.getPosition()) < shape.getRadius() + 2) {
				for (Graphic shape1 : botpanel.getShapes()) {
					if (!shape1.isLast()) {
						shape1.setLineSelected(false);
						shape1.setSelected(false);
					}
					shape1.setLast(false);
				}
				
				shape.setSelected(!shape.isSelected());
				shape.setLast(true);
				shape.setLineSelected(shape.isSelected());
				botpanel.repaint();
				clickedCircle = true;
			}
		}
		if (!clickedCircle) {
			for (Graphic shape1 : botpanel.getShapes()) {
				shape1.setSelected(false);
				shape1.setLineSelected(false);
			}
		}

	}

	public void mouseClicked(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void setBotpanel(BotPanelDrawArea panel) {
		this.botpanel = panel;
	}
	public BotPanelDrawArea getBotpanel() {
		return botpanel;
	}
}
