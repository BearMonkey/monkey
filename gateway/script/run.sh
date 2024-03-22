#!/bin/bash

basedir=$(cd $(dirname $0); pwd)
#project_dir=${basedir}/..
source ${basedir}/config.properties

container_name="${module_name}"
image_name="${module_name}"
if [ "X${tag}" == "X"  ]; then
    tag="latest"
fi
docker run --privileged=true -d -p ${port}:${port} --name ${container_name} ${image_name}:${tag}