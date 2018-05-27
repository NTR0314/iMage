import javax.swing.JFrame;

import org.iMage.plugins.*;
import org.jis.Main;

public class HelloWorldPlugin extends JmjrstPlugin {

	@Override
	public void configure() {
		JFrame frame = this.setupJFrame();
		
		if (condition) {
			
		}
	}

	@Override
	public String getName() {
		return "Hello-World-Plugin";
	}

	@Override
	public void init(Main arg0) {
		String home = System.getProperty("user.home");
		
		System.err.println("iMage: Sammelt Ihre Daten seit 2016! Folgende Ordner werden (meist) durchsucht:\n");
		System.err.println(home + "/Bilder");
		System.err.println(home + "/Pictures");
		System.err.println(home + "/Desktop");
		System.err.println(home + "/pics");
	}

	@Override
	public boolean isConfigurable() {
		return true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	private JFrame setupJFrame() {
		JFrame frame = new JFrame("Folders");
		frame.setVisible(true);
		
		return frame;
	}

}
