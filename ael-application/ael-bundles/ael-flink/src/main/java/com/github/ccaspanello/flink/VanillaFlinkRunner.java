package com.github.ccaspanello.flink;

import com.github.ccaspanello.ael.api.Result;
import com.github.ccaspanello.ael.api.RunnerService;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

/**
 * Created by ccaspanello on 4/25/18.
 */
public class VanillaFlinkRunner implements RunnerService {

  private static final Logger LOG = LoggerFactory.getLogger( VanillaFlinkRunner.class );

  private final BundleContext bundleContext;

  public VanillaFlinkRunner( BundleContext bundleContext ) {
    this.bundleContext = bundleContext;
  }

  @PostConstruct
  public void init() {
    LOG.info( "VanillaFlinkRunner.init()" );
  }

  @PreDestroy
  public void destroy() {
    LOG.info( "VanillaFlinkRunner.destroy()" );
  }

  @Override
  public String getName() {
    return "flink";
  }

  @Override
  public Result run( String ktr, Map<String, String> parameters ) {

    try{
      WordCount.main( new String[]{} );
      return null;
    }catch(Exception e){
      e.printStackTrace();
      throw new RuntimeException("Unexepcted error trying to run Flink word count example.", e);
    }
  }

}
