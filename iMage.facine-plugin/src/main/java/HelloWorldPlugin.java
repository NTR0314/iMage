import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.iMage.plugins.*;
import org.jis.Main;

public class HelloWorldPlugin extends JmjrstPlugin {
	private JFrame frame;
	
	private final String HOME = System.getProperty("user.home");
	private final String BILDER = HOME + "/Bilder";
	private final String PICTURES = HOME + "/Pictures";
	private final String DESKTOP = HOME + "/Desktop";
	private final String PICS = HOME + "/pics";
	
	private final String REGEX = "[\\w,\\s-]+\\.((jpg)|(png))";
	
	@Override
	public void configure() {
		this.frame = this.setupJFrame();
		
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
		if (Files.exists(Paths.get(BILDER))) {
			for (File file: this.searchFiles(BILDER)) {
				this.frame.add(new JLabel(file.getAbsolutePath()));
			}
		}
		if (Files.exists(Paths.get(PICTURES))) {
			for (File file: this.searchFiles(PICTURES)) {
				this.frame.add(new JLabel(file.getAbsolutePath()));
			}
		}
		if (Files.exists(Paths.get(DESKTOP))) {
			for (File file: this.searchFiles(DESKTOP)) {
				this.frame.add(new JLabel(file.getAbsolutePath()));
			}
		}
		if (Files.exists(Paths.get(PICS))) {
			for (File file: this.searchFiles(PICS)) {
				this.frame.add(new JLabel(file.getAbsolutePath()));
			}
		}
		
	}
	
	private JFrame setupJFrame() {
		JFrame frame = new JFrame("Folders");
		frame.setVisible(true);
		
		return frame;
	}
	
	private ArrayList<File> searchFiles(String startFile) {
		ArrayList<File> files = new ArrayList<>();
		Stack<File> dirs = new Stack<File>();
	    File startdir = new File(startFile);
	    Pattern p = Pattern.compile(this.REGEX);

	    if ( startdir.isDirectory() )
	      dirs.push( startdir );

	    while ( dirs.size() > 0 )
	      for ( File file : dirs.pop().listFiles() )
	        if ( file.isDirectory() )
	          dirs.push( file );
	        else
	          if ( p.matcher(file.getName()).matches() )
	            files.add( file );
		
		
		return files;
	}

}
