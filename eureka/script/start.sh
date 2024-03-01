#!/bin/bash
basedir=$(cd $(dirname $0);pwd)

image_name=eureka
container_name=$1
default_port=10000
port=$2
if [ "X${port}" == "X" ]; then
    port=${default_port}
fi
if [ "X${container_name}" == "X" ]; then
    container_name=${image_name}
fi
docker run --privileged=true -d -p ${port}:${default_port} --name ${container_name} ${image_name}