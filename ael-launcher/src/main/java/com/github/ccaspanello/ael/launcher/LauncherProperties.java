package com.github.ccaspanello.ael.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ccaspanello on 4/19/18.
 */
public class LauncherProperties {

  private static LauncherProperties instance;

  public static LauncherProperties instance() {
    if ( instance == null ) {
      instance = new LauncherProperties();
    }
    return instance;
  }

  private Properties properties;

  private LauncherProperties() {
    try {
      InputStream is = LauncherProperties.class.getClassLoader().getResourceAsStream( "launcher.properties" );
      properties = new Properties();
      properties.load( is );
    } catch ( IOException e ) {
      e.printStackTrace();
    }
  }

  public String karafHome() {
    return properties.getProperty( "karafHome" );
  }

}
