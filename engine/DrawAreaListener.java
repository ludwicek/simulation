package engine;

import java.awt.event.*;
import java.util.ArrayList;


public class DrawAreaListener extends MouseAdapter implements MouseMotionListener {
		private DrawArea drawArea;
		private ArrayList<Infoable> infoables = new ArrayList<Infoable>();
		public DrawAreaListener(DrawArea drawArea) {
			setDrawArea(drawArea);
		}
		public void mouseDragged(MouseEvent e) {
		}
		public void mouseMoved(MouseEvent e) {
		}
		public void mousePressed(MouseEvent e) {
			int x,y,bot_x,bot_y;
			boolean selected = false;
			drawArea.getFrame().hideInfo();
			x = e.getX();
			y = e.getY();
			for (Infoable infoable : infoables) {
				bot_x = (int) infoable.getPosition().getX();
				bot_y = (int) infoable.getPosition().getY();
				infoable.setSelected(false);
				if (x >= bot_x - 10 && x <= bot_x + 30 && y >= bot_y -10 && y <= bot_y + 30 && !selected) {
					try {
						if (infoable.showInfo()) {
							infoable.setSelected(true);
							selected = true;
							drawArea.getFrame().showInfo(infoable);
						}
					} catch (CloneNotSupportedException e1) {
						e1.printStackTrace();
					}
				}
			}
			
		}
		public void mouseClicked(MouseEvent e) {
		}
		public void mouseReleased(MouseEvent e) {
		}
		public DrawArea getDrawArea() {
			return drawArea;
		}
		public void setDrawArea(DrawArea drawArea) {
			this.drawArea = drawArea;
		}
		public ArrayList<Infoable> getInfoables() {
			return infoables;
		}
		public void setBots(ArrayList<Infoable> infoables) {
			this.infoables = infoables;
		}

}
