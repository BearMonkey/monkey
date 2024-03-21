#!/bin/bash
basedir=$(cd $(dirname $0);pwd)
project_dir=${basedir}/..

module_name=$1
module_dir=${project_dir}/${module_name}

# compile
mvn -U clean install -Dmaven.test.skip=true -f ${module_dir}/pom.xml

# copy file
package_dir=${module_dir}/package
rm -rf ${package_dir}
mkdir -p ${package_dir}/${module_name}
cp -f ${module_dir}/target/*.jar      ${package_dir}/${module_name}/
cp -f ${module_dir}/Dockerfile        ${package_dir}/${module_name}/
cp -f ${module_dir}/script/start.sh   ${package_dir}/${module_name}/

dos2unix ${package_dir}/${module_name}/*

output_dir=${module_dir}/output
rm -rf ${output_dir}
mkdir -p ${output_dir}
cd ${package_dir}/
tar -zcf ${output_dir}/${module_name}.tar.gz *
rm -rf ${package_dir}
