#!/bin/bash
basedir=$(cd $(dirname $0);pwd)
echo ${basedir}
install_dir=/home/monkey

function start()
{
    module_name=$1
    echo "start module: ${module_name}"
    sh ${install_dir}/${module_name}/start.sh
}


function install_module()
{
    tar_file=$1
    echo "start install module, tar_file=${tar_file}"
    module_name=$(echo ${tar_file} | awk -F '.tar' '{print $1}')

    rm -rf ${install_dir}/${module_name}
    tar -zxf ${basedir}/${tar_file} -C ${install_dir}
    chown root:root ${install_dir}/${module_name} -R

    docker ps -q  --filter name=${module_name} | xargs -i docker stop {}
    docker ps -qa --filter name=${module_name} | xargs -i docker rm {}
    docker images -q ${module_name}            | xargs -i docker rmi {}

    cd ${install_dir}/${module_name}
    docker build -t ${module_name} .

    start "${module_name}" ""
}



module=$1
if [ "X${module}" == "X" ]; then
    echo "install all module"
    for tar_file in $(ls ${basedir} | grep -v "install.sh")
    do
        #echo ${tar_file}
        #tar -zxf ${tar_file}
        install_module "${tar_file}"
    done
else
    echo "install module: ${module}"
    install_module "${module}.tar.gz"
fi



#basedir=/home/monkey
#chown root:root ${basedir} -R
#
##############################
#docker stop eureka_10000
#docker rm eureka_10000
#docker rmi eureka_10000
#
#cd ${basedir}/eureka
#docker build -t eureka_10000 .
#docker run --privileged=true -d -p 10000:10000 --name eureka_10000 eureka_10000
#
##############################
#docker stop gateway_8080
#docker rm gateway_8080
#docker rmi gateway_8080
#
#cd ${basedir}/gateway
#docker build -t gateway_8080 .
#docker run --privileged=true -d -p 8080:8080 --name gateway_8080 gateway_8080
#
##############################
#docker stop user_10020
#docker stop user_10021
#
#docker rm user_10020
#docker rm user_10021
#
#docker rmi user_10020
#
#cd ${basedir}/user
#docker build -t user_10020 .
#docker run --privileged=true -d -p 10020:10020 --name user_10020 user_10020
#docker run --privileged=true -d -p 10021:10020 --name user_10021 user_10020
