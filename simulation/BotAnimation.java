package simulation;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BotAnimation extends Animation {
	private final int ANIMATIONS = 36;
	public BotAnimation() {
		initAnimations(ANIMATIONS);
		try {
			for (int i = 0; i < ANIMATIONS; i++) {
				addImage(ImageIO.read(new File("images/bot"+ i*10 +".png")),i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
