package com.github.ccaspanello.spark.test;

import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by ccaspanello on 4/27/18.
 */
public class SparkIT {

  private static final Logger LOG = LoggerFactory.getLogger( SparkIT.class );

  @Test
  public void test() {
    // Get Test Artifacts from Target Folder
    String root = System.getProperty( "project.build.directory" );
    File sparkHome = new File( root, "spark/spark-2.3.0-bin-hadoop2.7" );
    File aelLauncher = new File( root, "ael-launcher.jar" );

    // Assert Artifacts Exist
    assertTrue( "Spark Home does not exist, check Maven setup.", sparkHome.exists() );
    assertTrue( "AEL Launcher does not exist, check Maven setup.", aelLauncher.exists() );

    LOG.info( "Running test relative to: {}", root );

    // Configure Spark Job
    Map<String, String> env = new HashMap<>();
    env.put( "SPARK_HOME", sparkHome.getAbsolutePath() );
    env.put( "SPARK_LOCAL_IP", "localhost" );
    SparkLauncher sparkLauncher = new SparkLauncher( env );
    sparkLauncher.setMaster( "local[*]" );
    sparkLauncher.setAppResource( aelLauncher.getAbsolutePath() );
    sparkLauncher.setMainClass( "com.github.ccaspanello.ael.launcher.LauncherMain" );
    sparkLauncher.addAppArgs( "-k=transformation.ktr", "-r=spark" );

    // Configure Application Listener
    CompletableFuture<Boolean> success = new CompletableFuture<>();
    SparkAppHandle.Listener listener = new SparkAppHandle.Listener() {
      @Override
      public void stateChanged( SparkAppHandle sparkAppHandle ) {
        SparkAppHandle.State state = sparkAppHandle.getState();
        if ( state.equals( SparkAppHandle.State.FAILED ) ) {
          success.complete( false );
        } else if ( state.equals( SparkAppHandle.State.FINISHED ) ) {
          success.complete( true );
        }
      }
      @Override
      public void infoChanged( SparkAppHandle sparkAppHandle ) {
        // No-Op
      }
    };

    // Kick of Karaf Instance
    try {
      sparkLauncher.startApplication( listener );
      boolean isSuccessful = success.get( 20, TimeUnit.SECONDS );
      assertTrue( isSuccessful );
    } catch ( IOException | InterruptedException | ExecutionException | TimeoutException e ) {
      throw new RuntimeException( "Unexpected error starting Spark Application.", e );
    }

  }
}
