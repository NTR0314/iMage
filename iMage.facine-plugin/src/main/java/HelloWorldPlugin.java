import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.iMage.plugins.*;
import org.jis.Main;

public class HelloWorldPlugin extends JmjrstPlugin {
	private final String HOME = System.getProperty("user.home");
	private final String BILDER = HOME + "/Bilder";
	private final String PICTURES = HOME + "/Pictures";
	private final String DESKTOP = HOME + "/Desktop";
	private final String PICS = HOME + "/pics";
	
	@Override
	public void configure() {
		JFrame frame = this.setupJFrame();
		
		if (Files.exists(Paths.get(BILDER))) {
			frame.add(new JLabel(BILDER));
		}
		
		if (Files.exists(Paths.get(PICTURES))) {
			frame.add(new JLabel(PICTURES));
		}
		
		if (Files.exists(Paths.get(DESKTOP))) {
			frame.add(new JLabel(DESKTOP));
		}
		if (Files.exists(Paths.get(PICS))) {
			frame.add(new JLabel(PICS));
		}		
		
	}

	@Override
	public String getName() {
		return "Hello-World-Plugin";
	}

	@Override
	public void init(Main arg0) {		
		System.err.println("iMage: Sammelt Ihre Daten seit 2016! Folgende Ordner werden (meist) durchsucht:\n");
		System.err.println(BILDER);
		System.err.println(PICTURES);
		System.err.println(DESKTOP);
		System.err.println(PICS);
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
