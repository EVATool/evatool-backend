#!/bin/bash
echo ------------------------------
echo BUILD DOCKER IMAGE
ls
docker build -t evatoolapp_image /home/evauser/evatool/backend/docker
ls
echo ------------------------------
echo RUNNING CONTAINERS
docker ps
echo ------------------------------
echo STOP DOCKER CONTAINER
docker stop evatoolapp_container
echo ------------------------------
echo START DOCKER CONTAINER
docker run --name evatoolapp_container --net=host -d --rm evatoolapp_image --server.port=443
echo ------------------------------
echo RUNNING CONTAINERS
docker ps
echo ------------------------------
echo ALL DOCKER IMAGES
docker images
echo ------------------------------
echo REMOVE DANGLING IMAGES
docker images --no-trunc -q -f dangling=true | xargs -r docker rmi
echo ------------------------------
echo ALL DOCKER IMAGES
docker images
echo ------------------------------

