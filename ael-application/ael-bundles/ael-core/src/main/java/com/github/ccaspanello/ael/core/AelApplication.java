package com.github.ccaspanello.ael.core;

import com.github.ccaspanello.ael.api.Result;
import com.github.ccaspanello.ael.api.RunnerService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.karaf.info.ServerInfo;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by ccaspanello on 4/19/18.
 */
public class AelApplication {

  private static final Logger LOG = LoggerFactory.getLogger( AelApplication.class );

  private final BundleContext bundleContext;
  private final List<RunnerService> runnerServices;

  public AelApplication( BundleContext bundleContext, List<RunnerService> runnerServices ) {
    this.bundleContext = bundleContext;
    this.runnerServices = runnerServices;
  }

  @PostConstruct
  public void init() {
    LOG.info( "************************" );
    LOG.info( "Starting AEL Application" );
    LOG.info( "************************" );

    ServerInfo serverInfo = bundleContext.getService( bundleContext.getServiceReference( ServerInfo.class ) );
    String[] args = serverInfo.getArgs();

    boolean debug= false;

    Options options = createOptions();
    try {
      CommandLineParser parser = new DefaultParser();
      CommandLine cmd = parser.parse( options, args );

      debug = cmd.hasOption( "d" );

      if ( cmd.hasOption( "h" ) ) {
        printHelp( options );
      } else if ( cmd.hasOption( "k" ) ) {
        String ktr = cmd.getOptionValue( "k" );
        String runner = cmd.hasOption( "r" ) ? cmd.getOptionValue( "r" ) : "direct-runner";
        runOnRunner( runner, ktr, new HashMap<>() );
      }

    } catch ( ParseException e ) {
      LOG.error( "{}.  Please refer to the help documentation.", e.getLocalizedMessage() );
      printHelp( options );
    } catch ( AelApplicationException e ) {
      LOG.error( "Error running application: {}", e.getLocalizedMessage() );
    } finally {
      if(!debug){
        kill();
      }
    }

  }

  private void runOnRunner( String runner, String ktr, Map<String, String> parameters ) {
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

  @PreDestroy
  public void destroy() {
    LOG.info( "************************" );
    LOG.info( "Stopping AEL Application" );
    LOG.info( "************************" );
  }


  private void kill() {
    try {
      bundleContext.getBundle( 0 ).stop();
    } catch ( BundleException e ) {
      e.printStackTrace();
    }
  }

  private Options createOptions() {
    Options options = new Options();

    Option help = Option.builder( "h" )
      .longOpt( "help" )
      .desc( "Dislpays application help." )
      .build();

    Option runner = Option.builder( "r" )
      .longOpt( "runner" )
      .desc( "Beam Runner - Valid Values: direct, spark, flink, apex" )
      .hasArg()
      .build();

    Option ktr = Option.builder( "k" )
      .longOpt( "ktr" )
      .desc( "KTR File - Location where transformation file resides." )
      .hasArg()
      .build();

    Option debug = Option.builder( "d" )
      .longOpt( "debug" )
      .desc( "Debug Flag - Keeps OSGi container alive for debugging purposes only." )
      .build();

    options.addOption( help );
    options.addOption( runner );
    options.addOption( ktr );
    options.addOption( debug );
    return options;
  }

  private void printHelp( Options options ) {
    HelpFormatter helpFormatter = new HelpFormatter();
    helpFormatter.printHelp( "launcher", options );
  }

}
