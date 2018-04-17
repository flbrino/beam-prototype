package com.github.beam.etl;

import com.github.beam.etl.wordcount.CountWords;
import com.github.beam.etl.wordcount.FormatAsTextFn;
import com.github.beam.etl.wordcount.WordCountOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * TODO Replace tutorial code with ETL program
 */
public class BeamEtlMain {

  private static final Logger LOG = LoggerFactory.getLogger( BeamEtlMain.class );

  static final List<String> LINES = Arrays.asList(
    "To be, or not to be: that is the question: ",
    "Whether 'tis nobler in the mind to suffer ",
    "The slings and arrows of outrageous fortune, ",
    "Or to take arms against a sea of troubles, " );

  public static void main( String[] args ) {
    WordCountOptions options = PipelineOptionsFactory.fromArgs( args ).withValidation().as( WordCountOptions.class );
    Pipeline p = Pipeline.create( options );

    // DataGrid 1
    PCollection<String> dataGrid = p.apply( Create.of( LINES ) ).setCoder( StringUtf8Coder.of() );

    // DataGrid 2

    // Concepts #2 and #3: Our pipeline applies the composite CountWords transform, and passes the
    // static FormatAsTextFn() to the ParDo transform.
    PCollection<String> data = p.apply( "ReadLines", TextIO.read().from( options.getInputFile() ) );
    PCollection<KV<String, Long>> data2 = data.apply( new CountWords() );
    PCollection<String> data3 = data2.apply( MapElements.via( new FormatAsTextFn() ) );
    data3.apply( "WriteCounts", TextIO.write().to( options.getOutput() ) );

    LOG.error( "-------------------------------------" );
    LOG.info( "debug: {}", data3.toString() );
    LOG.error( "-------------------------------------" );

    p.run().waitUntilFinish();
  }
}
