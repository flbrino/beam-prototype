package com.github.ccaspanello.spark.test;

import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
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
public class FlinkIT {

  private static final Logger LOG = LoggerFactory.getLogger( FlinkIT.class );

  @Ignore
  @Test
  public void test() {
    // Get Test Artifacts from Target Folder
    String root = System.getProperty( "project.build.directory" );
    File sparkHome = new File( root, "flink/flink-1.4.2" );
    File aelLauncher = new File( root, "ael-launcher.jar" );

    // Assert Artifacts Exist
    assertTrue( "Spark Home does not exist, check Maven setup.", sparkHome.exists() );
    assertTrue( "AEL Launcher does not exist, check Maven setup.", aelLauncher.exists() );

    LOG.info( "Running test relative to: {}", root );

    // TODO Try to use Flink Client to execute tests
  }
}
