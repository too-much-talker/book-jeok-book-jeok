#!/bin/bash

BUILD_SCRIPT_PATH=/home/ubuntu/S06P12A305/scripts

echo "#####################"
echo "##### 배포 시작 #####"
echo "#####################"
echo ""

sh $BUILD_SCRIPT_PATH/frontend-deploy.sh 2>&1
sh $BUILD_SCRIPT_PATH/backend-deploy.sh 2>&1

echo "#####################"
echo "##### 배포 종료 #####"
echo "#####################"
