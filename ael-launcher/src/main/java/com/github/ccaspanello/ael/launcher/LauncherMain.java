package com.github.ccaspanello.ael.launcher;

/**
 * Created by ccaspanello on 1/22/18.
 */
public class LauncherMain {

  public static void main( String[] args ) {

    LauncherProperties launcherProperties = LauncherProperties.instance();
    String karafHome = launcherProperties.karafHome();

    // TODO Embed Karaf and extract it to user directory at runtime.
    //String karafHome = "/Users/ccaspanello/git/ccaspanello/px/boot/boot-assembly/target/assembly";

    args = new String[] { "-ktr=/Users/ccaspanello/Desktop/beam.ktr", "-r=direct-runner" };
    KarafContainer karafContainer = new KarafContainer( karafHome );
    karafContainer.launch( args );
    karafContainer.awaitShutdown();
    System.exit( 0 );
  }

}
