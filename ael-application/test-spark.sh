#!/usr/bin/env bash
SECONDS=0 ;

export YARN_CONF_DIR=/Users/ccaspanello/Desktop/hdp_config
export SPARK_LOCAL_IP=localhost

PROJECT_DIR=$(pwd)

cd ~/spark

./bin/spark-submit \
  --class com.github.ccaspanello.ael.launcher.LauncherMain \
  --master local[*] \
  $PROJECT_DIR/ael-launcher/target/ael-launcher-1.0-SNAPSHOT-jar-with-dependencies.jar \
  --ktr=MyKtr -r=spark

echo $SECONDS