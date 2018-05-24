package org.iMage.plugins;

/**
 * Knows all available plug-ins and is responsible for using the service loader API to detect them.
 *
 * @author Dominic Wolff
 *
 */
public final class PluginManager {

  /**
   * No constructor for utility class.
   */
  private PluginManager() {
    throw new IllegalAccessError();
  }

  /**
   * Return an {@link Iterable} Object with all available {@link JmjrstPlugin}s alphabetically sorted 
   * according to their class names.
   *
   * @return an {@link Iterable} Object containing all available plug-ins alphabetically sorted 
   *          according to their class names.
   */
  public static Iterable<JmjrstPlugin> getPlugins() {
    //TODO: implement me!
  }

}
