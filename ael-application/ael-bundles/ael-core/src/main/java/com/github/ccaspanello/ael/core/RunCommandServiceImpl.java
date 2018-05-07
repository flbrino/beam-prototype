package com.github.ccaspanello.ael.core;

import com.github.ccaspanello.ael.api.Result;
import com.github.ccaspanello.ael.api.RunCommandService;
import com.github.ccaspanello.ael.api.RunnerService;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by ccaspanello on 4/30/18.
 */
public class RunCommandServiceImpl implements RunCommandService {

  private static final Logger LOG = LoggerFactory.getLogger(RunCommandServiceImpl.class);


  private final BundleContext bundleContext;
  private final List<RunnerService> runnerServices;

  public RunCommandServiceImpl( BundleContext bundleContext, List<RunnerService> runnerServices ) {
    this.bundleContext = bundleContext;
    this.runnerServices = runnerServices;
  }

  @PostConstruct
  public void init() {
    LOG.info( "init()" );
  }

  @PreDestroy
  public void destroy() {
    LOG.info( "destroy()" );
  }

  @Override
  public void run( String runner, String ktr, Map<String, String> parameters ) {
    LOG.info("Runner: {}", runner);
    // TODO Duplicate code in AelApplication - Abstract out
    List<String> availableRunners = runnerServices.stream().map( f -> f.getName() ).collect( Collectors.toList() );
    LOG.info( "AvailableRunners: {}", availableRunners );
    LOG.info("-------------------------");
    LOG.info("");
    LOG.info( "Running Transformation:" );
    LOG.info( "  * Runner:     {}", runner );
    LOG.info( "  * KTR:        {}", ktr );
    LOG.info( "  * Parameters: {}", parameters );
    Optional<RunnerService> runnerService =
      runnerServices.stream().filter( s -> s.getName().equals( runner ) ).findFirst();
    if ( runnerService.isPresent() ) {
      Result result = runnerService.get().run( ktr, parameters );
      LOG.info( "result: {}", result );
    } else {
      throw new AelApplicationException( "Unable to find the specificed runner: " + runner );
    }
  }
}
