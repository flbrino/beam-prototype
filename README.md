# Beam Prototype
[![Build Status](https://travis-ci.org/ccaspanello/beam-prototype.svg?branch=master)](https://travis-ci.org/ccaspanello/beam-prototype)
[![Coverage Status](https://coveralls.io/repos/github/ccaspanello/beam-prototype/badge.svg?branch=master)](https://coveralls.io/github/ccaspanello/beam-prototype?branch=master)

Project to learn how to effectively develop and test Beam applications.

## Building
This is a Maven based project.  Simply run `mvn clean install`


## AEL Prototype
This branch contains a prototype specifically geared on how to architect AEL for the future.  Here are some goals I am
trying to prove in this prototype:

* ALL IN Karaf - Leveraging Karaf is a necessity due to all the data sources Pentaho supports.  However, since some of
  our old code has not been migrated to Karaf it poses a problem running in distributed enviornments which automatically
  include JARs needed for that framework to run.  Karaf should help isolate is from these types of conflicts.
  
* Embedded Karaf - Another challenge is working with Karaf in distributed environments.  Most of these distributed 
  computing platforms assume a "thick JAR" which conflicts with how Karaf is distributed.  The goal here is to create a
  small Karaf container that is embedded in the JAR.  The JAR then automatically extracts that embedded Karaf instance
  if needed (taking advantage of distributed cache).
  
* Plugins On Demand (Stretch Goal) - Leveraging what we learned in a Hackathon project we can downlaod and load plugins
  on demand based on which steps are included in the KTR.  This will keep the embedded Karaf instance small and keep
  launch times to a minimum.  As long as we use the distributed cache plugins will not have to be re-downloaded every
  time.  This also solves the problem of making sure the server has all of the desired plugins based on what plugins ETL
  engineers use to develop with on their client machine.
  
* Beam Integration - From a customer perspective AEL advertises *write once run everywhere*.  I'd love to bring this 
  concept into AEL so we don't have to rewrite *native* steps for each engine we want to support.  By leveraging Beam we
  can write a native step to the Beam API and run it everywhere (Spark/Flink/Apex/Dataflow).  This also helps our 
  community.  By telling our community to write to Beam APIs a plugin *Bob* writes targeted for Spark can be leveraged
  by *Suzy* running on Dataflow.
  
### Project Structure for `ael-application`

* `ael-assembly` - Creates the Karaf assembly zip file using configuration & feature files.


* `ael-features` - Feature files created from bundles.  Each is constructed with a bundle start level to control order of
  loading.  By leveraging bundle start levels we can avoid a bunch of error prone *wait* logic we have seen in the past.
 
* `ael-bundles` - Developed OSGi bundles; this includes the API, Engine Runners, and Core application

* `ael-launcher` - Simple JAR responsible for extracting the embedded Karaf container and starting it passing all
  arguments to Karaf.
  
### Running
You can execute this JAR in multiple environments.  See below for details on each one.

#### Spark
1. Download a 2.1.1 version of Apache Spark
2. Look at ./test-spark.sh for an example of how we run it with Apache Spark

#### Flink
1. Download a 1.4.1 version of Apache Flink
2. Look at ./test-flink.sh for an example of how we run it with Apache Flink

#### Dataflow

### Work In Progress
* Finish working through Flink classloading issues
* Integrate Beam and make sure we can run on Spark & Flink followed by Dataflow
* Write Beam transformations that leverge an actual KTR using pentaho-kettle TransMeta
* Map StepMeta data to basic POJOs using a data mapper like Orka
* Break up steps into separate plugin bundles
* Stretch Goal - Bring in Hackathon concept and load plugins on demand.
 