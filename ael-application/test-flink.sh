#!/usr/bin/env bash
SECONDS=0 ;

export YARN_CONF_DIR=~/yarn_conf/cdh513

PROJECT_DIR=$(pwd)

cd ~/flink/flink-1.4.2

./bin/flink run \
  $PROJECT_DIR/ael-launcher/target/ael-launcher-1.0-SNAPSHOT-jar-with-dependencies.jar \
    --ktr=MyKtr -r=flink -d

echo $SECONDS
