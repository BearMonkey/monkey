#!/bin/bash

basedir=$(cd $(dirname $0); pwd)
project_dir=${basedir}/..

mvn -U clean install -f ${project_dir}/pom.xml