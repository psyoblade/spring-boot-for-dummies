#!/bin/sh
dir=$(dirname "$0")
java -jar "$dir/h2-1.4.199.jar" -webAllowOthers -tcpAllowOthers -tcpPort 8043
