package com.github.ccaspanello.ael.launcher;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * TODO Revisit test; doesn't work at all levels in the POM
 *
 * Created by ccaspanello on 4/25/18.
 */
public class ZipUtilTest {

  private String builtArtifact = "ael-assembly-1.0-SNAPSHOT.zip";

  @Test
  public void unzipTest() throws Exception {
    File jarFile = new File( LauncherMain.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() );
    File buildRoot = jarFile.getParentFile().getParentFile().getParentFile();

    File destination = new File(jarFile.getParentFile(),"test-extract");
    if(destination.exists()){
      FileUtils.deleteDirectory( destination );
    }

    destination.mkdirs();

    File zipEntry = new File( buildRoot, "ael-assembly/target/" + builtArtifact );

    assertTrue(zipEntry.exists(), "Zip File `"+zipEntry.getAbsolutePath()+"' does not exist.");
    assertTrue(destination.exists(), "Destination Folder `"+zipEntry.getAbsolutePath()+"' does not exist.");

    ZipUtil.unzip( new FileInputStream(zipEntry), destination );

    assertEquals(destination.list().length, 6);
  }
}
