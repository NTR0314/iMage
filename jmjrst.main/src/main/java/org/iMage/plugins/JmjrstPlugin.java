package org.iMage.plugins;

/**
 * Abstract parent class for plug-ins for JMJRST
 *
 * @author Dominic Wolff
 *
 */
public abstract class JmjrstPlugin implements Comparable<JmjrstPlugin> {

  /**
   * Returns the name of this plug-in
   *
   * @return the name of the plug-in
   */
  public abstract String getName();

  /**
   * JMJRST pushes the main application to every subclass - so plug-ins are allowed to look at Main
   * as well.
   *
   * @param main
   *          JMJRST main application
   */
  public abstract void init(org.jis.Main main);

  /**
   * Runs plug-in
   */
  public abstract void run();

  /**
   * Returns whether the plug-in can be configured or not
   *
   * @return true if the plug-in can be configured.
   */
  public abstract boolean isConfigurable();

  /**
   * Open a configuration dialogue.
   */
  public abstract void configure();
  
  /**
   * Comparing the names of Plugins using the compare() method provided by String class
   * @param plugin the plugin to compare names with
   * @return 	0,	if same name
   * 			-1, if pluginname is lexicographicly less than argument pluginname
   * 			1, if pluginname is lexicographicly greater than argument pluginname
   */
  public int compareTo(JmjrstPlugin plugin) {
	  String name1 = this.getName();
	  String name2 = plugin.getName();
	  
	   return name1.compareTo(name2);
  }


}
