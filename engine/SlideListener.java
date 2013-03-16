package engine;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SlideListener implements ChangeListener {
	private MainWindow window;
	public SlideListener(MainWindow window) {
		this.window = window;
	}
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		window.setSimSpeed((double)source.getValue());
	}

}
