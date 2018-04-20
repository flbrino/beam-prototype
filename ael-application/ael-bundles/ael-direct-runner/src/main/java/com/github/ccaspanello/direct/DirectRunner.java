package com.github.ccaspanello.direct;

import com.github.ccaspanello.ael.api.Result;
import com.github.ccaspanello.ael.api.RunnerService;
import com.github.ccaspanello.ael.api.WordCountService;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

/**
 * Created by ccaspanello on 4/19/18.
 */
public class DirectRunner implements RunnerService {

  private static final Logger LOG = LoggerFactory.getLogger( DirectRunner.class );

  private final BundleContext bundleContext;

  public DirectRunner( BundleContext bundleContext ) {
    this.bundleContext = bundleContext;
  }

  @PostConstruct
  public void init() {
    LOG.info( "DirectRunner.init()" );
  }

  @PreDestroy
  public void destroy() {
    LOG.info( "DirectRunner.destroy()" );
  }

  @Override public String getName() {
    return "direct-runner";
  }

  @Override public Result run( String ktr, Map<String, String> parameters ) {
    return null;
  }
}
