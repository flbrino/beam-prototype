package com.github.ccaspanello.ael.launcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by ccaspanello on 1/22/18.
 */
public class LauncherMain {

  private static final Logger LOG = LoggerFactory.getLogger(LauncherMain.class);

  public static void main( String[] args ) throws Exception {

    String aelAssembly = System.getProperty("AEL_ASSEMBLY");

    File karafHome;
    if( aelAssembly  == null ){
      KarafDistributor distributor = new KarafDistributor();
      karafHome = distributor.extractKaraf();
    } else{
      karafHome = new File(aelAssembly);
    }
    LOG.info("Karaf Home: {}", karafHome);

    KarafContainer karafContainer = new KarafContainer( karafHome.getAbsolutePath() );
    karafContainer.launch( args );
    karafContainer.awaitShutdown();
  }
}
