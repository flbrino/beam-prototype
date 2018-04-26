package com.github.ccaspanello.spark;

import com.github.ccaspanello.ael.api.Result;
import com.github.ccaspanello.ael.api.RunnerService;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ccaspanello on 4/23/18.
 */
public class VanillaSparkRunner implements RunnerService {

  private static final Logger LOG = LoggerFactory.getLogger( VanillaSparkRunner.class );

  private final BundleContext bundleContext;

  public VanillaSparkRunner( BundleContext bundleContext ) {
    this.bundleContext = bundleContext;
  }

  @PostConstruct
  public void init() {
    LOG.info( "VanillaSparkRunner.init()" );
  }

  @PreDestroy
  public void destroy() {
    LOG.info( "VanillaSparkRunner.destroy()" );
  }

  @Override public String getName() {
    return "spark";
  }

  @Override public Result run( String ktr, Map<String, String> parameters ) {

    SparkContext sc = SparkContext.getOrCreate();
    SparkSession ss = new SparkSession( sc );

    // Schema
    StructType schema = new StructType( new StructField[] {
      new StructField( "key", DataTypes.StringType, false, Metadata.empty() ),
      new StructField( "value", DataTypes.IntegerType, false, Metadata.empty() )
    } );

    // Data
    List<Row> myList = new ArrayList<>();
    for ( int i = 0; i < 25; i++ ) {
      String key = "key" + i;
      int value = i;
      myList.add( RowFactory.create( key, value ) );
    }

    Dataset<Row> result = ss.createDataFrame( myList, schema );
    result.show();
    return null;
  }
}