package engine;

import init.Init;

public class BotSensorSystem {
	private Bot bot;
	public BotSensorSystem(Bot bot) {
		setBot(bot);
	}
	public boolean isActive(Drawable draw, int type) {
		switch(type) {
			case Init.EQUAL_BOT: {
				if ( draw.getSensorInfo().get(2) == bot.getRace() && draw.getSensorInfo().get(3) != -2 ) {
					//System.out.println("Vidim rovnakeho (" + draw.getSensorInfo().get(3) + ")");
					return true;
				}
				break;
			} 
			case Init.FOREIGN_BOT:
				if ( draw.getSensorInfo().get(2) != 0 && draw.getSensorInfo().get(2) != bot.getRace() && draw.getSensorInfo().get(3) != -2 ) {
					//System.out.println("Vidim cudzieho");
					return true;
				}
				break;
			case Init.PLANT:
				if ( draw.getSensorInfo().get(2) == 0 && draw.getSensorInfo().get(3) != -2 ) {
					//System.out.println("Vidim rastlinu");
					return true;
				}
				break;
			case Init.EQUAL_HIVE:
				if ( draw.getSensorInfo().get(2) == bot.getRace() && draw.getSensorInfo().get(3) == -2 ) {
					//System.out.println("Vidim rovnaky hive");
					return true;
				}
				break;
			case Init.FOREIGN_HIVE:
				if ( draw.getSensorInfo().get(2) != bot.getRace() && draw.getSensorInfo().get(2) != 0 && draw.getSensorInfo().get(3) == -2 ) {
					//System.out.println("Vidim cudzi hive");
					return true;
				}
				break;
			default :break;
		
		}
	return false;
	}
	public Bot getBot() {
		return bot;
	}
	public void setBot(Bot bot) {
		this.bot = bot;
	}
}
