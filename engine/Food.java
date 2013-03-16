package engine;

import java.awt.image.BufferedImage;

public abstract class Food extends Shape implements Consumable {

	private static final long serialVersionUID = 4531710585408731119L;
	private BufferedImage image;

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public BufferedImage getImage() {
		return image;
	}

}
