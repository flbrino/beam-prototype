#!/usr/bin/env bash
SECONDS=0 ;

PROJECT_DIR=$(pwd)

java -jar $PROJECT_DIR/ael-launcher/target/ael-launcher-1.0-SNAPSHOT-jar-with-dependencies.jar \
  --ktr=MyKtr -r=direct-runner -d

echo $SECONDS