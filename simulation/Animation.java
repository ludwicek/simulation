package simulation;

import java.awt.image.BufferedImage;

public abstract class Animation {
	private static final long serialVersionUID = 4491855269401176037L;
	private BufferedImage images[];
	public BufferedImage[] getImages() {
		return images;
	}
	protected void setImages(BufferedImage images[]) {
		this.images = images;
	}
	protected void addImage(BufferedImage image, int place) {
		this.images[place] = image;
	}
	protected void initAnimations(int size) {
		images = new BufferedImage[size];
	}
}
