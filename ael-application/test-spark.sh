#!/usr/bin/env bash
SECONDS=0 ;

export YARN_CONF_DIR=~/yarn_conf/cdh513
export SPARK_LOCAL_IP=localhost

PROJECT_DIR=$(pwd)

cd ~/Spark/spark-2.2.0-bin-hadoop2.7

./bin/spark-submit \
  --conf spark.driver.extraJavaOptions=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 \
  --class com.github.ccaspanello.ael.launcher.LauncherMain \
  --master local[*] \
  $PROJECT_DIR/ael-launcher/target/ael-launcher-1.0-SNAPSHOT-jar-with-dependencies.jar \
  --ktr=MyKtr -r=spark -d

echo $SECONDS
