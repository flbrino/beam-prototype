package com.github.ccaspanello.spark;

import com.github.ccaspanello.ael.api.Result;
import com.github.ccaspanello.ael.api.RunnerService;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

/**
 * Created by ccaspanello on 4/19/18.
 */
public class SparkRunner implements RunnerService {

  private static final Logger LOG = LoggerFactory.getLogger( SparkRunner.class );

  private final BundleContext bundleContext;

  public SparkRunner( BundleContext bundleContext ) {
    this.bundleContext = bundleContext;
  }

  @PostConstruct
  public void init() {
    LOG.info( "SparkRunner.init()" );

  }

  @PreDestroy
  public void destroy() {
    LOG.info( "SparkRunner.destroy()" );
  }

  @Override public String getName() {
    return "spark-runner";
  }

  @Override public Result run( String ktr, Map<String, String> parameters ) {
    return null;
  }
}