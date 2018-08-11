#!/bin/bash

if [ $# -lt 0 ]; then
    echo Please use the following format:
    echo "$0 <MVN action: clean, jar, site>"
    exit 1
fi

START=`date +%s`

function clean {
    LOGS_ARRAY=(`find ./ -maxdepth 1 -name "*.log"`)

    mvn clean

    for dir in `find . -maxdepth 1 -type d`
    do
        if [ -d "${dir}/target/" ]; then
            rm -rf ${dir}/target/
        fi
    done

    if [ ${#LOGS_ARRAY[@]} -gt 0 ]; then
        rm *.log
    fi

    if [ -d "./logs/" ]; then
        rm -rf ./logs/
    fi
}

ACTION="${1}"

if [ -z "${1}" ] ; then
    ACTION="clean"
fi

if [ "$ACTION" == "clean" ]; then
    clean
elif [ "$ACTION" == "jar" ]; then
    clean
    mvn -Dmaven.test.skip=true package
elif [ "$ACTION" == "site" ]; then
    clean
    mvn -Dmaven.test.skip=true site
fi

RUNTIME=$((`date +%s` - START))

echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] BUILD SUCCESS"
echo "[INFO] ------------------------------------------------------------------------"
echo "[INFO] Total time taken: ${RUNTIME} s"
echo "[INFO] ------------------------------------------------------------------------"
