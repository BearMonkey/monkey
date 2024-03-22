#!/bin/bash

basedir=$(cd $(dirname $0); pwd)
#project_dir=${basedir}/..
source ${basedir}/config.properties

container_name1="${container_name}_${port1}"
container_name2="${container_name}_${port2}"
image_name="${module_name}"
if [ "X${tag}" == "X"  ]; then
    tag="latest"
fi
#docker run --privileged=true -d -p ${port1}:${port_at_host} --name ${container_name1} ${image_name}:${tag}
#docker run --privileged=true -d -p ${port2}:${port_at_host} --name ${container_name2} ${image_name}:${tag}
docker run --privileged=true -d -p --name ${container_name1} ${image_name}:${tag}
docker run --privileged=true -d -p --name ${container_name2} ${image_name}:${tag}