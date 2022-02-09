#!/bin/bash

FE_DIR=/home/ubuntu/S06P12A305/frontend

echo "############################"
echo "### 프론트엔드 배포 시작 ###"
echo "############################"
echo ""

echo "####################"
echo "# npm install 시작 #"
echo "####################"
cd $FE_DIR
npm install
echo "####################"
echo "# npm install 종료 #"
echo "####################"
echo ""

echo "######################"
echo "# npm run build 시작 #"
echo "######################"
npm run build
echo "######################"
echo "# npm run build 종료 #"
echo "######################"
echo ""

echo "###########################"
echo "# change build files 시작 #"
echo "###########################"
sudo rm -rf /var/www/html/dist
sudo mv $FE_DIR/build /var/www/html/dist
echo "###########################"
echo "# change build files 종료 #"
echo "###########################"
echo ""

echo "############################"
echo "### 프론트엔드 배포 종료 ###"
echo "############################"
echo ""
