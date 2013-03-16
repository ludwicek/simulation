package engine;

import java.awt.Cursor;
import java.awt.Dimension;
//import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.Random;


public class DrawAreaListener extends MouseAdapter implements MouseMotionListener, MouseWheelListener, KeyListener {
		private DrawArea drawArea;
		private int draggedID = -1;
		private Dragable dragged;
		private double xDist;
		private double yDist;
		private int dX;
		private int dY;
		private boolean panelDraggable = false;
		public DrawAreaListener(DrawArea drawArea) {
			setDrawArea(drawArea);
		}
		public void mouseWheelMoved(MouseWheelEvent e) {
			int notches = e.getWheelRotation();
			//Point p = drawArea.getLocation();
			if (notches < 0) {
				drawArea.zoomIn();
			} 
			else {
				drawArea.zoomOut();
		    }
			if ( drawArea.getScale() > 0.40 && -(drawArea.getLocation().getX()) > (drawArea.getWidth() ) * drawArea.getScale() - drawArea.getInit().appWidth + drawArea.getInit().rightPanelWidth + 15)  {
				drawArea.setLocation((int)-((drawArea.getWidth() ) * drawArea.getScale() - drawArea.getInit().appWidth + drawArea.getInit().rightPanelWidth + 15), (int)drawArea.getLocation().getY());
			}
			if ( drawArea.getScale() > 0.40 && -(drawArea.getLocation().getY()) > (drawArea.getHeight() ) * drawArea.getScale() - drawArea.getInit().appHeight + 60)  {
				drawArea.setLocation((int)drawArea.getLocation().getX(),(int)-((drawArea.getHeight() ) * drawArea.getScale() - drawArea.getInit().appHeight + 60));
			}
			if (drawArea.getScale() <= 0.40) {
				drawArea.setLocation(0,0);
			}
			
		}
		public void mouseDragged(MouseEvent e) {
			if (drawArea.isAdding()) return;
			switch(e.getModifiers()) {
				case InputEvent.BUTTON1_MASK: {
					if (draggedID == -1) {
						for (Dragable drag : drawArea.getInit().getDragables()) {
							if (draggedID != -1) break;
							double dragX = drag.getPosition().getX();
							double dragY = drag.getPosition().getY();
							double dragHeight = drag.getHeight(); 
							double dragWidth = drag.getWidth();
							double mouseX = e.getX()/drawArea.getScale();
							double mouseY = e.getY()/drawArea.getScale();
							
							if (mouseX >= dragX && mouseX <= dragX + dragWidth && mouseY >= dragY && mouseY <= dragY + dragHeight ) {
								dragged = drag;
								dragged.setDragged(true);
								draggedID = dragged.getId();
								xDist = mouseX - dragX;
								yDist = mouseY - dragY;
							}
						}				
					}
					else {
						dragged.setPosition(new Point2D.Double(e.getX()/drawArea.getScale() - xDist,e.getY()/drawArea.getScale() - yDist));
					}
					break;
				}
				case InputEvent.BUTTON3_MASK: {
					if (drawArea.getScale() < 0.10) {
						drawArea.setLocation(0,0);
						panelDraggable = false;
					}
			        if (panelDraggable) {
			        	
			        	System.out.println( ); 
			        	if (e.getLocationOnScreen().x - dX <= drawArea.getInit().boundaries && - (e.getLocationOnScreen().x - dX) < (drawArea.getWidth() ) * drawArea.getScale() - drawArea.getInit().appWidth + drawArea.getInit().rightPanelWidth + 15) {
			        		drawArea.setLocation(e.getLocationOnScreen().x - dX, drawArea.getLocation().y);
			        	}
			        	if (e.getLocationOnScreen().y - dY <= drawArea.getInit().boundaries && - (e.getLocationOnScreen().y - dY) < (drawArea.getHeight() ) * drawArea.getScale() - drawArea.getInit().appHeight + 60) {
			        		drawArea.setLocation(drawArea.getLocation().x, e.getLocationOnScreen().y - dY );
			        	}
			        	/*
			        	if ( e.getLocationOnScreen().x - dX <= drawArea.getInit().boundaries && e.getLocationOnScreen().x - dX > - (drawArea.getInit().boundaries + drawArea.getFrame().getEditorScroll().getWidth() - drawArea.getInit().appWidth + drawArea.getInit().rightPanelWidth) ) {
			        		drawArea.setLocation(e.getLocationOnScreen().x - dX, drawArea.getLocation().y);
			        		
			        	}
			        	if ( e.getLocationOnScreen().y - dY <= drawArea.getInit().boundaries && e.getLocationOnScreen().y - dY > - (drawArea.getInit().boundaries + 50 + drawArea.getHeight() - drawArea.getInit().appHeight) ){
			        		drawArea.setLocation(drawArea.getLocation().x, e.getLocationOnScreen().y - dY );
				            
			        	}
			        	*/
			        	dX = e.getLocationOnScreen().x - drawArea.getX();
			        	dY = e.getLocationOnScreen().y - drawArea.getY();
			        }

			        break;
				}
			}
			
			
		}
		public void mouseMoved(MouseEvent e) {
			
		}
		public void mousePressed(MouseEvent e) {
			 switch(e.getModifiers()) {
		      case InputEvent.BUTTON1_MASK: {
		    	    if (drawArea.isAdding()) {
		    	    	Random generator = new Random();
		    	    	switch(drawArea.getAddType()) { 
			    	    	case 1: {
			    	    		drawArea.getFrame().getInit().createBot(generator.nextInt(drawArea.getInit().getRaceCount()) + 1, new Point2D.Double(e.getX()/drawArea.getScale(),e.getY()/drawArea.getScale()), true);
			    	    		break;
			    	    	}
			    	    	case 2: {
			    	    		drawArea.getFrame().getInit().createPlant(new Point2D.Double(e.getX()/drawArea.getScale(),e.getY()/drawArea.getScale()), true);
			    	    		break;
			    	    	}
		    	    	}
		    	    	return;
		    	    }
					int x,y,bot_x,bot_y;
					boolean selected = false;
				
					x = (int) (e.getX()/drawArea.getScale());
					y = (int) (e.getY()/drawArea.getScale());
					for (Infoable infoable : drawArea.getInit().getInfoables()) {
						bot_x = (int) infoable.getPosition().getX();
						bot_y = (int) infoable.getPosition().getY();
						infoable.setSelected(false);
						if (x >= bot_x - 10 && x <= bot_x + 30 && y >= bot_y -10 && y <= bot_y + 30 && !selected) {
							try {
								if (infoable.showInfo()) {

									infoable.setSelected(true);
									selected = true;
									drawArea.getFrame().getInfoPanel().setVisible(true);
									drawArea.getFrame().getInfoPanel().drawInfo(infoable);
								}
							} catch (CloneNotSupportedException e1) {
								e1.printStackTrace();
							}
						}
					} 
					if (selected == false) {
						drawArea.getFrame().getInfoPanel().setVisible(false);
					}
		        break;
		        }
		      case InputEvent.BUTTON3_MASK: {
		    	
	            dX = e.getLocationOnScreen().x - drawArea.getX();
	            dY = e.getLocationOnScreen().y - drawArea.getY();
	            if ((e.getX() / drawArea.getScale()) < drawArea.getWidth() && (e.getY() / drawArea.getScale()) < drawArea.getHeight()) {
		            panelDraggable = true;	            	
	            }
	            else {
	            	panelDraggable = false;
	            }
		    	if (drawArea.isAdding()) {
		    		drawArea.setAdding(false);
		    		drawArea.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		    		return;
		    	}

		        break;
		        
		        
		        }

		     }
		    

			
		}
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2 && !e.isConsumed()) {
		        int bot_x;
		        int bot_y;
		        int x,y;
		        
		        for (Infoable infoable : drawArea.getInit().getInfoables()) {
		        	if (drawArea.isShowingBotPanel()) break;
					bot_x = (int) infoable.getPosition().getX();
					bot_y = (int) infoable.getPosition().getY();
					x = (int) (e.getX()/drawArea.getScale());
					y = (int) (e.getY()/drawArea.getScale());					
					if (x >= bot_x - 10 && x <= bot_x + 30 && y >= bot_y -10 && y <= bot_y + 30 && infoable.getInfo().size() > 2)    {
						drawArea.showBotInfo(infoable.getID());
				        drawArea.setShowingBotPanel(true);
					}
		        }
			    e.consume();

			}
		}
		public void mouseReleased(MouseEvent e) {
			if (dragged != null) {
				dragged.setDragged(false);
				draggedID = -1;
			}
			panelDraggable = false;
		}
		public void keyTyped(KeyEvent k) {
			System.out.println(k.getKeyChar());
			//System.out.println("aaaasdasdasd");
			switch(k.getKeyChar()) {
				case '+': {
					
					drawArea.zoomIn();
					break;
				}
				case '-': {
					drawArea.zoomOut();
					break;
				}
			}
			drawArea.setPreferredSize(new Dimension((int)(drawArea.getInit().drawareaWidth*drawArea.getScale()), (int)(drawArea.getInit().drawareaHeight*drawArea.getScale())));
			drawArea.revalidate();
			drawArea.repaint();

		}
		public void keyPressed(KeyEvent k) {
			//System.out.println(k.getKeyChar());
			//System.out.println("aaaasdasdasd");
		}		
		public void keyReleased(KeyEvent k) {
			
		}		
		
		public DrawArea getDrawArea() {
			return drawArea;
		}
		public void setDrawArea(DrawArea drawArea) {
			this.drawArea = drawArea;
		}

}
