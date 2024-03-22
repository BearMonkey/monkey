#!/bin/bash

basedir=$(cd $(dirname $0); pwd)
#project_dir=${basedir}/..
source ${basedir}/config.properties

container_name="${module_name}"
image_name="${module_name}"
if [ "X${tag}" == "X"  ]; then
    tag="latest"
fi
docker run --privileged=true -d -p ${port1}:${port_at_host} --name ${container_name} ${image_name}:${tag}
docker run --privileged=true -d -p ${port2}:${port_at_host} --name ${container_name} ${image_name}:${tag}