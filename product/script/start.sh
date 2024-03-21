#!/bin/bash
basedir=$(cd $(dirname $0);pwd)

image_name=product
container_name=$1
default_port=10080
port=$2
if [ "X${port}" == "X" ]; then
    port=${default_port}
fi
if [ "X${container_name}" == "X" ]; then
    container_name=${image_name}
fi

port=10080
container_name_10080="${container_name}_10080"
docker run --privileged=true -d -p ${port}:${default_port} --name ${container_name_10080} ${image_name}

port=10081
container_name_10081="${container_name}_10081"
docker run --privileged=true -d -p ${port}:${default_port} --name ${container_name_10081} ${image_name}