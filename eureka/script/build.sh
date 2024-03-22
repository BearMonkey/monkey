#!/bin/bash
basedir=$(cd $(dirname $0);pwd)
project_dir=${basedir}/..

source ${basedir}/config.properties

docker ps -q  --filter name=${module_name} | xargs -i docker stop {}
docker ps -qa --filter name=${module_name} | xargs -i docker rm {}
docker images -q ${module_name}            | xargs -i docker rmi {}

cd ${project_dir}
docker build -t ${module_name}:${tag} .