package engine;

import java.awt.image.BufferedImage;

public abstract class Food extends Shape implements Consumable {
	private BufferedImage image;

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public BufferedImage getImage() {
		return image;
	}

}
