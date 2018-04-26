#!/usr/bin/env bash
SECONDS=0 ;

export YARN_CONF_DIR=/Users/ccaspanello/Desktop/hdp_config

PROJECT_DIR=$(pwd)

cd ~/flink-1.4.2

./bin/flink run \
  $PROJECT_DIR/ael-launcher/target/ael-launcher-1.0-SNAPSHOT-jar-with-dependencies.jar \
    --ktr=MyKtr -r=flink -d

echo $SECONDS