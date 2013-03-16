package graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import engine.BotPanel;

public class Circle extends Graphic  {
	private static final Color SELECTED_COLOR = new Color(201,152,4);
	private ArrayList<ConnectionLine> lines = new ArrayList<ConnectionLine>();
	private ArrayList<ConnectionLine> linesBefore = new ArrayList<ConnectionLine>();
	private BotPanel botpanel;
	private double wageShown;
	private boolean last;
	public Circle(Point2D position, int radius, Color color, BotPanel panel) {
		this.position = position;
		this.radius = radius;
		this.setColor(color);
		this.setBotpanel(panel);
	}
	public void moveShape(Point2D position) {
		this.position = position;
	}

	public void drawShape(Graphics2D g) {
		g.setColor(getColor());
		if (hovered || selected) {
			if (hovered) {
				g.drawOval((int)position.getX() - radius - 2, (int)position.getY() - radius - 2, radius*2 + 4, radius*2 + 4);				
			}
			if (selected) { 
				g.setColor(SELECTED_COLOR);
				g.fillOval((int)position.getX() - radius, (int)position.getY() - radius, radius*2, radius*2);
			}
		}
		g.drawOval((int)position.getX() - radius, (int)position.getY() - radius, radius*2, radius*2);
		for (ConnectionLine line : lines) {
			g.setColor(Color.BLACK);
			if (line.isOkLeft() && line.isOkRight()) {
				//setWageShown(line.getWage());
				getBotpanel().setWage(line.getWage());
				line.paint(g, new Point2D.Double(position.getX() + radius, position.getY()));
			}
			//line.paint(g, position);
		}
	}
	public void setLines(ArrayList<ConnectionLine> lines) {
		this.lines = lines;
	}
	public ArrayList<ConnectionLine> getLines() {
		return lines;
	}
	public void setLineSelected(boolean selected) {
		for(ConnectionLine line : lines) {
			line.setOkLeft(selected);
		}
		for(ConnectionLine line : linesBefore) {
			line.setOkRight(selected);
		}
	}
	public void setLinesBefore(ArrayList<ConnectionLine> linesBefore) {
		this.linesBefore = linesBefore;
	}
	public ArrayList<ConnectionLine> getLinesBefore() {
		return linesBefore;
	}
	public void setWageShown(double wageShown) {
		this.wageShown = wageShown;
	}
	public double getWageShown() {
		return wageShown;
	}
	public void setBotpanel(BotPanel botpanel) {
		this.botpanel = botpanel;
	}
	public BotPanel getBotpanel() {
		return botpanel;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	public boolean isLast() {
		return last;
	}


}
