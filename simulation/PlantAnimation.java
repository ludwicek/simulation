package simulation;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlantAnimation extends Animation {
	private final int ANIMATIONS = 1;
	public PlantAnimation() {
		initAnimations(ANIMATIONS);
		try {
			for (int i = 0; i < ANIMATIONS; i++) {
				addImage(ImageIO.read(new File("images/plant.gif")),i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}